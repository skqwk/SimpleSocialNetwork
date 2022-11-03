package ru.sqwk.ssn.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.sqwk.ssn.dto.PostDTO;
import ru.sqwk.ssn.model.PostModel;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.PostService;

@Slf4j
@RestController
@AllArgsConstructor
public class PostController {

    private final PostService postService;

    @PostMapping(value = "/post")
    public PostModel addPost(@AuthenticationPrincipal UserAccount userAccount, @RequestBody PostDTO postDTO) {
        log.info("User with id = {} want to add post with content = {}", userAccount.getId(), postDTO.getPostContent());
        PostModel postModel = postService.addPost(userAccount.getId(), postDTO.getPostContent());
        log.info("Add new post with content = {}", postModel.getContent());

        return postModel;
    }
    @PutMapping(value = "/posts/{postId}")
    public PostModel addPost(@AuthenticationPrincipal UserAccount userAccount, @RequestBody PostDTO postDTO, @PathVariable Long postId) {
        PostModel postModel = postService.updatePost(userAccount.getId(), postDTO.getPostContent(), postId);
        log.info("Add new post with content = {}", postModel.getContent());

        return postModel;
    }

    @DeleteMapping("/posts/{postId}")
    public void deletePost(@PathVariable(name = "postId") Long postId) {
        postService.deletePost(postId);
    }

}
