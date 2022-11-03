package ru.sqwk.ssn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Friendship {
    private Integer user1;
    private Integer user2;
    private String category;
    private String timestamp;
}
