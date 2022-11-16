package ru.sqwk.ssn.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentModel {
  private Long id;
  private String createdAt;
  private AuthorModel author;
  private String content;
  private Boolean isEditable;
}
