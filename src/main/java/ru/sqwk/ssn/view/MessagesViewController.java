package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sqwk.ssn.model.FriendChatModel;
import ru.sqwk.ssn.model.MessageModel;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.FriendshipService;
import ru.sqwk.ssn.service.MessageService;
import ru.sqwk.ssn.service.UserService;

import java.util.List;

@Controller
@AllArgsConstructor
@Slf4j
public class MessagesViewController {

  private final MessageService messageService;
  private final FriendshipService friendshipService;
  private final UserService userService;

  @GetMapping("/messages")
  public String chats(@AuthenticationPrincipal UserAccount userAccount, Model model) {
    List<MessageModel> chats = messageService.getChats(userAccount.getId());
    List<FriendChatModel> friendChats = userService.getFriendChats();
    model.addAttribute("messages", chats);
    model.addAttribute("friendChats", friendChats);
    log.info("Get {} chats by userId = {}", chats.size(), userAccount.getId());
    return "messages";
  }

  @GetMapping("/chat")
  public String chat(
      @AuthenticationPrincipal UserAccount userAccount,
      @RequestParam("friend") Long friendId,
      Model model) {
    List<MessageModel> messages = messageService.getChat(userAccount.getId(), friendId);
    model.addAttribute("messages", messages);
    model.addAttribute("sender", userAccount.getLogin());
    model.addAttribute("recipient", userService.getUser(friendId).getLogin());
    model.addAttribute("recipientId", friendId);
    return "chat";
  }
}
