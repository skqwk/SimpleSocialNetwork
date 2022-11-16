package ru.sqwk.ssn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Message {
  private Long id;
  private String timestamp;
  private String content;
  private Long senderId;
  private Long recipientId;
  private Boolean hasBeenRead;
}
