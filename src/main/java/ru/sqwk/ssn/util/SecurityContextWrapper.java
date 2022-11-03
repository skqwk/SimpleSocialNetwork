package ru.sqwk.ssn.util;

import org.springframework.security.core.context.SecurityContextHolder;
import ru.sqwk.ssn.security.UserAccount;

public class SecurityContextWrapper {
    public static UserAccount getNowUser() {
        return (UserAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
