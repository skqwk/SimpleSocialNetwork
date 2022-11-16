package ru.sqwk.ssn.service;

public interface LikesService {
  void deleteLike(Long userId, Long postId);

  void addLike(Long userId, Long postId);
}
