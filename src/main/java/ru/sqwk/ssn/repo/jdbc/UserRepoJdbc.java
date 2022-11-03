package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.domain.User;
import ru.sqwk.ssn.model.AuthorModel;
import ru.sqwk.ssn.model.UserModel;
import ru.sqwk.ssn.model.UserProfileModel;
import ru.sqwk.ssn.repo.UserRepo;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.security.UserRole;

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
    String query = "SELECT user_id, full_name, login FROM user;";
    return jdbc.query(query, this::mapResultSetToUserModel);
  }

  @Override
  public Optional<UserProfileModel> getUser(Long id) {
    String query =
        "SELECT user_id, full_name, login, email, birthdate FROM user WHERE user_id = ?;";

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
                    .build(),
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
    String query = "INSERT INTO user(login, password, email, birthdate, role, full_name) VALUES(?, ?, ?, ?, ?, ?);";
    jdbc.update(query, user.getLogin(), user.getPassword(), user.getEmail(), user.getBirthdate(), user.getRole(), user.getFullName());
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

  private UserAccount mapResultSetToUserAccount(ResultSet rs, Integer rowNum) throws SQLException {
    return UserAccount.builder()
            .id(rs.getLong("user_id"))
            .login(rs.getString("login"))
            .password(rs.getString("password"))
            .role(UserRole.USER)
            .build();
  }

  private UserModel mapResultSetToUserModel(ResultSet rs, Integer rowNum) throws SQLException {
    return UserModel.builder()
        .id(rs.getLong("user_id"))
        .fullName(rs.getString("full_name"))
        .login(rs.getString("login"))
        .build();
  }
}
