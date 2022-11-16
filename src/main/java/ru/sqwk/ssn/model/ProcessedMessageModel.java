package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProcessedMessageModel {
  private Long id;
  private String senderLogin;
  private String recipientLogin;
  private Long senderId;
  private Long recipientId;
  private String content;
  private String sentAt;
}
