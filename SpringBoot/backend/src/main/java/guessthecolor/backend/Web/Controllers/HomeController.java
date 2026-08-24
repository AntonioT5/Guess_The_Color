package guessthecolor.backend.Web.Controllers;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guessthecolor.backend.Domain.Practice;
import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Repository.PracticeRepository;
import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    
    private final PracticeRepository practiceRepository;

    @GetMapping()
    public String getDashboard(Model model, @AuthenticationPrincipal User user) {

        model.addAttribute("username", user.getUsername());

        List<Practice> practices = practiceRepository.findByUserOrderByCreatedAtDesc(user);
        double bestScore = practices.stream().mapToDouble(p->p.getTotalScore()).max().orElse(0);
        double avgScore = practices.stream().mapToDouble(p->p.getTotalScore()).average().orElse(0);

        model.addAttribute("bestScore", bestScore);
        model.addAttribute("avgScore", avgScore);

        return "homePage";
    }

    
    
}
