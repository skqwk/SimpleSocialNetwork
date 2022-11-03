package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommunityModel {
    private String name;
    private Integer amountMembers;
    private String topic;
    private Long id;
}
