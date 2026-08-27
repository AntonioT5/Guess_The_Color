package guessthecolor.backend.Web.Controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guessthecolor.backend.Domain.DailyPuzzle;
import guessthecolor.backend.Domain.Practice;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Service.DailyPuzzleAttemptService;
import guessthecolor.backend.Service.DailyPuzzleService;
import guessthecolor.backend.Service.PracticeService;
import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/home")
@AllArgsConstructor
@PreAuthorize("isAuthenticated()")
public class HomeController {
    
    private final PracticeService practiceService;
    private final DailyPuzzleAttemptService dailyPuzzleAttemotService;
    private final DailyPuzzleService dailyPuzzleService;

    @GetMapping()
    public String getDashboard(Model model, @AuthenticationPrincipal User user) {

        model.addAttribute("username", user.getUsername());

        DailyPuzzle puzzle = dailyPuzzleService.getOrCreateTodaysPuzzle();

        List<Practice> practices = practiceService.findAllPracticesByUser(user);
        double bestScore = practices.stream().mapToDouble(p->p.getTotalScore()).max().orElse(0);
        double avgScore = practices.stream().mapToDouble(p->p.getTotalScore()).average().orElse(0);

        model.addAttribute("exists", dailyPuzzleAttemotService.userAndPuzzleExists(user, puzzle));
        model.addAttribute("bestScore", bestScore);
        model.addAttribute("avgScore", avgScore);
        try{
            model.addAttribute("puzzleScore", dailyPuzzleAttemotService.findByUserAndPuzzle(user, puzzle).getTotalScore());
        }
        catch(RuntimeException e){
            model.addAttribute("puzzleScore", " data loading error");
        }

        return "homePage";
    }

    
    
}
