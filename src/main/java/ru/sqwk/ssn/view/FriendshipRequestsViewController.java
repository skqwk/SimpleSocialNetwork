package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.FriendshipRequestsService;

@Slf4j
@Controller
@AllArgsConstructor
public class FriendshipRequestsViewController {

  private final FriendshipRequestsService friendshipRequestsService;

  @GetMapping("/friendship-requests")
  public String friends(@AuthenticationPrincipal UserAccount userAccount, Model model) {
    Long userId = userAccount.getId();
    model.addAttribute(
        "incomingRequests", friendshipRequestsService.getIncomingFriendshipRequests(userId));
    model.addAttribute(
        "outcomingRequests", friendshipRequestsService.getOutcomingFriendshipRequests(userId));
    return "friendship_requests";
  }
}
