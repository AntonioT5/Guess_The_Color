package guessthecolor.backend.Web.Controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import guessthecolor.backend.Domain.User;


@Controller
@RequestMapping("/home")
public class HomeController {
    

    @GetMapping()
    public String getDashboard(Model model, @AuthenticationPrincipal User user) {

         model.addAttribute("username", user.getUsername());
        return "homePage";
    }
    
}
