package guessthecolor.backend.Web.Controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import guessthecolor.backend.Domain.DailyAttemptRound;
import guessthecolor.backend.Domain.DailyPuzzle;
import guessthecolor.backend.Domain.DailyPuzzleAttempt;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Domain.Record.Color;
import guessthecolor.backend.Service.ColorService;
import guessthecolor.backend.Service.DailyPuzzleAttemptService;
import guessthecolor.backend.Service.DailyPuzzleService;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;



@Controller
@AllArgsConstructor
@RequestMapping("/daily")
public class DailyPuzzleController {
    
    private final DailyPuzzleService dailyPuzzleService;
    private final DailyPuzzleAttemptService attemptService;
    private final ColorService colorService;

    @PostMapping("/start")
    public String start(@AuthenticationPrincipal User user, HttpSession session) {
        DailyPuzzle puzzle = dailyPuzzleService.getOrCreateTodaysPuzzle();

        try{
            DailyPuzzleAttempt attempt = attemptService.startAttempt(user, puzzle);
            session.setAttribute("dailyAttempt", attempt);
            return "redirect:/daily/play/0";
        } 
        catch (IllegalStateException e) {
            return "redirect:/daily/leaderboard";
        }   
    }

    @GetMapping("/play/{roundIndex}")
    public String memorizePage(@PathVariable int roundIndex, HttpSession session, Model model){
        DailyPuzzleAttempt attempt = (DailyPuzzleAttempt) session.getAttribute("dailyAttempt");
        
        if (attempt == null || roundIndex < 0 || roundIndex >= 5) {
            return "redirect:/home";
        }

        Color target = colorService.generateColors(attempt.getDailyPuzzle().getSeed()).get(roundIndex);

        model.addAttribute("roundIndex", roundIndex);
        model.addAttribute("targetR", target.r());
        model.addAttribute("targetG", target.g());
        model.addAttribute("targetB", target.b());
        return "memorize";
    }

    @GetMapping("/play/{roundIndex}/guess")
    public String guessPage(@PathVariable int roundIndex, HttpSession session, Model model) {
        model.addAttribute("roundIndex", roundIndex);

        DailyPuzzleAttempt attempt = (DailyPuzzleAttempt) session.getAttribute("dailyAttempt");
        if (attempt == null || roundIndex < 0 || roundIndex >= 5) {
            return "redirect:/home";
        }
        return "guess";
    }

    @PostMapping("/rounds")
    public String submitRound(@RequestParam int roundIndex, @RequestParam int r,
                               @RequestParam int g, @RequestParam int b,
                               HttpSession session) {

        DailyPuzzleAttempt attempt = (DailyPuzzleAttempt) session.getAttribute("dailyAttempt");
        if (attempt == null || roundIndex < 0 || roundIndex >= 5) {
            return "redirect:/home";
        }

        attemptService.submitRound(attempt, roundIndex, new Color(r, g, b));
        return "redirect:/daily/play/" + roundIndex + "/result";
    }

    @GetMapping("/play/{roundIndex}/result")
    public String resultPage(@PathVariable int roundIndex, Model model, HttpSession session) {

        DailyPuzzleAttempt attempt = (DailyPuzzleAttempt) session.getAttribute("dailyAttempt");
        if (attempt == null || roundIndex < 0 || roundIndex >= attempt.getDailyAttemptRounds().size()) {
            return "redirect:/home";
        }

        DailyAttemptRound round = attempt.getDailyAttemptRounds().get(roundIndex);

        model.addAttribute("roundIndex", roundIndex);
        model.addAttribute("round", round);
        model.addAttribute("isLastRound", roundIndex == 4);
        return "result";
    }
    
    @GetMapping("/final")
    public String finalPage(Model model, HttpSession session) {
        DailyPuzzleAttempt attempt = (DailyPuzzleAttempt) session.getAttribute("dailyAttempt");
        if (attempt == null) {
            return "redirect:/home";
        }

        DailyPuzzleAttempt completed = attemptService.completeAttempt(attempt);
        model.addAttribute("practice", completed);

        double max = completed.getDailyAttemptRounds().stream().mapToDouble(r->r.getScore()).max().orElse(0);
        double avg = completed.getDailyAttemptRounds().stream().mapToDouble(r->r.getScore()).average().orElse(0);

        model.addAttribute("max", max);
        model.addAttribute("avg", avg);

        session.removeAttribute("dailyAttempt");

        return "final";
    }

    @GetMapping("/leaderboard")
    public String getLeaderboard(Model model) {
        DailyPuzzle puzzle = dailyPuzzleService.getOrCreateTodaysPuzzle();
        List<DailyPuzzleAttempt> leaderboard = attemptService.getLeaderboard(puzzle);
        model.addAttribute("leaderboard", leaderboard);
        return "dailyLeaderboard";
    }
    

}
