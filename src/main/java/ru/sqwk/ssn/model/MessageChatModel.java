package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс модели отображения чатов
 */
@Getter
@Setter
@Builder
public class MessageChatModel {
    Long id;
    String sentAt;
    AuthorModel author;
    String content;
    Boolean hasBeenRead;
    Boolean own;
    Long friendId;
    String friendLogin;
}
