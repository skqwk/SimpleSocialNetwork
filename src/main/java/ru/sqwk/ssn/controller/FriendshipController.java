package ru.sqwk.ssn.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sqwk.ssn.dto.SelectedFriendshipCategoryDTO;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.FriendshipRequestsService;
import ru.sqwk.ssn.service.FriendshipService;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class FriendshipController {

  private final FriendshipService friendshipService;
  private final FriendshipRequestsService friendshipRequestsService;

  @PostMapping("friendship-request/{friendId}")
  public void createFriendshipRequest(
      @AuthenticationPrincipal UserAccount userAccount, @PathVariable("friendId") Long friendId) {
    friendshipRequestsService.addRequest(userAccount.getId(), friendId);
  }

  @PostMapping("friendship/{friendId}")
  public void addFriendship(
      @AuthenticationPrincipal UserAccount userAccount, @PathVariable("friendId") Long friendId) {
    friendshipService.addFriendship(userAccount.getId(), friendId);
  }

  @DeleteMapping("friendship/{friendId}")
  public void removeFriendship(
      @AuthenticationPrincipal UserAccount userAccount, @PathVariable("friendId") Long friendId) {
    friendshipService.removeFriendship(userAccount.getId(), friendId);
  }

  @DeleteMapping("friendship-request/{sender}/{recipient}")
  public void removeFriendshipRequest(@PathVariable("sender") Long senderId, @PathVariable("recipient") Long recipientId) {
    friendshipRequestsService.removeRequest(senderId, recipientId);
  }

  @PutMapping("friendship/{friendId}/category")
  public void updateFriendshipCategory(
      @AuthenticationPrincipal UserAccount userAccount,
      @PathVariable("friendId") Long friendId,
      @RequestBody SelectedFriendshipCategoryDTO categoryDTO) {
    friendshipService.updateFriendshipCategory(
        userAccount.getId(), friendId, categoryDTO.getSelectedCategory());
  }
}
