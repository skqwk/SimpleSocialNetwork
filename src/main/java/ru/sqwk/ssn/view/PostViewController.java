package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.PostService;

@Controller
@AllArgsConstructor
public class PostViewController {

  private final PostService postService;

  @GetMapping("/posts")
  public String posts(@AuthenticationPrincipal UserAccount userAccount, Model model) {
    model.addAttribute("posts", postService.getPosts(userAccount.getId()));
    return "posts";
  }

  @GetMapping("/posts/{postId}")
  public String post(
      @AuthenticationPrincipal UserAccount userAccount, Model model, @PathVariable Long postId) {
    model.addAttribute("post", postService.getExpandedPost(userAccount.getId(), postId));
    return "post";
  }
}
