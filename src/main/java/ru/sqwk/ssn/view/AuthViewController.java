package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sqwk.ssn.security.UserCredentials;
import ru.sqwk.ssn.service.AuthService;

import javax.validation.Valid;

@Slf4j
@Controller
@AllArgsConstructor
public class AuthViewController {

  private final AuthService authService;

  @GetMapping("/login")
  public String login() {
    return "login";
  }

  @GetMapping("/login-error")
  public String loginError(Model model) {
    model.addAttribute("loginError", true);
    return "login";
  }

  @GetMapping("/registration")
  public String registration(Model model) {
    model.addAttribute("credentials", new UserCredentials());
    return "registration";
  }

  @PostMapping("/registration")
  public String registration(
      @Valid @ModelAttribute("credentials") UserCredentials userCredentials,
      BindingResult result,
      Model model) {
    if (result.hasErrors()) {
      log.error("Input has errors!");
      System.out.println(result.getModel().toString());
      return "redirect:registration";
    }
    try {
      authService.registrate(userCredentials);
    } catch (Exception ex) {
        model.addAttribute("hasError", true);
        model.addAttribute("reason", ex.getMessage());
        model.addAttribute("credentials", userCredentials);
        return "registration";
    }
    return "/login";
  }
}
