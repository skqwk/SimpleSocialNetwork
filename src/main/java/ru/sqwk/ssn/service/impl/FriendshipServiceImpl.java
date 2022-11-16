package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.Friendship;
import ru.sqwk.ssn.repo.FriendshipRepo;
import ru.sqwk.ssn.service.FriendshipService;
import ru.sqwk.ssn.util.Formatter;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

  private final FriendshipRepo friendshipRepo;

  @Override
  public void removeFriendship(Long userId, Long friendId) {
    log.info("Delete friendship between ({}, {})", userId, friendId);
    friendshipRepo.delete(userId, friendId);
  }

  @Override
  public void addFriendship(Long userId, Long friendId) {
    log.info("Create friendship between ({}, {})", userId, friendId);
    Friendship friendship =
        Friendship.builder()
            .timestamp(Formatter.format(new Date()))
            .category("Common")
            .user1(userId)
            .user2(friendId)
            .build();
    friendshipRepo.save(friendship);
  }

  @Override
  public void updateFriendshipCategory(Long userId, Long friendId, String category) {
    log.info("Update friendship({}, {}), category = {}", userId, friendId, category);
    friendshipRepo.update(userId, friendId, category);
  }
}
