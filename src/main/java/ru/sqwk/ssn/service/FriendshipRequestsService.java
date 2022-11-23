package ru.sqwk.ssn.service;

import ru.sqwk.ssn.model.FriendshipRequestModel;

import java.util.List;

public interface FriendshipRequestsService {
    List<FriendshipRequestModel> getOutcomingFriendshipRequests(Long id);
    List<FriendshipRequestModel> getIncomingFriendshipRequests(Long id);

    void addRequest(Long id, Long friendId);

    void removeRequest(Long senderId, Long recipientId);

    boolean exists(Long friendId, Long userId);
}
