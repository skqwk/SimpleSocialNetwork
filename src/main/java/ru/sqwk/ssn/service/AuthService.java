package ru.sqwk.ssn.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.sqwk.ssn.security.UserCredentials;

public interface AuthService extends UserDetailsService {

    void registrate(UserCredentials userCredentials);
}
