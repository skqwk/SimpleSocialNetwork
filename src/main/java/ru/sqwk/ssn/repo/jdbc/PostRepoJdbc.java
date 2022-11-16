package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.domain.Post;
import ru.sqwk.ssn.model.CommentModel;
import ru.sqwk.ssn.model.PostExpandedModel;
import ru.sqwk.ssn.model.PostModel;
import ru.sqwk.ssn.repo.PostRepo;
import ru.sqwk.ssn.repo.UserRepo;
import ru.sqwk.ssn.util.SecurityContextWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
@AllArgsConstructor
public class PostRepoJdbc implements PostRepo {

  private final JdbcTemplate jdbc;
  private final UserRepo userRepo;

  @Override
  public List<PostModel> getPosts(Long userId) {
    String query =
        "SELECT post_id, "
            + "post_author, "
            + "content, "
            + "timestamp, "
            + "count_post_likes(post_id) as likes, "
            + "check_is_liked(?, post_id) as is_liked, "
            + "case when post_author = ? then 1 else 0 end as is_editable "
            + "FROM post ORDER BY timestamp DESC;";
    return jdbc.query(query, this::mapResultSetToPostModel, userId, userId);
  }

  @Override
  public PostModel getPost(Long userId, Long postId) {
    String query =
        "SELECT post_id, "
            + "post_author, "
            + "content, "
            + "timestamp, "
            + "count_post_likes(post_id) as likes, "
            + "check_is_liked(?, ?) as is_liked, "
            + "1 as is_editable "
            + "FROM post WHERE post_id = ?;";
    return jdbc.queryForObject(query, this::mapResultSetToPostModel, userId, postId, postId);
  }

  @Override
  public Long save(Post post) {
    String query = "INSERT INTO post(timestamp, content, post_author) VALUES (?, ?, ?);";
    KeyHolder keyHolder = new GeneratedKeyHolder();

    jdbc.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
          ps.setString(1, post.getCreatedAt());
          ps.setString(2, post.getContent());
          ps.setLong(3, post.getAuthorId());
          return ps;
        },
        keyHolder);
    return keyHolder.getKey().longValue();
  }

  @Override
  public PostExpandedModel getExpandedPost(Long authorId, Long postId) {
    String query =
        "SELECT post_id, "
            + "post_author, "
            + "content, "
            + "timestamp, "
            + "count_post_likes(post_id) as likes, "
            + "check_is_liked(?, ?) as is_liked, "
            + "case when post_author = ? then 1 else 0 end as is_editable "
            + "FROM post WHERE post_id = ?;";
    return jdbc.queryForObject(
        query, this::mapResultSetToPostExpandedModel, authorId, postId, authorId, postId);
  }

  @Override
  public void deletePost(Long postId) {
    String query = "DELETE FROM post WHERE post_id = ?;";
    jdbc.update(query, postId);
  }

  @Override
  public void update(Post post) {
    String query = "UPDATE post SET content = ? WHERE post_id = ?;";
    jdbc.update(query, post.getContent(), post.getId());
  }

  PostModel mapResultSetToPostModel(ResultSet rs, Integer rowNum) throws SQLException {
    return PostModel.builder()
        .id(rs.getLong("post_id"))
        .createdAt(rs.getString("timestamp"))
        .content(rs.getString("content"))
        .author(userRepo.getAuthor(rs.getLong("post_author")))
        .amountLikes(rs.getInt("likes"))
        .isLiked(rs.getBoolean("is_liked"))
        .isEditable(rs.getBoolean("is_editable"))
        .build();
  }

  PostExpandedModel mapResultSetToPostExpandedModel(ResultSet rs, Integer rowNum)
      throws SQLException {
    return PostExpandedModel.builder()
        .id(rs.getLong("post_id"))
        .comments(getComments(SecurityContextWrapper.getNowUser().getId(), rs.getLong("post_id")))
        .createdAt(rs.getString("timestamp"))
        .content(rs.getString("content"))
        .author(userRepo.getAuthor(rs.getLong("post_author")))
        .amountLikes(rs.getInt("likes"))
        .isEditable(rs.getBoolean("is_editable"))
        .build();
  }

  private List<CommentModel> getComments(Long userId, Long postId) {
    String query =
        "SELECT post_id, "
            + "comment_author, "
            + "content, "
            + "timestamp, "
            + "comment_id, "
            + "case when comment_author = ? then 1 else 0 end as is_editable "
            + "FROM comment WHERE post_id = ? ORDER BY timestamp DESC;";
    return jdbc.query(query, this::mapResultSetToCommentModel, userId, postId);
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
