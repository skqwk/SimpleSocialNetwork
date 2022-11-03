package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.model.CommunityModel;
import ru.sqwk.ssn.model.CommunityProfileModel;

import java.util.List;
import java.util.Optional;

public interface CommunityRepo {
    List<CommunityModel> getCommunities();
    Optional<CommunityProfileModel> getCommunity(Long id);
}
