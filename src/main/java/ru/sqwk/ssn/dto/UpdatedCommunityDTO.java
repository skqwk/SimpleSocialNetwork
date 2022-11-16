package ru.sqwk.ssn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdatedCommunityDTO {
  private String name;
  private String topic;
  private Integer ageLimit;
}
