package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.Comment;
import ru.sqwk.ssn.model.CommentModel;
import ru.sqwk.ssn.repo.CommentRepo;
import ru.sqwk.ssn.service.CommentService;
import ru.sqwk.ssn.util.Formatter;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

  private final CommentRepo commentRepo;

  @Override
  public void deleteComment(Long commentId) {
    commentRepo.deleteComment(commentId);
  }

  @Override
  public CommentModel addComment(Long authorId, String commentContent, Long postId) {
    Comment comment =
        Comment.builder()
            .postId(postId)
            .content(commentContent)
            .authorId(authorId)
            .createdAt(Formatter.format(new Date()))
            .build();
    Long commentId = commentRepo.save(comment);
    log.info("Create comment with id = {}", commentId);
    return getComment(authorId, commentId);
  }

  private CommentModel getComment(Long authorId, Long commentId) {
    return commentRepo.getComment(authorId, commentId);
  }
}
