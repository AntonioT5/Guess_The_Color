package guessthecolor.backend.Web.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import guessthecolor.backend.Domain.Practice;
import guessthecolor.backend.Domain.PracticeRound;
import guessthecolor.backend.Domain.Record.Color;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Service.ColorService;
import guessthecolor.backend.Service.PracticeService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;



@Controller
@RequestMapping("/practice")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class PracticeController {
    
    private final PracticeService practiceService;
    private final ColorService colorService;

    @PostMapping("/start")
    public String start(@AuthenticationPrincipal User user, HttpSession session) {
        Practice practice = practiceService.startSession(user);
        session.setAttribute("practice", practice);
        return "redirect:/practice/play/0";
    }

    @GetMapping("/play/{roundIndex}")
    public String memorizePage(@PathVariable int roundIndex, HttpSession session, Model model) {
        Practice practice = (Practice) session.getAttribute("practice");

        if (practice == null || roundIndex < 0 || roundIndex >= 5) {
            return "redirect:/home";
        }

        Color target = colorService.generateColors(practice.getSeed()).get(roundIndex);

        model.addAttribute("roundIndex", roundIndex);
        model.addAttribute("targetR", target.r());
        model.addAttribute("targetG", target.g());
        model.addAttribute("targetB", target.b());
        return "memorizePractice";
    }

    @GetMapping("/play/{roundIndex}/guess")
    public String guessPage(@PathVariable int roundIndex, HttpSession session, Model model) {
        model.addAttribute("roundIndex", roundIndex);

        Practice practice = (Practice) session.getAttribute("practice");
        if (practice == null || roundIndex < 0 || roundIndex >= 5) {
            return "redirect:/home";
        }

        return "guess";
    }

    @PostMapping("/rounds")
    public String submitRound(@RequestParam int roundIndex, @RequestParam int r,
                               @RequestParam int g, @RequestParam int b,
                               HttpSession session) {
        Practice practice = (Practice) session.getAttribute("practice");
        if (practice == null || roundIndex < 0 || roundIndex >= 5) {
            return "redirect:/home";
        }

        practiceService.submitRound(practice, roundIndex, new Color(r, g, b));
        return "redirect:/practice/play/" + roundIndex + "/result";
    }
    
    @GetMapping("/play/{roundIndex}/result")
    public String resultPage(@PathVariable int roundIndex, Model model, HttpSession session) {
        Practice practice = (Practice) session.getAttribute("practice");
        if (practice == null || roundIndex < 0 || roundIndex >= practice.getPracticeRounds().size()) {
            return "redirect:/home";
        }

        PracticeRound round = practice.getPracticeRounds().get(roundIndex);

        model.addAttribute("roundIndex", roundIndex);
        model.addAttribute("round", round);
        model.addAttribute("isLastRound", roundIndex == 4);
        return "result";
    }
    
    @GetMapping("/final")
    public String finalPage(Model model, HttpSession session) {
        Practice practice = (Practice) session.getAttribute("practice");
        if (practice == null) {
            return "redirect:/home";
        }

        Practice completed = practiceService.completeSession(practice);
        model.addAttribute("practice", completed);

        double max = completed.getPracticeRounds().stream().mapToDouble(r->r.getScore()).max().orElse(0);
        double avg = completed.getPracticeRounds().stream().mapToDouble(r->r.getScore()).average().orElse(0);

        model.addAttribute("max", max);
        model.addAttribute("avg", avg);

        session.removeAttribute("practice");

        return "final";
    }
}
