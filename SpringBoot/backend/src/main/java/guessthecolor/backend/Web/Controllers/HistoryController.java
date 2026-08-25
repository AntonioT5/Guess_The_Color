package guessthecolor.backend.Web.Controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
    public String getMethodName(@AuthenticationPrincipal User user, Model model, @RequestParam(defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 5);
        Page<Practice> practicePage = practiceService.findPracticesByUser(user, pageable);

        List<Practice> completed = practicePage.getContent().stream().filter(p -> p.getPracticeRounds().size() == 5).toList();

        model.addAttribute("practices", completed);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", practicePage.getTotalPages());
        model.addAttribute("hasNext", practicePage.hasNext());
        model.addAttribute("hasPrevious", practicePage.hasPrevious());
        
        return "history";
    }
    
}
