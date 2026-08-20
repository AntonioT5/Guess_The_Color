package guessthecolor.backend.Service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import guessthecolor.backend.Domain.Record.Color;
import guessthecolor.backend.Service.ColorService;

@Service
public class ColorServiceImpl implements ColorService {

    @Override
    public List<Color> generateColors(long seed) {
        Random rng = new Random(seed);
        List<Color> colors = new ArrayList<>();
        for (int i=0; i<5; i++){
            int h = rng.nextInt(360);
            int s = 40 + rng.nextInt(61);
            int b = 40 + rng.nextInt(61);
            colors.add(new Color(h, s, b));
        }
        return colors;
    }

    @Override
    public double calculateDeltaE(Color target, Color guess) {
        double[] targetLab = hsbToLab(target);
        double[] guessLab = hsbToLab(guess);
        return ciede2000(targetLab, guessLab);
    }

    @Override
    public double calculateScore(double deltaE) {
        double score = 10.0 * Math.exp(-deltaE / 12.0);
        return Math.max(0, Math.min(10, score));
    }

    private double[] hsbToLab(Color c){
        int rgbInt = java.awt.Color.HSBtoRGB(c.h() / 360f, c.s() / 100f, c.b() / 100f);
        int r = (rgbInt >> 16) & 0xFF;
        int g = (rgbInt >> 8) & 0xFF;
        int bl = rgbInt & 0xFF;
        return rgbToLab(r, g, bl);
    }
    
    private double[] rgbToLab(int r, int g, int b){
        double rl = pivotRgb(r / 255.0);
        double gl = pivotRgb(g / 255.0);
        double bl = pivotRgb(b / 255.0);
        
        //converts RGB into an intermediate space XYZ (a scientific "how much of each type of light" measurement)
        double x = rl * 0.4124 + gl * 0.3576 + bl * 0.1805;
        double y = rl * 0.2126 + gl * 0.7152 + bl * 0.0722;
        double z = rl * 0.0193 + gl * 0.1192 + bl * 0.9505;
        x /= 0.95047;
        y /= 1.00000;
        z /= 1.08883;

        double fx = pivotXyz(x);
        double fy = pivotXyz(y);
        double fz = pivotXyz(z);

        double L = 116 * fy - 16;
        double A = 500 * (fx - fy);
        double B = 200 * (fy - fz);
        return new double[]{L, A, B};
    }

    private double pivotRgb(double n) { 
        return n > 0.04045 ? Math.pow((n + 0.055) / 1.055, 2.4) : n / 12.92; // sRGB→Lab converter formula
    }

    private double pivotXyz(double n) {
        return n > 0.008856 ? Math.cbrt(n) : (7.787 * n) + (16.0 / 116.0); //XYZ into the final L, A, B numbers, where L = lightness, A = green↔red axis, B = blue↔yellow axis
    }

    //industry standard, published by color scientists
    //two Lab colors in → one number out, where bigger = more visually different.
    private double ciede2000(double[] lab1, double[] lab2){
        double L1 = lab1[0], a1 = lab1[1], b1 = lab1[2];
        double L2 = lab2[0], a2 = lab2[1], b2 = lab2[2];

        double avgL = (L1 + L2) / 2.0;
        double c1 = Math.sqrt(a1 * a1 + b1 * b1);
        double c2 = Math.sqrt(a2 * a2 + b2 * b2);
        double avgC = (c1 + c2) / 2.0;

        double g = 0.5 * (1 - Math.sqrt(Math.pow(avgC, 7) / (Math.pow(avgC, 7) + Math.pow(25, 7))));
        double a1p = a1 * (1 + g);
        double a2p = a2 * (1 + g);

        double c1p = Math.sqrt(a1p * a1p + b1 * b1);
        double c2p = Math.sqrt(a2p * a2p + b2 * b2);
        double avgCp = (c1p + c2p) / 2.0;

        double h1p = Math.toDegrees(Math.atan2(b1, a1p));
        if (h1p < 0) h1p += 360;
        double h2p = Math.toDegrees(Math.atan2(b2, a2p));
        if (h2p < 0) h2p += 360;

        double deltHp;
        if (c1p * c2p == 0) deltHp = 0;
        else if (Math.abs(h2p - h1p) <= 180) deltHp = h2p - h1p;
        else if (h2p <= h1p) deltHp = h2p - h1p + 360;
        else deltHp = h2p - h1p - 360;

        double deltLp = L2 - L1;
        double deltCp = c2p - c1p;
        double deltHbig = 2 * Math.sqrt(c1p * c2p) * Math.sin(Math.toRadians(deltHp) / 2.0);

        double avgHp;
        if (c1p * c2p == 0) avgHp = h1p + h2p;
        else if (Math.abs(h1p - h2p) <= 180) avgHp = (h1p + h2p) / 2.0;
        else if (h1p + h2p < 360) avgHp = (h1p + h2p + 360) / 2.0;
        else avgHp = (h1p + h2p - 360) / 2.0;

        double t = 1 - 0.17 * Math.cos(Math.toRadians(avgHp - 30))
                     + 0.24 * Math.cos(Math.toRadians(2 * avgHp))
                     + 0.32 * Math.cos(Math.toRadians(3 * avgHp + 6))
                     - 0.20 * Math.cos(Math.toRadians(4 * avgHp - 63));

        double deltTheta = 30 * Math.exp(-Math.pow((avgHp - 275) / 25.0, 2));
        double rc = 2 * Math.sqrt(Math.pow(avgCp, 7) / (Math.pow(avgCp, 7) + Math.pow(25, 7)));
        double sl = 1 + (0.015 * Math.pow(avgL - 50, 2)) / Math.sqrt(20 + Math.pow(avgL - 50, 2));
        double sc = 1 + 0.045 * avgCp;
        double sh = 1 + 0.015 * avgCp * t;
        double rt = -Math.sin(Math.toRadians(2 * deltTheta)) * rc;

        double klsl = deltLp / sl;
        double kcsc = deltCp / sc;
        double khsh = deltHbig / sh;

        return Math.sqrt(klsl * klsl + kcsc * kcsc + khsh * khsh + rt * kcsc * khsh);
    }
}
