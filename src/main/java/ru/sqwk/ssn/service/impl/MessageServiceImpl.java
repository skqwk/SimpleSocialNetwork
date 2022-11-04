package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.Message;
import ru.sqwk.ssn.dto.MessageDTO;
import ru.sqwk.ssn.model.MessageModel;
import ru.sqwk.ssn.model.ProcessedMessageModel;
import ru.sqwk.ssn.model.UserProfileModel;
import ru.sqwk.ssn.repo.MessageRepo;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.MessageService;
import ru.sqwk.ssn.service.UserService;
import ru.sqwk.ssn.util.Formatter;
import ru.sqwk.ssn.util.SecurityContextWrapper;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepo messageRepo;
  private final UserService userService;

  @Override
  public List<MessageModel> getChats(Long userId) {
    return messageRepo.getChats(userId);
  }

  @Override
  public List<MessageModel> getChat(Long userId, Long friendId) {
    return messageRepo.getMessages(userId, friendId);
  }

  @Override
  public ProcessedMessageModel saveMessage(MessageDTO messageDTO) {
    //UserAccount senderUser = SecurityContextWrapper.getNowUser();
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

    ProcessedMessageModel processedMessage =
        ProcessedMessageModel.builder()
            .content(message.getContent())
            .senderId(from.getId())
            .senderLogin(from.getLogin())
            .recepientId(to.getId())
            .recepientLogin(to.getLogin())
            .sentAt(message.getTimestamp())
            .build();

    // messageRepo.save()
    return processedMessage;
  }
}
