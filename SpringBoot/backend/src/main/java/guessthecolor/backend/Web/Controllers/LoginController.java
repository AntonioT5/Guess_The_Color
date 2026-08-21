package guessthecolor.backend.Web.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {
    
    @GetMapping
    public String getLoginPage() {
        return "login.html";
    }
    
}
