package ru.sqwk.ssn.service;

import ru.sqwk.ssn.dto.NewCommunityDTO;
import ru.sqwk.ssn.dto.UpdatedCommunityDTO;
import ru.sqwk.ssn.model.CommunityModel;
import ru.sqwk.ssn.model.CommunityProfileModel;

import java.util.List;

public interface CommunityService {
  List<CommunityModel> getCommunities();

  CommunityProfileModel getCommunity(Long id);

  void deleteCommunity(Long communityId);

  CommunityProfileModel createCommunity(NewCommunityDTO newCommunityDTO);

  List<CommunityModel> getCommunitiesByTopic(String category);

  List<CommunityModel> getCommunitiesByTopicAndName(String category, String name);

  void updateCommunity(Long communityId, UpdatedCommunityDTO updatedCommunityDTO);
}
