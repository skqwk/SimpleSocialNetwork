package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.model.MessageModel;
import ru.sqwk.ssn.repo.MessageRepo;
import ru.sqwk.ssn.repo.UserRepo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class MessageRepoJdbc implements MessageRepo {

  private final JdbcTemplate jdbc;
  private final UserRepo userRepo;

  @Override
  public List<MessageModel> getChats(Long userId) {
    String query =
        "SELECT T.friend, MS.message_id, MS.recipient, MS.sender, MS.content, MS.has_been_read, T.time, CASE WHEN sender = ? THEN 1 ELSE 0 END as own FROM ("
            + "SELECT max(M.timestamp) as time, F.friend FROM "
            + "(SELECT DISTINCT user2 as friend FROM friendship WHERE user1 = ? "
            + "UNION "
            + "SELECT DISTINCT user1 as friend FROM friendship where user2 = ?) F "
            + "INNER JOIN "
            + "(SELECT * FROM message WHERE sender = ? OR recipient = ?) M "
            + "ON F.friend = M.sender or M.recipient = F.friend group by F.friend) T, "
            + "    message MS "
            + "WHERE T.time = MS.timestamp AND (T.friend = MS.sender OR T.friend = MS.recipient);";

    return jdbc.query(
        query, this::mapResultSetToMessageModel, userId, userId, userId, userId, userId);
  }

  @Override
  public List<MessageModel> getMessages(Long userId, Long friendId) {
    String query =
        "SELECT ? as friend, M.recipient, M.sender, M.content, M.has_been_read, M.timestamp as time, M.message_id, "
            + "CASE WHEN sender = ? THEN 1 ELSE 0 END as own "
            + "FROM message M "
            + "    WHERE (M.recipient = ? AND M.sender = ?) OR (M.recipient = ? and M.sender = ?) "
            + "ORDER BY timestamp DESC;";

    return jdbc.query(
        query,
        this::mapResultSetToMessageModel,
        friendId,
        userId,
        userId,
        friendId,
        friendId,
        userId);
  }

  private MessageModel mapResultSetToMessageModel(ResultSet rs, int rowNum) throws SQLException {
    return MessageModel.builder()
        .id(rs.getLong("message_id"))
        .content(rs.getString("content"))
        .hasBeenRead(rs.getBoolean("has_been_read"))
        .own(rs.getBoolean("own"))
        .author(userRepo.getAuthor(rs.getLong("sender")))
        .sentAt(rs.getString("time"))
        .friendId(rs.getLong("friend"))
        .build();
  }
}
