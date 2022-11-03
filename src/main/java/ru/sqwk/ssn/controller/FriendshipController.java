package ru.sqwk.ssn.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.FriendshipService;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;


    @PostMapping("friendship/{friendId}")
    public void addFriendship(@AuthenticationPrincipal UserAccount userAccount, @PathVariable("friendId") Long friendId) {
        friendshipService.addFriendship(userAccount.getId(), friendId);
    }
    @DeleteMapping("friendship/{friendId}")
    public void removeFriendship(@AuthenticationPrincipal UserAccount userAccount, @PathVariable("friendId") Long friendId) {
        friendshipService.removeFriendship(userAccount.getId(), friendId);
    }


}
