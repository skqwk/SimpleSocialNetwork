package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.model.CommunityModel;
import ru.sqwk.ssn.model.CommunityProfileModel;
import ru.sqwk.ssn.repo.CommunityRepo;
import ru.sqwk.ssn.service.CommunityService;

import java.util.List;

@Service
@AllArgsConstructor
public class CommunityServiceImpl implements CommunityService {

    private final CommunityRepo communityRepo;

    @Override
    public List<CommunityModel> getCommunities() {
        return communityRepo.getCommunities();
    }

    @Override
    public CommunityProfileModel getCommunity(Long id) {
        return communityRepo.getCommunity(id).orElseThrow(() -> new RuntimeException("Not found"));
    }
}
