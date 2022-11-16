package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
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

@Repository
@AllArgsConstructor
public class UserRepoJdbc implements UserRepo {

  private final JdbcTemplate jdbc;

  @Override
  public List<UserModel> getUsers() {
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    String query =
        "SELECT user_id, full_name, login, is_friends(user_id, ?) AS is_friend FROM user WHERE role != 'ADMIN' AND user_id != ?;";
    return jdbc.query(query, this::mapResultSetToUserModel, nowUserId, nowUserId);
  }

  @Override
  public List<FriendModel> getFriends() {
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    String query = "call db.get_friends(?)";
    return jdbc.query(query, this::mapResultSetToFriendModel, nowUserId);
  }

  @Override
  public List<FriendChatModel> getFriendChats() {
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    String query = "call db.get_friends(?)";
    return jdbc.query(query, this::mapResultSetToFriendChatModel, nowUserId);
  }

  @Override
  public List<FriendModel> getFriendsByName(String name) {
    String nameParam = "%" + name + "%";
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    String query =
        "SELECT * FROM (SELECT * FROM user WHERE (login LIKE ? "
            + "                  OR email LIKE ?"
            + "                  OR full_name LIKE ?) ) U  INNER JOIN  ("
            + "    SELECT user1 as user, category, timestamp FROM friendship F1 where F1.user2 = ?"
            + "    UNION"
            + "    select user2 as user, category, timestamp from friendship F2 where F2.user1 = ?) FS"
            + "    on U.user_id = FS.user;";

    return jdbc.query(
        query,
        this::mapResultSetToFriendModel,
        nameParam,
        nameParam,
        nameParam,
        nowUserId,
        nowUserId);
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
        "SELECT user_id, full_name, login, email, birthdate, is_friends(user_id, ?) as is_friend FROM user WHERE user_id = ?;";
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();

    UserProfileModel userProfileModel =
        jdbc.queryForObject(
            query,
            (rs, rowNum) ->
                UserProfileModel.builder()
                    .id(rs.getLong("user_id"))
                    .fullName(rs.getString("full_name"))
                    .login(rs.getString("login"))
                    .email(rs.getString("email"))
                    .birthDate(rs.getString("birthdate"))
                    .isFriend(rs.getBoolean("is_friend"))
                    .build(),
            nowUserId,
            id);
    return Optional.ofNullable(userProfileModel);
  }

  @Override
  public Optional<UserAccount> findByLogin(String username) {
    String query = "SELECT user_id, role, password, login FROM user WHERE login = ?;";
    UserAccount userAccount = jdbc.queryForObject(query, this::mapResultSetToUserAccount, username);
    return Optional.ofNullable(userAccount);
  }

  @Override
  public void saveRegisteredUser(User user) {
    String query =
        "INSERT INTO user(login, password, email, birthdate, role, full_name) VALUES(?, ?, ?, ?, ?, ?);";
    jdbc.update(
        query,
        user.getLogin(),
        user.getPassword(),
        user.getEmail(),
        user.getBirthdate(),
        user.getRole(),
        user.getFullName());
  }

  public AuthorModel getAuthor(long authorId) {
    String query = "SELECT login, user_id FROM user WHERE user_id = ?;";
    AuthorModel author =
        jdbc.queryForObject(
            query,
            (rs, rowNum) ->
                AuthorModel.builder()
                    .id(rs.getLong("user_id"))
                    .login(rs.getString("login"))
                    .build(),
            authorId);
    return author;
  }

  @Override
  public List<UserModel> getUsersByName(String name) {
    String nameParam = "%" + name + "%";
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    String query =
        "SELECT user_id, full_name, login, is_friends(user_id, ?) as is_friend FROM user WHERE"
            + "               (login LIKE ?"
            + "            OR email LIKE ?"
            + "            OR full_name LIKE ?) AND user_id != ?;";
    return jdbc.query(
        query,
        this::mapResultSetToUserModel,
        nowUserId,
        nameParam,
        nameParam,
        nameParam,
        nowUserId);
  }

  @Override
  public void update(Long id, UpdatedUserDTO updatedUserDTO) {
    String query =
        "UPDATE user SET login = ?, full_name = ?, email = ?, birthdate = ? WHERE user_id = ?;";
    jdbc.update(
        query,
        updatedUserDTO.getLogin(),
        updatedUserDTO.getFullName(),
        updatedUserDTO.getEmail(),
        updatedUserDTO.getBirthDate(),
        id);
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
