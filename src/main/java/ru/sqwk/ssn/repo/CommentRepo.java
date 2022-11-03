package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.domain.Comment;
import ru.sqwk.ssn.model.CommentModel;

public interface CommentRepo {
    void deleteComment(Long commentId);
    Long save(Comment comment);
    CommentModel getComment(Long authorId, Long commentId);
}
