package ru.sqwk.ssn.websocket;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import ru.sqwk.ssn.domain.Message;
import ru.sqwk.ssn.dto.MessageDTO;
import ru.sqwk.ssn.model.ProcessedMessageModel;
import ru.sqwk.ssn.service.MessageService;

@Slf4j
@Controller
@AllArgsConstructor
public class ChatWebSocketController {

  private final SimpMessagingTemplate messagingTemplate;
  private final MessageService messageService;

  @MessageMapping("/chat")
  public void processMessage(@Payload MessageDTO messageDTO) {
    log.info("Get message in broker, to = {}, content = {}", messageDTO.getTo(), messageDTO.getContent());

    ProcessedMessageModel message = messageService.saveMessage(messageDTO);
    messagingTemplate.convertAndSendToUser(message.getRecipientLogin(), "/queue/messages", message);
    messagingTemplate.convertAndSendToUser(message.getSenderLogin(), "/queue/messages", message);
  }
}
