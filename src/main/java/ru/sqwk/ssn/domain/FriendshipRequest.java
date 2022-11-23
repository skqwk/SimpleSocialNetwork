package ru.sqwk.ssn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendshipRequest {
    private Long senderId;
    private Long recipientId;
    private String timestamp;
    private String status;
}
