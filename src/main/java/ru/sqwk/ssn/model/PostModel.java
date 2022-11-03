package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostModel {
    private Long id;
    private AuthorModel author;
    private String content;
    private String createdAt;
    private int amountLikes;
    private Boolean isLiked;
    private Boolean isEditable;
}
