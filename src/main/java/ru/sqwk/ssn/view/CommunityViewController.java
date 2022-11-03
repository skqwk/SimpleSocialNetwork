package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sqwk.ssn.service.CommunityService;

@Controller
@AllArgsConstructor
public class CommunityViewController {


    private final CommunityService communityService;

    @GetMapping("/communities")
    public String communities(Model model) {
        model.addAttribute("communities", communityService.getCommunities());
        return "communities";
    }


    @GetMapping("/communities/{id}")
    public String communities(@PathVariable Long id, Model model) {
        model.addAttribute("community", communityService.getCommunity(id));
        return "community";
    }

}
