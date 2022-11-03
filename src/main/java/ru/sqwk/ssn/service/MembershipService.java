package ru.sqwk.ssn.service;

import ru.sqwk.ssn.model.CommunityMemberModel;

public interface MembershipService {
    void deleteMembership(Long userId, Long communityId);

    CommunityMemberModel addMembership(Long userId, Long communityId);
}
