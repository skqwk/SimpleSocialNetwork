package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.User;
import ru.sqwk.ssn.repo.UserRepo;
import ru.sqwk.ssn.security.UserCredentials;
import ru.sqwk.ssn.security.UserRole;
import ru.sqwk.ssn.service.AuthService;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final UserRepo userRepo;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepo
        .findByLogin(username)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));
  }

  @Override
  public void registrate(UserCredentials userCredentials) {
    log.info("User with login = {} want to register", userCredentials.getLogin());
    if (userRepo.findByLogin(userCredentials.getLogin()).isEmpty()) {

      User registeredUser =
          User.builder()
              .role(UserRole.USER.toString())
              .password(passwordEncoder.encode(userCredentials.getPassword()))
              .login(userCredentials.getLogin())
              .email(userCredentials.getEmail())
              .birthdate(userCredentials.getBirthDate())
              .fullName(userCredentials.getFullName())
              .build();
      log.info("Save new user with login = {}", userCredentials.getLogin());
      userRepo.saveRegisteredUser(registeredUser);
    } else {
      log.error("User with login = {} already exists!", userCredentials.getLogin());
      throw new IllegalStateException(
          String.format("Пользователь с логином = %s уже существует!", userCredentials.getLogin()));
    }
  }
}
