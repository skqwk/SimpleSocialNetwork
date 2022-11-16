package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.Message;
import ru.sqwk.ssn.dto.MessageDTO;
import ru.sqwk.ssn.dto.UpdatedMessageDTO;
import ru.sqwk.ssn.model.MessageChatModel;
import ru.sqwk.ssn.model.MessageModel;
import ru.sqwk.ssn.model.ProcessedMessageModel;
import ru.sqwk.ssn.repo.MessageRepo;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.MessageService;
import ru.sqwk.ssn.service.UserService;
import ru.sqwk.ssn.util.Formatter;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepo messageRepo;
  private final UserService userService;

  @Override
  public List<MessageChatModel> getChats(Long userId) {
    return messageRepo.getChats(userId);
  }

  @Override
  public List<MessageModel> getChat(Long userId, Long friendId) {

    List<MessageModel> messages = messageRepo.getMessages(userId, friendId);
    messages.stream()
        .filter(m -> !m.getOwn())
        .forEach(
            m -> {
              markMessageAsRead(m.getId());
              m.setHasBeenRead(true);
            });
    return messages;
  }

  @Override
  public ProcessedMessageModel saveMessage(MessageDTO messageDTO) {
    // UserAccount senderUser = SecurityContextWrapper.getNowUser();
    UserAccount to = userService.getUserByName(messageDTO.getTo());
    UserAccount from = userService.getUserByName(messageDTO.getFrom());
    Message message =
        Message.builder()
            .hasBeenRead(false)
            .content(messageDTO.getContent())
            .senderId(from.getId())
            .timestamp(Formatter.format(new Date()))
            .recipientId(to.getId())
            .build();

    log.info("Save message from = {}, to = {}", from.getLogin(), to.getLogin());

    Long messageId = messageRepo.save(message);
    Message savedMessage = getMessage(messageId);

    ProcessedMessageModel processedMessage =
        ProcessedMessageModel.builder()
            .id(savedMessage.getId())
            .content(savedMessage.getContent())
            .senderId(from.getId())
            .senderLogin(from.getLogin())
            .recipientId(to.getId())
            .recipientLogin(to.getLogin())
            .sentAt(savedMessage.getTimestamp())
            .build();
    return processedMessage;
  }

  @Override
  public void deleteMessage(Long messageId) {
    messageRepo.delete(messageId);
  }

  @Override
  public void updateMessage(Long messageId, UpdatedMessageDTO messageDTO) {
    messageRepo.update(messageId, messageDTO.getMessageContent());
  }

  @Override
  public void markMessageAsRead(Long messageId) {
    messageRepo.markMessageAsRead(messageId);
  }

  private Message getMessage(Long messageId) {
    return messageRepo
        .getMessage(messageId)
        .orElseThrow(() -> new RuntimeException("Not found message"));
  }
}
