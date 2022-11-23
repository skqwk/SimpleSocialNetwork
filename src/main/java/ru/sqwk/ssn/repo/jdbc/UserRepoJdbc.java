package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.domain.User;
import ru.sqwk.ssn.dto.UpdatedUserDTO;
import ru.sqwk.ssn.model.AuthorModel;
import ru.sqwk.ssn.model.FriendChatModel;
import ru.sqwk.ssn.model.FriendModel;
import ru.sqwk.ssn.model.UserModel;
import ru.sqwk.ssn.model.UserProfileModel;
import ru.sqwk.ssn.repo.UserRepo;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.security.UserRole;
import ru.sqwk.ssn.util.SecurityContextWrapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Repository
@AllArgsConstructor
public class UserRepoJdbc implements UserRepo {

  private final NamedParameterJdbcTemplate jdbc;

  @Override
  public List<UserModel> getUsers() {
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    SqlParameterSource namedParameters =
        new MapSqlParameterSource().addValue("nowUserId", nowUserId);
    String query =
        "SELECT user_id, full_name, login, "
            + "is_friends(user_id, :nowUserId) AS is_friend, "
            + "has_request(:nowUserId, user_id) AS has_request "
            + "FROM user WHERE role != 'ADMIN' AND user_id != :nowUserId;";
    return jdbc.query(query, namedParameters, this::mapResultSetToUserModel);
  }

  @Override
  public List<FriendModel> getFriends() {
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    SqlParameterSource namedParameters =
        new MapSqlParameterSource().addValue("nowUserId", nowUserId);
    String query = "call db.get_friends(:nowUserId)";
    return jdbc.query(query, namedParameters, this::mapResultSetToFriendModel);
  }

  @Override
  public List<FriendChatModel> getFriendChats() {
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    SqlParameterSource namedParameters =
        new MapSqlParameterSource().addValue("nowUserId", nowUserId);
    String query = "call db.get_friends(?)";
    return jdbc.query(query, namedParameters, this::mapResultSetToFriendChatModel);
  }

  @Override
  public List<FriendModel> getFriendsByName(String name) {
    String nameParam = "%" + name + "%";
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    SqlParameterSource namedParameters =
        new MapSqlParameterSource()
            .addValue("nowUserId", nowUserId)
            .addValue("nameParam", nameParam);
    String query =
        "SELECT * FROM (SELECT * FROM user WHERE (login LIKE :nameParam "
            + "                  OR email LIKE :nameParam"
            + "                  OR full_name LIKE :nameParam) ) U  INNER JOIN  ("
            + "    SELECT user1 as user, category, timestamp FROM friendship F1 where F1.user2 = :nowUserId"
            + "    UNION"
            + "    select user2 as user, category, timestamp from friendship F2 where F2.user1 = :nowUserId) FS"
            + "    on U.user_id = FS.user;";

    return jdbc.query(query, namedParameters, this::mapResultSetToFriendModel);
  }

  private FriendChatModel mapResultSetToFriendChatModel(ResultSet rs, int rowNum)
      throws SQLException {
    return FriendChatModel.builder()
        .friendId(rs.getLong("user_id"))
        .friendLogin(rs.getString("login"))
        .build();
  }

  @Override
  public Optional<UserProfileModel> getUser(Long id) {

    String query =
        "SELECT user_id, full_name, login, email, birthdate, "
            + "is_friends(user_id, :nowUserId) as is_friend, "
            + "has_request(:nowUserId, user_id) as has_request "
            + "FROM user WHERE user_id = :id;";
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    SqlParameterSource namedParameters =
        new MapSqlParameterSource().addValue("nowUserId", nowUserId).addValue("id", id);

    UserProfileModel userProfileModel =
        jdbc.queryForObject(
            query,
            namedParameters,
            (rs, rowNum) ->
                UserProfileModel.builder()
                    .id(rs.getLong("user_id"))
                    .fullName(rs.getString("full_name"))
                    .login(rs.getString("login"))
                    .email(rs.getString("email"))
                    .birthDate(rs.getString("birthdate"))
                    .isFriend(rs.getBoolean("is_friend"))
                    .hasRequest(rs.getBoolean("has_request"))
                    .build());
    return Optional.ofNullable(userProfileModel);
  }

  @Override
  public Optional<UserAccount> findByLogin(String username) {
    log.info("Find user with username = {}", username);
    String query = "SELECT user_id, role, password, login FROM user WHERE login = :username;";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("username", username);
    Optional<UserAccount> userO = Optional.empty();
    try {
      UserAccount userAccount =
          jdbc.queryForObject(query, namedParameters, this::mapResultSetToUserAccount);
      userO = Optional.ofNullable(userAccount);

    } catch (EmptyResultDataAccessException ex) {
      log.info("User with login = {} not found", username);
    }
    return userO;
  }

  @Override
  public void saveRegisteredUser(User user) {
    SqlParameterSource namedParameters =
        new MapSqlParameterSource()
            .addValue("login", user.getLogin())
            .addValue("email", user.getEmail())
            .addValue("role", user.getRole())
            .addValue("fullName", user.getFullName())
            .addValue("birthdate", user.getBirthdate())
            .addValue("password", user.getPassword());
    String query =
        "INSERT INTO user(login, password, email, birthdate, role, full_name) "
            + "VALUES(:login, :password, :email, :birthdate, :role, :fullName);";
    jdbc.update(query, namedParameters);
  }

  public AuthorModel getAuthor(long authorId) {
    String query = "SELECT login, user_id FROM user WHERE user_id = :authorId;";
    SqlParameterSource namedParameters = new MapSqlParameterSource().addValue("authorId", authorId);
    AuthorModel author =
        jdbc.queryForObject(
            query,
            namedParameters,
            (rs, rowNum) ->
                AuthorModel.builder()
                    .id(rs.getLong("user_id"))
                    .login(rs.getString("login"))
                    .build());
    return author;
  }

  @Override
  public List<UserModel> getUsersByName(String name) {
    String nameParam = "%" + name + "%";
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    SqlParameterSource namedParameters =
        new MapSqlParameterSource().addValue("nameParam", name).addValue("nowUserId", nowUserId);
    String query =
        "SELECT user_id, full_name, login, "
            + "is_friends(user_id, :nowUserId) as is_friend, "
            + "has_request(:nowUserId, user_id) as has_request "
            + "FROM user WHERE"
            + "               (login LIKE :nameParam"
            + "            OR email LIKE :nameParam"
            + "            OR full_name LIKE :nameParam) AND user_id != :nowUserId;";
    return jdbc.query(query, namedParameters, this::mapResultSetToUserModel);
  }

  @Override
  public void update(Long id, UpdatedUserDTO updatedUserDTO) {
    SqlParameterSource namedParameters =
        new MapSqlParameterSource()
            .addValue("login", updatedUserDTO.getLogin())
            .addValue("fullName", updatedUserDTO.getFullName())
            .addValue("birthDate", updatedUserDTO.getBirthDate())
            .addValue("id", id)
            .addValue("email", updatedUserDTO.getEmail());
    String query =
        "UPDATE user SET login = :login, full_name = :fullName, email = :email, birthdate = :birthDate WHERE user_id = :id;";
    jdbc.update(query, namedParameters);
  }

  private UserAccount mapResultSetToUserAccount(ResultSet rs, Integer rowNum) throws SQLException {
    return UserAccount.builder()
        .id(rs.getLong("user_id"))
        .login(rs.getString("login"))
        .password(rs.getString("password"))
        .role(UserRole.valueOf(rs.getString("role")))
        .build();
  }

  private UserModel mapResultSetToUserModel(ResultSet rs, Integer rowNum) throws SQLException {
    return UserModel.builder()
        .isFriend(rs.getBoolean("is_friend"))
        .hasRequest(rs.getBoolean("has_request"))
        .id(rs.getLong("user_id"))
        .fullName(rs.getString("full_name"))
        .login(rs.getString("login"))
        .build();
  }

  private FriendModel mapResultSetToFriendModel(ResultSet rs, Integer rowNum) throws SQLException {
    return FriendModel.builder()
        .id(rs.getLong("user_id"))
        .fullName(rs.getString("full_name"))
        .login(rs.getString("login"))
        .category(rs.getString("category"))
        .build();
  }
}
