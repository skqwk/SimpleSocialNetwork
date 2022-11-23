package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.domain.FriendshipRequest;
import ru.sqwk.ssn.model.CommunityMemberModel;
import ru.sqwk.ssn.model.FriendshipRequestModel;
import ru.sqwk.ssn.repo.FriendshipRequestsRepo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class FriendshipRequestsRepoJdbc implements FriendshipRequestsRepo {
    private final JdbcTemplate jdbc;

    @Override
    public List<FriendshipRequestModel> findAllOutcomingRequestsByUserId(Long userId) {
        String selectOutcomingRequests = "SELECT sender, recipient, timestamp, login, status FROM friendship_request FR " +
                "LEFT JOIN user U ON FR.recipient = U.user_id " +
                "WHERE sender = ?;";
        List<FriendshipRequestModel> requests = jdbc.query(selectOutcomingRequests,
                this::mapResultSetToFriendshipRequestModel, userId);
        return requests;
    }

    @Override
    public List<FriendshipRequestModel> findAllIncomingRequestsByUserId(Long userId) {
        String selectIncomingRequests = "SELECT sender, recipient, timestamp, login, status FROM friendship_request FR " +
                "LEFT JOIN user U ON FR.sender = U.user_id " +
                "WHERE recipient = ?;";
        List<FriendshipRequestModel> requests = jdbc.query(selectIncomingRequests,
                this::mapResultSetToFriendshipRequestModel, userId);
        return requests;
    }

    @Override
    public void delete(Long senderId, Long recipientId) {
        String query = "DELETE FROM friendship_request WHERE sender = ? AND recipient = ?;";
        jdbc.update(query, senderId, recipientId);
    }

    @Override
    public boolean exists(Long senderId, Long recipientId) {
        String query = "SELECT has_request(?, ?) as has_request;";
        return Boolean.TRUE.equals(jdbc.queryForObject(query, Boolean.class, senderId, recipientId));
    }

    @Override
    public void save(FriendshipRequest request) {
        String query = "INSERT INTO friendship_request(sender, recipient, timestamp, status) " +
                "VALUES(?, ?, ?, ?) ON DUPLICATE KEY UPDATE " +
                "timestamp = VALUES(timestamp), status = VALUES(status);";

        jdbc.update(
                query,
                request.getSenderId(),
                request.getRecipientId(),
                request.getTimestamp(),
                request.getStatus());
    }

    private FriendshipRequestModel mapResultSetToFriendshipRequestModel(ResultSet rs, Integer rowNum) throws SQLException {
        return FriendshipRequestModel.builder()
                .senderId(rs.getLong("sender"))
                .recipientId(rs.getLong("recipient"))
                .login(rs.getString("login"))
                .status(rs.getString("status"))
                .timestamp(rs.getString("timestamp"))
                .build();
    }
}
