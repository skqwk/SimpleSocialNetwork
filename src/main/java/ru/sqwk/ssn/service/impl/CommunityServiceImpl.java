package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.Community;
import ru.sqwk.ssn.dto.NewCommunityDTO;
import ru.sqwk.ssn.model.CommunityModel;
import ru.sqwk.ssn.model.CommunityProfileModel;
import ru.sqwk.ssn.repo.CommunityRepo;
import ru.sqwk.ssn.service.CommunityService;
import ru.sqwk.ssn.util.Formatter;

import java.util.Date;
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

    @Override
    public void deleteCommunity(Long communityId) {
        communityRepo.delete(communityId);
    }

    @Override
    public CommunityProfileModel createCommunity(NewCommunityDTO newCommunityDTO) {
        Community community = Community.builder()
                .creationDate(Formatter.format(new Date()))
                .name(newCommunityDTO.getName())
                .topic(newCommunityDTO.getTopic())
                .ageLimit(newCommunityDTO.getAgeLimit())
                .build();
        Long id = communityRepo.save(community);
        return getCommunity(id);
    }
}
