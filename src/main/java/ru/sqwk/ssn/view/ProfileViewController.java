package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.UserService;

@Slf4j
@Controller
@AllArgsConstructor
public class ProfileViewController {

    private final UserService userService;

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserAccount userAccount, Model model) {
        log.info("Load profile for user with id = {}", userAccount.getId());
        model.addAttribute("user", userService.getUser(userAccount.getId()));
        return "user";
    }

}
