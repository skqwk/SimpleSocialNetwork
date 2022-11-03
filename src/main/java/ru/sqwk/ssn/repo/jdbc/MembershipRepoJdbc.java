package ru.sqwk.ssn.repo.jdbc;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.sqwk.ssn.domain.Membership;
import ru.sqwk.ssn.model.CommunityMemberModel;
import ru.sqwk.ssn.repo.MembershipRepo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class MembershipRepoJdbc implements MembershipRepo {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void deleteMembership(Long userId, Long communityId) {
        String query = "DELETE FROM membership WHERE user_id = ? AND community_id = ?";
        jdbcTemplate.update(query, userId, communityId);
    }

    @Override
    public void save(Membership membership) {
        String query = "INSERT INTO membership(user_id, entry_date, community_id) VALUES(?, ?, ?);";
        jdbcTemplate.update(query, membership.getUserId(), membership.getEntryDate(), membership.getCommunityId());
    }

    @Override
    public Optional<CommunityMemberModel> getMembership(Long userId, Long communityId) {
        String query = "SELECT U.login, M.entry_date, U.user_id FROM user U " +
                "INNER JOIN membership M ON U.user_id = M.user_id WHERE M.community_id = ? AND U.user_id = ?;";
        return Optional.ofNullable(

        jdbcTemplate.queryForObject(query, this::mapResultSetToCommunityMemberModel, communityId, userId)
        );
    }

    private CommunityMemberModel mapResultSetToCommunityMemberModel(ResultSet rs, int rowNum) throws SQLException {
        return CommunityMemberModel.builder()
                .id(rs.getLong("user_id"))
                .entryDate(rs.getString("entry_date"))
                .login(rs.getString("login"))
                .build();
    }
}
