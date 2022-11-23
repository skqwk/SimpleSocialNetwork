package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.domain.FriendshipRequest;
import ru.sqwk.ssn.model.FriendshipRequestModel;

import java.util.List;

public interface FriendshipRequestsRepo {

    void save(FriendshipRequest request);
    List<FriendshipRequestModel> findAllOutcomingRequestsByUserId(Long userId);
    List<FriendshipRequestModel> findAllIncomingRequestsByUserId(Long userId);

    void delete(Long senderId, Long recipientId);

    boolean exists(Long senderId, Long recipientId);
}
