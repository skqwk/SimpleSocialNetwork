package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.domain.Friendship;
import ru.sqwk.ssn.repo.FriendshipRepo;

@Repository
@AllArgsConstructor
public class FriendshipRepoJdbc implements FriendshipRepo {

    private final JdbcTemplate jdbc;

    @Override
    public void delete(Long userId, Long friendId) {
        String query = "DELETE FROM friendship WHERE user1 = ? AND user2 = ?;";
        jdbc.update(query, userId, friendId);
        jdbc.update(query, friendId, userId);
    }

    @Override
    public void save(Friendship friendship) {
        String query = "INSERT INTO friendship(user1, user2, timestamp, category) VALUES(?, ?, ?, ?);";

        jdbc.update(query, friendship.getUser1(), friendship.getUser2(), friendship.getTimestamp(), friendship.getCategory());


    }
}
