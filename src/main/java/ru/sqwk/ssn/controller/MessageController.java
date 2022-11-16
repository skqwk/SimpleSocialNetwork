package ru.sqwk.ssn.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sqwk.ssn.dto.UpdatedMessageDTO;
import ru.sqwk.ssn.service.MessageService;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping("api/v1")
public class MessageController {
  private final MessageService messageService;

  @DeleteMapping("/messages/{messageId}")
  public void deleteMessage(@PathVariable Long messageId) {
    messageService.deleteMessage(messageId);
  }

  @PutMapping("/messages/{messageId}")
  public void updateMessage(
      @PathVariable Long messageId, @RequestBody UpdatedMessageDTO messageDTO) {
    messageService.updateMessage(messageId, messageDTO);
  }

  @PatchMapping("/messages/{messageId}")
  public void setMessageRead(@PathVariable Long messageId) {
    messageService.markMessageAsRead(messageId);
  }
}
