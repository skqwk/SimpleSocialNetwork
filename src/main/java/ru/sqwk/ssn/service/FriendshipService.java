package ru.sqwk.ssn.service;

public interface FriendshipService {
  void removeFriendship(Long userId, Long friendId);

  void addFriendship(Long id, Long friendId);

  void updateFriendshipCategory(Long id, Long friendId, String category);
}
