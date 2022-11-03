package ru.sqwk.ssn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Comment {
    private Long id;
    private String content;
    private Long postId;
    private Long authorId;
    private String createdAt;
}
