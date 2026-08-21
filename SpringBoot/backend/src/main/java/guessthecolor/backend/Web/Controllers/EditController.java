package guessthecolor.backend.Web.Controllers;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import guessthecolor.backend.Domain.User;
import guessthecolor.backend.Service.UserService;
import lombok.AllArgsConstructor;


@Controller
@RequestMapping("/edit")
@AllArgsConstructor
public class EditController {

    private final UserService userService;

    @GetMapping()
    public String getEditPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("mail", user.getMail());
        return "editProfile";
    }

    @PostMapping()
    public String editProfileData(@RequestParam String oldUsername, @RequestParam String username, @RequestParam String mail,
        Model model
    ) {
        try {
            userService.editData(oldUsername, username, mail);
            return "redirect:/home";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            return "redirect:/editProfile";
        }

    }
    
    
}