package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.model.CommunityMemberModel;
import ru.sqwk.ssn.model.CommunityModel;
import ru.sqwk.ssn.model.CommunityProfileModel;
import ru.sqwk.ssn.repo.CommunityRepo;
import ru.sqwk.ssn.util.SecurityContextWrapper;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class CommunityRepoJdbc implements CommunityRepo {

  private final JdbcTemplate jdbc;

    @Override
    public List<CommunityModel> getCommunities() {
        String query = "SELECT community_id, name, topic, count_members(community_id) AS amount_members FROM community;";


        return jdbc.query(query, (rs, rowNum) -> CommunityModel.builder()
                .id(rs.getLong("community_id"))
                .topic(rs.getString("topic"))
                .name(rs.getString("name"))
                .amountMembers(rs.getInt("amount_members"))
                .build());

//        String query = "SELECT user_id, full_name, login FROM user;";
//
//        jdbc.query(query, (rs, rowNum) -> UserModel.builder()
//                .id(rs.getLong("user_id"))
//                .fullName(rs.getString("full_name"))
//                .login(rs.getString("login"))
//                .build()).forEach(um -> System.out.println(um.getLogin()));

//        return null;
    }

    @Override
    public Optional<CommunityProfileModel> getCommunity(Long id) {
        String query = "SELECT community_id, name, topic, creation_date FROM community WHERE community_id = ?;";
        List<CommunityMemberModel> members = getMembers(id);
        CommunityProfileModel communityProfileModel = jdbc.queryForObject(query,
                (rs, rowNum) -> CommunityProfileModel.builder()
                        .id(rs.getLong("community_id"))
                        .name(rs.getString("name"))
                        .topic(rs.getString("topic"))
                        .members(members)
                        .beIn(members.stream().anyMatch(m -> m.getId().equals(SecurityContextWrapper.getNowUser().getId())))
                        .creationDate(rs.getString("creation_date"))
                        .build(),
                id);

        return Optional.ofNullable(communityProfileModel);
    }

    private List<CommunityMemberModel> getMembers(Long communityId) {
        String query = "CALL get_members(?);";
        return jdbc.query(query, (rs, rowNum) -> CommunityMemberModel.builder()
                .id(rs.getLong("user_id"))
                .entryDate(rs.getString("entry_date"))
                .login(rs.getString("login"))
                .build(), communityId);
    }
}
