package ru.sqwk.ssn.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UpdatedUserDTO {
    private String login;
    private String email;
    private String fullName;
    private String birthDate;
}
