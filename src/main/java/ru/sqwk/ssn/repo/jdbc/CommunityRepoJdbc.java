package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.domain.Community;
import ru.sqwk.ssn.model.CommunityMemberModel;
import ru.sqwk.ssn.model.CommunityModel;
import ru.sqwk.ssn.model.CommunityProfileModel;
import ru.sqwk.ssn.repo.CommunityRepo;
import ru.sqwk.ssn.util.SecurityContextWrapper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class CommunityRepoJdbc implements CommunityRepo {

  private final JdbcTemplate jdbc;

  @Override
  public List<CommunityModel> getCommunities() {
    String query =
        "SELECT community_id, name, topic, count_members(community_id) AS amount_members FROM community;";

    return jdbc.query(
        query,
        (rs, rowNum) ->
            CommunityModel.builder()
                .id(rs.getLong("community_id"))
                .topic(rs.getString("topic"))
                .name(rs.getString("name"))
                .amountMembers(rs.getInt("amount_members"))
                .build());
  }

  @Override
  public Optional<CommunityProfileModel> getCommunity(Long id) {
    Long nowUserId = SecurityContextWrapper.getNowUser().getId();
    String query =
        "SELECT community_id, name, age_limit, topic, creation_date, is_suit_for_age_limit(?, age_limit) as is_suit FROM community WHERE community_id = ?;";

    CommunityProfileModel communityProfileModel =
        jdbc.queryForObject(query, this::mapRowSetToCommunityProfileModel, nowUserId, id);

    return Optional.ofNullable(communityProfileModel);
  }

  @Override
  public void delete(Long communityId) {
    String query = "DELETE FROM community WHERE community_id = ?;";
    jdbc.update(query, communityId);
  }

  @Override
  public Long save(Community community) {
    String query =
        "INSERT INTO community(age_limit, topic, creation_date, name) VALUES(?, ?, ?, ?);";
    KeyHolder keyHolder = new GeneratedKeyHolder();
    jdbc.update(
        connection -> {
          PreparedStatement ps =
              connection.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
          ps.setObject(1, community.getAgeLimit());
          ps.setString(2, community.getTopic());
          ps.setString(3, community.getCreationDate());
          ps.setString(4, community.getName());
          return ps;
        },
        keyHolder);
    return keyHolder.getKey().longValue();
  }

  private CommunityProfileModel mapRowSetToCommunityProfileModel(ResultSet rs, int rowNum)
      throws SQLException {
    List<CommunityMemberModel> members = getMembers(rs.getLong("community_id"));
    String ageLimit = rs.getString("age_limit");
    return CommunityProfileModel.builder()
        .id(rs.getLong("community_id"))
        .name(rs.getString("name"))
        .topic(rs.getString("topic"))
        .ageLimit(ageLimit == null ? "Нет" : ageLimit + " +")
        .isSuitForAgeLimit(rs.getBoolean("is_suit"))
        .members(members)
        .beIn(
            members.stream()
                .anyMatch(m -> m.getId().equals(SecurityContextWrapper.getNowUser().getId())))
        .creationDate(rs.getString("creation_date"))
        .build();
  }

  private List<CommunityMemberModel> getMembers(Long communityId) {
    String query = "CALL get_members(?);";
    return jdbc.query(
        query,
        (rs, rowNum) ->
            CommunityMemberModel.builder()
                .id(rs.getLong("user_id"))
                .entryDate(rs.getString("entry_date"))
                .login(rs.getString("login"))
                .build(),
        communityId);
  }
}
