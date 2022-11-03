package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommunityMemberModel {
    private String login;
    private Long id;
    private String entryDate;
}
