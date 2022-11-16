package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.domain.Comment;
import ru.sqwk.ssn.model.CommentModel;
import ru.sqwk.ssn.repo.CommentRepo;
import ru.sqwk.ssn.repo.UserRepo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class CommentRepoJdbc implements CommentRepo {

  private final JdbcTemplate jdbc;
  private final UserRepo userRepo;

  @Override
  public void deleteComment(Long commentId) {
    String query = "DELETE FROM comment WHERE comment_id = ?;";
    jdbc.update(query, commentId);
  }

  @Override
  public Long save(Comment comment) {
    String query =
        "INSERT INTO comment(timestamp, content, comment_author, post_id) VALUES (?, ?, ?, ?);";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbc.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
          ps.setString(1, comment.getCreatedAt());
          ps.setString(2, comment.getContent());
          ps.setLong(3, comment.getAuthorId());
          ps.setLong(4, comment.getPostId());
          return ps;
        },
        keyHolder);
    return keyHolder.getKey().longValue();
  }

  @Override
  public CommentModel getComment(Long authorId, Long commentId) {
    String query =
        "SELECT post_id, "
            + "comment_author, "
            + "content, "
            + "timestamp, "
            + "comment_id, "
            + "case when comment_author = ? then 1 else 0 end as is_editable "
            + "FROM comment WHERE comment_id = ?;";
    return jdbc.queryForObject(query, this::mapResultSetToCommentModel, authorId, commentId);
  }

  private List<CommentModel> getComments(Long postId) {
    String query =
        "SELECT post_id, comment_author, content, timestamp, comment_id FROM comment WHERE post_id = ? ORDER BY timestamp DESC;";
    return jdbc.query(query, this::mapResultSetToCommentModel, postId);
  }

  private CommentModel mapResultSetToCommentModel(ResultSet rs, int rowNum) throws SQLException {
    return CommentModel.builder()
        .id(rs.getLong("comment_id"))
        .createdAt(rs.getString("timestamp"))
        .content(rs.getString("content"))
        .isEditable(rs.getBoolean("is_editable"))
        .author(userRepo.getAuthor(rs.getLong("comment_author")))
        .build();
  }
}
