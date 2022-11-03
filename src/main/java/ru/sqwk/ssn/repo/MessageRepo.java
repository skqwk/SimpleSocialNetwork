package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.model.MessageModel;

import java.util.List;

public interface MessageRepo {
    List<MessageModel> getChats(Long userId);

    List<MessageModel> getMessages(Long userId, Long friendId);
}
