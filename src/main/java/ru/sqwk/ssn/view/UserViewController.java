package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.sqwk.ssn.service.UserService;

@Controller
@AllArgsConstructor
public class UserViewController {

    private final UserService userService;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/users/{id}")
    public String user(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user";
    }

}
