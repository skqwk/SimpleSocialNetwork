package ru.sqwk.ssn.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import ru.sqwk.ssn.domain.Message;
import ru.sqwk.ssn.dto.MessageDTO;

@Slf4j
@Controller
public class ChatWebSocketController {

  //  @GetMapping("/webs")
  //  public String websPage() {
  //    return "index.html";
  //  }

  @MessageMapping("/chat")
  @SendTo("/topic/messages")
  public Message send(MessageDTO messageDTO) {
    log.info(
        "Broker get messages. Content = '{}', to = '{}'",
        messageDTO.getContent(),
        messageDTO.getTo());
    return Message.builder()
        // from(messageDTO.getFrom())
        .content(messageDTO.getContent())
        // .sentAt(new SimpleDateFormat("HH:mm").format(new Date()))
        .build();
  }
}
