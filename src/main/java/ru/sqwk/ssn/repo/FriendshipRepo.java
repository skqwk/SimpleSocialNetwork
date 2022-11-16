package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.domain.Friendship;

public interface FriendshipRepo {
  void delete(Long userId, Long friendId);

  void save(Friendship friendship);

  void update(Long id, Long friendId, String category);
}
