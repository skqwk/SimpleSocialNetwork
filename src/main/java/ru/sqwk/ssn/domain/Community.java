package ru.sqwk.ssn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Community {
    private Long id;
    private String name;
    private String creationDate;
    private String topic;
    private Integer ageLimit;
}
