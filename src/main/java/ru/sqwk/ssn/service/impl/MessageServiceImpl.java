package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.model.MessageModel;
import ru.sqwk.ssn.repo.MessageRepo;
import ru.sqwk.ssn.service.MessageService;

import java.util.List;

@Service
@AllArgsConstructor
public class MessageServiceImpl implements MessageService {

  private final MessageRepo messageRepo;

  @Override
  public List<MessageModel> getChats(Long userId) {
    return messageRepo.getChats(userId);
  }

  @Override
  public List<MessageModel> getChat(Long userId, Long friendId) {
    return messageRepo.getMessages(userId, friendId);
  }
}
