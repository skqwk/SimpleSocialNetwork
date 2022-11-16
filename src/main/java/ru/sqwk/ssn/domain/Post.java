package ru.sqwk.ssn.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class Post {
  private Long id;
  private String createdAt;
  private String content;
  private Long authorId;
}
