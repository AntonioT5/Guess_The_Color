package guessthecolor.backend.Web.Controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
@PreAuthorize("isAuthenticated()")
public class EditController {

    private final UserService userService;

    @GetMapping()
    public String getEditPage(Model model, @AuthenticationPrincipal User user) {
        model.addAttribute("username", user.getUsername());
        model.addAttribute("mail", user.getMail());
        return "editProfile";
    }

    @PostMapping()
    public String editProfileData(@RequestParam String oldUsername,
                                @RequestParam String oldMail,
                                @RequestParam String username, 
                                @RequestParam String mail,
                                @AuthenticationPrincipal User currentUser,
                                Model model
    ) {
        try {
            userService.editData(oldUsername, oldMail, username, mail);

            UserDetails updatedUser = userService.loadUserByMail(mail);

            Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedUser, 
                updatedUser.getPassword(), 
                updatedUser.getAuthorities()
            );

            SecurityContextHolder.getContext().setAuthentication(newAuth);
            
            return "redirect:/home";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("username", currentUser.getUsername());
            model.addAttribute("mail", currentUser.getMail());
            return "editProfile";
        }

    }
    
    
}