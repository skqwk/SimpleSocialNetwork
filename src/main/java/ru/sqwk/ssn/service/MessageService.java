package ru.sqwk.ssn.service;


import ru.sqwk.ssn.domain.Message;
import ru.sqwk.ssn.dto.MessageDTO;
import ru.sqwk.ssn.model.MessageModel;
import ru.sqwk.ssn.model.ProcessedMessageModel;

import java.util.List;

public interface MessageService {

    List<MessageModel> getChats(Long userId);

    List<MessageModel> getChat(Long id, Long friendId);

    ProcessedMessageModel saveMessage(MessageDTO messageDTO);
}
