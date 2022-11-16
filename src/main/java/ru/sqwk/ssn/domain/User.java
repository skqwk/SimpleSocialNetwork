package ru.sqwk.ssn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class User {
  private Long id;
  private String password;
  private String login;
  private String role;
  private String email;
  private String birthdate;
  private String fullName;
}
