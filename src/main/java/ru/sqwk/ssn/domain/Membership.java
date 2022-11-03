package ru.sqwk.ssn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Membership {
    private Long userId;
    private Long communityId;
    private String entryDate;
}
