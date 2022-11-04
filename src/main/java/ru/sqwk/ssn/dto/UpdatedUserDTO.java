package ru.sqwk.ssn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedUserDTO {
  private String login;
  private String email;
  private String fullName;
  private String birthDate;
}
