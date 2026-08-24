package guessthecolor.backend.Web.Controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guessthecolor.backend.Domain.Practice;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Service.PracticeService;
import lombok.AllArgsConstructor;


@Controller
@AllArgsConstructor
@RequestMapping("/history")
public class HistoryController {
    
    private final PracticeService practiceService;

    @GetMapping()
    public String getMethodName(@AuthenticationPrincipal User user, Model model) {

        List<Practice> practices = practiceService.findAllPracticesByUser(user).stream().filter(p->p.getPracticeRounds().size()==5).toList();

        model.addAttribute("practices", practices);
        
        return "history";
    }
    
}
