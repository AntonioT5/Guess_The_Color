package guessthecolor.backend.Web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {
    
    private final AuthService authService;
    
    @GetMapping
    public String getLoginPage() {
        return "login.html";
    }

    @PostMapping
    public String login(@RequestParam String username, 
                        @RequestParam String password,
                        Model model,
                        HttpServletRequest req){
        User user;

        try {
            user = this.authService.login(username, password);
            req.getSession().setAttribute("user", user);
            return "redirect:/home";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "login.html";
        }
    }
    
}
