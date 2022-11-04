package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProcessedMessageModel {
    private String senderLogin;
    private String recepientLogin;
    private Long senderId;
    private Long recepientId;
    private String content;
    private String sentAt;

}
