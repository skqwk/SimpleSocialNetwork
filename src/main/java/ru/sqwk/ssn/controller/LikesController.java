package ru.sqwk.ssn.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.LikesService;

@RestController
@AllArgsConstructor
public class LikesController {

  private final LikesService likesService;

  @PostMapping("/likes/{postId}")
  public void addLike(
      @AuthenticationPrincipal UserAccount userAccount,
      @PathVariable(name = "postId") Long postId) {
    likesService.addLike(userAccount.getId(), postId);
  }

  @DeleteMapping("/likes/{postId}")
  public void deleteLike(
      @AuthenticationPrincipal UserAccount userAccount,
      @PathVariable(name = "postId") Long postId) {
    likesService.deleteLike(userAccount.getId(), postId);
  }
}
