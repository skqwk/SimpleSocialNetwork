package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sqwk.ssn.controller.CommunityController;
import ru.sqwk.ssn.dto.NewCommunityDTO;
import ru.sqwk.ssn.service.CommunityService;

@Slf4j
@Controller
@AllArgsConstructor
public class CommunityViewController {

  private final CommunityService communityService;
  private final CommunityController communityController;

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

  @GetMapping("/community/new")
  public String newCommunity(Model model) {
    model.addAttribute("community", new NewCommunityDTO());
    return "community_form";
  }

  @PostMapping("/community")
  public String createCommunity(@ModelAttribute("community") NewCommunityDTO newCommunityDTO) {
    log.info(
        "Create new community, name = {}, topic = {}, ageLimit = {}",
        newCommunityDTO.getName(),
        newCommunityDTO.getTopic(),
        newCommunityDTO.getAgeLimit());

    Long id = communityController.createCommunity(newCommunityDTO).getId();
    return String.format("redirect:/communities/%s", id);
  }
}
