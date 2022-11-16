package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.Membership;
import ru.sqwk.ssn.model.CommunityMemberModel;
import ru.sqwk.ssn.repo.MembershipRepo;
import ru.sqwk.ssn.service.MembershipService;
import ru.sqwk.ssn.util.Formatter;

import java.util.Date;

@Service
@AllArgsConstructor
public class MembershipServiceImpl implements MembershipService {

  private final MembershipRepo membershipRepo;

  @Override
  public void deleteMembership(Long userId, Long communityId) {
    membershipRepo.deleteMembership(userId, communityId);
  }

  @Override
  public CommunityMemberModel addMembership(Long userId, Long communityId) {
    Membership membership =
        Membership.builder()
            .communityId(communityId)
            .userId(userId)
            .entryDate(Formatter.format(new Date()))
            .build();

    membershipRepo.save(membership);
    return getMembership(userId, communityId);
  }

  public CommunityMemberModel getMembership(Long userId, Long communityId) {
    return membershipRepo
        .getMembership(userId, communityId)
        .orElseThrow(
            () ->
                new RuntimeException(
                    String.format(
                        "Membership with userId = %s, communityId = %s - not found",
                        userId, communityId)));
  }
}
