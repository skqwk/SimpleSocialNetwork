package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.domain.Community;
import ru.sqwk.ssn.model.CommunityModel;
import ru.sqwk.ssn.model.CommunityProfileModel;

import java.util.List;
import java.util.Optional;

public interface CommunityRepo {
  List<CommunityModel> getCommunities();

  Optional<CommunityProfileModel> getCommunity(Long id);

  void delete(Long communityId);

  Long save(Community community);

  List<CommunityModel> getCommunitiesByName(String name);

  void update(Community updatedCommunity);
}
