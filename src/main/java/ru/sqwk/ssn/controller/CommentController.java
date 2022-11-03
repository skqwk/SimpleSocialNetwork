package ru.sqwk.ssn.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sqwk.ssn.dto.CommentDTO;
import ru.sqwk.ssn.model.CommentModel;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.CommentService;

@Slf4j
@RestController
@AllArgsConstructor
public class CommentController {

  private final CommentService commentService;

  @DeleteMapping("/comments/{commentId}")
  public void deleteComment(@PathVariable Long commentId) {
    commentService.deleteComment(commentId);
  }

  @PostMapping("/posts/{postId}/comment")
  public CommentModel addComment(
      @AuthenticationPrincipal UserAccount userAccount,
      @PathVariable Long postId,
      @RequestBody CommentDTO commentDTO) {
    log.info(
        "User with id = {} wants add comment with content = {}",
        userAccount.getId(),
        commentDTO.getCommentContent());
    return commentService.addComment(userAccount.getId(), commentDTO.getCommentContent(), postId);
  }
}
