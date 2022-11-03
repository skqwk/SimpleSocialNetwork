package ru.sqwk.ssn.view;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ru.sqwk.ssn.security.UserCredentials;
import ru.sqwk.ssn.service.AuthService;
import ru.sqwk.ssn.service.UserService;

@Controller
@AllArgsConstructor
public class AuthViewController {

    private final AuthService authService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("credentials", new UserCredentials());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("credentials") UserCredentials userCredentials) {
        authService.registrate(userCredentials);
        return "/login";
    }
}
