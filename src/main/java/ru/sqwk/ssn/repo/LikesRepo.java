package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.domain.Likes;

public interface LikesRepo {
    void addLikes(Likes likes);
    void deleteLikes(Long userId, Long postId);
}
