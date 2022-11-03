package ru.sqwk.ssn.service;

import ru.sqwk.ssn.model.CommunityModel;
import ru.sqwk.ssn.model.CommunityProfileModel;

import java.util.List;

public interface CommunityService {
    List<CommunityModel> getCommunities();

    CommunityProfileModel getCommunity(Long id);
}
