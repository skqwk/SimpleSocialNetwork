package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.domain.Post;
import ru.sqwk.ssn.model.PostExpandedModel;
import ru.sqwk.ssn.model.PostModel;

import java.util.List;

public interface PostRepo {
  List<PostModel> getPosts(Long userId);

  PostModel getPost(Long authorId, Long postId);

  Long save(Post post);

  PostExpandedModel getExpandedPost(Long authorId, Long postId);

  void deletePost(Long postId);

  void update(Post post);
}
