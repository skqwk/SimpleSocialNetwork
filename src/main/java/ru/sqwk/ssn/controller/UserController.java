package ru.sqwk.ssn.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ru.sqwk.ssn.dto.UpdatedUserDTO;
import ru.sqwk.ssn.model.UserModel;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.UserService;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/users")
    List<UserModel> findAll(@RequestParam(name = "name", required = false, defaultValue = "") String name) {
        if (name.isEmpty()) {
            return userService.getUsers();

        } else {
            return userService.getUsersByName(name);
        }
    }

    @PutMapping("/user")
    void update(@AuthenticationPrincipal UserAccount userAccount, @RequestBody UpdatedUserDTO updatedUserDTO) {
        userService.updateUser(userAccount.getId(), updatedUserDTO);
    }


}