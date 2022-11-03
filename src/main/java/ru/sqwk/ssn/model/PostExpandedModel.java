package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class PostExpandedModel {
    private Long id;
    private AuthorModel author;
    private String content;
    private String createdAt;
    private int amountLikes;
    private List<CommentModel> comments;
    private Boolean isEditable;
}
