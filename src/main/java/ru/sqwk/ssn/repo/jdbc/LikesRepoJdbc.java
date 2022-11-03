package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.domain.Likes;
import ru.sqwk.ssn.repo.LikesRepo;

@Repository
@AllArgsConstructor
public class LikesRepoJdbc implements LikesRepo {

    private final JdbcTemplate jdbc;

    @Override
    public void addLikes(Likes likes) {
        String query = "INSERT INTO likes(timestamp, post_id, user_id) VALUES(?, ?, ?);";
        jdbc.update(query, likes.getTimestamp(), likes.getPostId(), likes.getUserId());
    }

    @Override
    public void deleteLikes(Long userId, Long postId) {
        String query = "DELETE FROM likes WHERE user_id = ? AND post_id = ?;";
        jdbc.update(query, userId, postId);
    }
}
