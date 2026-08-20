package guessthecolor.backend.Service;

import java.util.List;

import guessthecolor.backend.Domain.Record.Color;

public interface ColorService {
    
    List<Color> generateColors(long seed);
    double calculateDeltaE(Color target, Color guess);
    double calculateScore(double deltaE);
}
