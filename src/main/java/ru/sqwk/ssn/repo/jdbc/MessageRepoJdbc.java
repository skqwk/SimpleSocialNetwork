package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.domain.Message;
import ru.sqwk.ssn.model.MessageChatModel;
import ru.sqwk.ssn.model.MessageModel;
import ru.sqwk.ssn.repo.MessageRepo;
import ru.sqwk.ssn.repo.UserRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MessageRepoJdbc implements MessageRepo {

  private final JdbcTemplate jdbc;
  private final UserRepo userRepo;

  @Override
  public List<MessageChatModel> getChats(Long userId) {
    String query =
            "SELECT login as friend_login, message_id, recipient, sender, content, has_been_read, time, own, friend FROM ("
        + "SELECT T.friend, MS.message_id, MS.recipient, MS.sender, MS.content, MS.has_been_read, T.time, CASE WHEN sender = ? THEN 1 ELSE 0 END as own FROM ("
            + "SELECT max(M.timestamp) as time, F.friend FROM "
            + "(SELECT DISTINCT user2 as friend FROM friendship WHERE user1 = ? "
            + "UNION "
            + "SELECT DISTINCT user1 as friend FROM friendship where user2 = ?) F "
            + "INNER JOIN "
            + "(SELECT * FROM message WHERE sender = ? OR recipient = ?) M "
            + "ON F.friend = M.sender or M.recipient = F.friend group by F.friend) T, "
            + "    message MS "
            + "WHERE T.time = MS.timestamp AND (T.friend = MS.sender OR T.friend = MS.recipient)) CH, user where CH.friend = user.user_id;";

    return jdbc.query(
        query, this::mapResultSetToMessageChatModel, userId, userId, userId, userId, userId);
  }

  @Override
  public List<MessageModel> getMessages(Long userId, Long friendId) {
    String query =
        "SELECT ? as friend, M.recipient, M.sender, M.content, M.has_been_read, M.timestamp as time, M.message_id, "
            + "CASE WHEN sender = ? THEN 1 ELSE 0 END as own, "
            + "CASE WHEN ((DATE_ADD(NOW(), interval 3 hour) - M.timestamp) < 300) THEN 1 ELSE 0 END as can_edit "
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

  @Override
  public Optional<Message> getMessage(Long messageId) {
    String query = "SELECT * FROM message WHERE message_id = ?;";
    Message message = jdbc.queryForObject(query, this::mapResultSetToMessage, messageId);
    return Optional.ofNullable(message);
  }

  private Message mapResultSetToMessage(ResultSet rs, int rowNum) throws SQLException {
    return Message.builder()
        .id(rs.getLong("message_id"))
        .content(rs.getString("content"))
        .hasBeenRead(rs.getBoolean("has_been_read"))
        .senderId(rs.getLong("sender"))
        .recipientId(rs.getLong("recipient"))
        .timestamp(rs.getString("timestamp"))
        .build();
  }

  @Override
  public Long save(Message message) {
    String query =
        "INSERT INTO message(recipient, sender, has_been_read, content, timestamp) "
            + "VALUES (?, ?, ?, ?, ?);";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbc.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
          ps.setLong(1, message.getRecipientId());
          ps.setLong(2, message.getSenderId());
          ps.setBoolean(3, message.getHasBeenRead());
          ps.setString(4, message.getContent());
          ps.setString(5, message.getTimestamp());
          return ps;
        },
        keyHolder);
    return keyHolder.getKey().longValue();
  }

<<<<<<< HEAD
  @Override
  public void delete(Long messageId) {
    String query = "DELETE FROM message WHERE message_id = ?;";
    jdbc.update(query, messageId);
  }

  @Override
  public void update(Long messageId, String messageContent) {
    String query = "UPDATE message SET content = ? WHERE message_id = ?";
    jdbc.update(query, messageContent, messageId);
  }

  @Override
  public void markMessageAsRead(Long messageId) {
    String query = "UPDATE message SET has_been_read = 1 WHERE message_id = ?;";
    jdbc.update(query, messageId);
  }

  private MessageModel mapResultSetToMessageModel(ResultSet rs, int rowNum) throws SQLException {
=======
    @Override
    public void delete(Long messageId) {
        String query = "DELETE FROM message WHERE message_id = ?;";
        jdbc.update(query, messageId);
    }

    @Override
    public void update(Long messageId, String messageContent) {
        String query = "UPDATE message SET content = ? WHERE message_id = ?";
        jdbc.update(query, messageContent, messageId);
    }

    @Override
    public void markMessageAsRead(Long messageId) {
        String query = "UPDATE message SET has_been_read = 1 WHERE message_id = ?;";
        jdbc.update(query, messageId);
    }

    private MessageChatModel mapResultSetToMessageChatModel(ResultSet rs, int rowNum) throws SQLException {
    return MessageChatModel.builder()
        .id(rs.getLong("message_id"))
        .content(rs.getString("content"))
        .hasBeenRead(rs.getBoolean("has_been_read"))
        .own(rs.getBoolean("own"))
        .author(userRepo.getAuthor(rs.getLong("sender")))
        .sentAt(rs.getString("time"))
        .friendId(rs.getLong("friend"))
        .friendLogin(rs.getString("friend_login"))
        .build();
  }
    private MessageModel mapResultSetToMessageModel(ResultSet rs, int rowNum) throws SQLException {
>>>>>>> 283451c58d4d4de5e1f49b034df582e4152981f7
    return MessageModel.builder()
        .id(rs.getLong("message_id"))
        .content(rs.getString("content"))
        .hasBeenRead(rs.getBoolean("has_been_read"))
        .own(rs.getBoolean("own"))
        .author(userRepo.getAuthor(rs.getLong("sender")))
        .sentAt(rs.getString("time"))
        .friendId(rs.getLong("friend"))
        .canEdit(rs.getBoolean("can_edit"))
        .build();
  }
}
