package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.domain.Membership;
import ru.sqwk.ssn.model.CommunityMemberModel;

import java.util.Optional;

public interface MembershipRepo {
    void deleteMembership(Long userId, Long communityId);
     void save(Membership membership);
     Optional<CommunityMemberModel> getMembership(Long userId, Long communityId);
}
