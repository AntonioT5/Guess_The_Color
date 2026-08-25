package guessthecolor.backend.Web.Controllers;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import guessthecolor.backend.Domain.Exception.InvalidArgumentsException;
import guessthecolor.backend.Service.UserService;
import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {
    private final UserService userService;

    @GetMapping
    public String getRegisterPage(Model model) {
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String mail,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatPassword,
                           Model model) throws InvalidArgumentsException {

        try {
            this.userService.register(username, password, repeatPassword, mail, new Date());
            return "redirect:/login";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "register";
        }
    }
}
