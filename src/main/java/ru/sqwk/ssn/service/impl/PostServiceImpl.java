package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.Post;
import ru.sqwk.ssn.model.PostExpandedModel;
import ru.sqwk.ssn.model.PostModel;
import ru.sqwk.ssn.repo.PostRepo;
import ru.sqwk.ssn.service.PostService;
import ru.sqwk.ssn.util.Formatter;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepo postRepo;

  @Override
  public List<PostModel> getPosts(Long userId) {
    return postRepo.getPosts(userId);
  }

  @Override
  public PostModel addPost(Long authorId, String postContent) {
    Post post =
        Post.builder()
            .content(postContent)
            .authorId(authorId)
            .createdAt(Formatter.format(new Date()))
            .build();
    Long postId = postRepo.save(post);
    log.info("Create post with id = {}", postId);
    return getPost(authorId, postId);
  }

  private PostModel getPost(Long authorId, Long postId) {
    return postRepo.getPost(authorId, postId);
  }

  @Override
  public PostExpandedModel getExpandedPost(Long authorId, Long postId) {
    return postRepo.getExpandedPost(authorId, postId);
  }

  @Override
  public void deletePost(Long postId) {
    postRepo.deletePost(postId);
  }

  @Override
  public PostModel updatePost(Long authorId, String postContent, Long postId) {
    Post post = Post.builder().id(postId).content(postContent).build();
    postRepo.update(post);
    log.info("Create post with id = {}", postId);
    return getPost(authorId, postId);
  }
}
