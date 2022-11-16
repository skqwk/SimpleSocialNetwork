package ru.sqwk.ssn.service;

import ru.sqwk.ssn.model.PostExpandedModel;
import ru.sqwk.ssn.model.PostModel;

import java.util.List;

public interface PostService {
  List<PostModel> getPosts(Long userId);

  PostModel addPost(Long id, String postContent);

  PostExpandedModel getExpandedPost(Long id, Long postId);

  void deletePost(Long postId);

  PostModel updatePost(Long id, String postContent, Long postId);
}
