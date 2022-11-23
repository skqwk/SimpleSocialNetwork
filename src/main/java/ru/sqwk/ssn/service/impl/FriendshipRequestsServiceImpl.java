package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.Friendship;
import ru.sqwk.ssn.domain.FriendshipRequest;
import ru.sqwk.ssn.model.FriendshipRequestModel;
import ru.sqwk.ssn.repo.FriendshipRequestsRepo;
import ru.sqwk.ssn.service.FriendshipRequestsService;
import ru.sqwk.ssn.util.Formatter;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class FriendshipRequestsServiceImpl implements FriendshipRequestsService {

    private final FriendshipRequestsRepo friendshipRequestsRepo;

    @Override
    public List<FriendshipRequestModel> getOutcomingFriendshipRequests(Long id) {
        return friendshipRequestsRepo.findAllOutcomingRequestsByUserId(id);
    }

    @Override
    public List<FriendshipRequestModel> getIncomingFriendshipRequests(Long id) {
        return friendshipRequestsRepo.findAllIncomingRequestsByUserId(id);
    }

    @Override
    public void addRequest(Long userId, Long friendId) {
        log.info("Get request on friendship between ({}, {})", userId, friendId);
        FriendshipRequest request =
                FriendshipRequest.builder()
                        .timestamp(Formatter.format(new Date()))
                        .status("Pending")
                        .senderId(userId)
                        .recipientId(friendId)
                        .build();
        friendshipRequestsRepo.save(request);
    }

    @Override
    public void removeRequest(Long senderId, Long recipientId) {
        log.info("Remove friendshipRequest from = {}, to = {}", senderId, recipientId);
        friendshipRequestsRepo.delete(senderId, recipientId);
    }

    @Override
    public boolean exists(Long senderId, Long recipientId) {
        return friendshipRequestsRepo.exists(senderId, recipientId);
    }
}
