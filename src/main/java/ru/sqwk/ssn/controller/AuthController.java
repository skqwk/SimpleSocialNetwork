package ru.sqwk.ssn.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sqwk.ssn.model.UserLogin;
import ru.sqwk.ssn.security.UserAccount;

@RestController
@RequestMapping("api/v1")
public class AuthController {

  @GetMapping("/auth")
  public UserLogin getNowUser(@AuthenticationPrincipal UserAccount userAccount) {
    return UserLogin.builder()
            .login(userAccount.getLogin())
            .build();
  }
}
