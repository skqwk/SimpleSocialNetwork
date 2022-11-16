package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MessageModel {
    Long id;
    String sentAt;
    AuthorModel author;
    String content;
    Boolean hasBeenRead;
    Boolean own;
    Long friendId;
    Boolean canEdit;
}
