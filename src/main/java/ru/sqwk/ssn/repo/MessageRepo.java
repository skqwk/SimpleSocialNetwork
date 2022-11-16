package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.domain.Message;
import ru.sqwk.ssn.model.MessageChatModel;
import ru.sqwk.ssn.model.MessageModel;

import java.util.List;
import java.util.Optional;

public interface MessageRepo {
    List<MessageChatModel> getChats(Long userId);

    List<MessageModel> getMessages(Long userId, Long friendId);

    Optional<Message> getMessage(Long messageId);

    Long save(Message message);

    void delete(Long messageId);

    void update(Long messageId, String messageContent);

    void markMessageAsRead(Long messageId);
}
