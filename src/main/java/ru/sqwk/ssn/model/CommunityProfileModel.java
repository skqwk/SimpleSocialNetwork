package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CommunityProfileModel {
  private Long id;
  private String name;
  private String topic;
  private String creationDate;
  private List<CommunityMemberModel> members;
  private Boolean beIn;
  private String ageLimit;
  private Boolean isSuitForAgeLimit;
}
