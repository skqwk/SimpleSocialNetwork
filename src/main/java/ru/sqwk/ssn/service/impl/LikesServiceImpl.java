package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.Likes;
import ru.sqwk.ssn.repo.LikesRepo;
import ru.sqwk.ssn.service.LikesService;
import ru.sqwk.ssn.util.Formatter;

import java.util.Date;

@Service
@AllArgsConstructor
public class LikesServiceImpl implements LikesService {
    private final LikesRepo likesRepo;

    @Override
    public void deleteLike(Long userId, Long postId) {
        likesRepo.deleteLikes(userId, postId);
    }

    @Override
    public void addLike(Long userId, Long postId) {
        Likes likes = Likes.builder()
                .postId(postId)
                .userId(userId)
                .timestamp(Formatter.format(new Date()))
                .build();
        likesRepo.addLikes(likes);

    }
}
