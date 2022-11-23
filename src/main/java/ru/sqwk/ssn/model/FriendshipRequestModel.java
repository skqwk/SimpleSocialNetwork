package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FriendshipRequestModel {
    private Long senderId;
    private Long recipientId;
    private String timestamp;
    private String status;
    private String login;
}
