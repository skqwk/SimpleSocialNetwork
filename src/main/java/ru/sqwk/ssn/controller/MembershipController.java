package ru.sqwk.ssn.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sqwk.ssn.model.CommunityMemberModel;
import ru.sqwk.ssn.model.UserLogin;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.MembershipService;

@RestController
@AllArgsConstructor
public class MembershipController {

    private final MembershipService membershipService;

    @DeleteMapping("/membership/{communityId}")
    public UserLogin leaveCommunity(@AuthenticationPrincipal UserAccount userAccount, @PathVariable Long communityId) {
        membershipService.deleteMembership(userAccount.getId(), communityId);
        return UserLogin.builder()
                .login(userAccount.getLogin())
                .build();
    }


    @PostMapping("/membership/{communityId}")
    public CommunityMemberModel joinCommunity(@AuthenticationPrincipal UserAccount userAccount, @PathVariable Long communityId) {
        return membershipService.addMembership(userAccount.getId(), communityId);
    }

}
