package ru.sqwk.ssn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Friendship {
    private Long user1;
    private Long user2;
    private String category;
    private String timestamp;
}
