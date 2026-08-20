package guessthecolor.backend.Web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guessthecolor.backend.Domain.DailyPuzzle;
import guessthecolor.backend.Service.DailyPuzzleAttemptService;
import guessthecolor.backend.Service.DailyPuzzleService;
import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/daily")
@AllArgsConstructor
public class DailyPuzzleController {

    private final DailyPuzzleService dailyPuzzleService;
    private final DailyPuzzleAttemptService attemptService;

    @GetMapping()
    public String getDailyPage(Model model) {
        DailyPuzzle dailyPuzzle = dailyPuzzleService.getOrCreateTodaysPuzzle();
        model.addAttribute("puzzle", dailyPuzzle);
        return "";
    }
    
}
