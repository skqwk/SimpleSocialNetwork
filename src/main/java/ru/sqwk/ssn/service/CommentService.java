package ru.sqwk.ssn.service;

import ru.sqwk.ssn.model.CommentModel;

public interface CommentService {
    void deleteComment(Long commentId);

    CommentModel addComment(Long authorId, String commentContent, Long postId);
}
