package ru.sqwk.ssn.service;


import ru.sqwk.ssn.model.MessageModel;

import java.util.List;

public interface MessageService {

    List<MessageModel> getChats(Long userId);

    List<MessageModel> getChat(Long id, Long friendId);
}
