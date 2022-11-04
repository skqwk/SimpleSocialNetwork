package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sqwk.ssn.controller.UserController;
import ru.sqwk.ssn.model.UserProfileModel;
import ru.sqwk.ssn.service.UserService;

@Controller
@AllArgsConstructor
public class UserViewController {

    private final UserController userController;
    private final UserService userService;

    @GetMapping("/users")
    public String users(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/friends")
    public String friends(Model model) {
        model.addAttribute("friends", userService.getFriends());
        return "friends";
    }

    @GetMapping("/users/{id}")
    public String user(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "user";
    }
//
//    @PostMapping("/user/update")
//    public String updateUser(@ModelAttribute("user") UserProfileModel userProfileModel) {
//        userController.
//    }

}
