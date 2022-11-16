package ru.sqwk.ssn.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum UserRole {
  USER,
  ADMIN;

  public List<SimpleGrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(String.format("ROLE_%s", this.name())));
  }
}
