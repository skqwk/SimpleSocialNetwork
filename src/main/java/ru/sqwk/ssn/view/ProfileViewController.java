package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.UserService;

@Controller
@AllArgsConstructor
public class ProfileViewController {

    private final UserService userService;

    @GetMapping("/profile")
    public String profile(@AuthenticationPrincipal UserAccount userAccount, Model model) {
        model.addAttribute("user", userService.getUser(userAccount.getId()));
        return "user";
    }

}
