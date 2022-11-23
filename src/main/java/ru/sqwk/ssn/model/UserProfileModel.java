package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserProfileModel {
  private String fullName;
  private String login;
  private Long id;
  private String birthDate;
  private String email;
  private Boolean isFriend;
  private Boolean hasRequest;
}
