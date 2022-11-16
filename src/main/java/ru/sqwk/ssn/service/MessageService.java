package ru.sqwk.ssn.service;

import ru.sqwk.ssn.dto.MessageDTO;
import ru.sqwk.ssn.dto.UpdatedMessageDTO;
import ru.sqwk.ssn.model.MessageChatModel;
import ru.sqwk.ssn.model.MessageModel;
import ru.sqwk.ssn.model.ProcessedMessageModel;

import java.util.List;

public interface MessageService {

  List<MessageChatModel> getChats(Long userId);

  List<MessageModel> getChat(Long id, Long friendId);

  ProcessedMessageModel saveMessage(MessageDTO messageDTO);

  void deleteMessage(Long messageId);

  void updateMessage(Long messageId, UpdatedMessageDTO messageDTO);

  void markMessageAsRead(Long messageId);
}
