package ru.sqwk.ssn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Likes {
    private Long userId;
    private Long postId;
    private String timestamp;
}
