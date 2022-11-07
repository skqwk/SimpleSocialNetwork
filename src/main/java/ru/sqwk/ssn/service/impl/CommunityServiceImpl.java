package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.domain.Community;
import ru.sqwk.ssn.dto.NewCommunityDTO;
import ru.sqwk.ssn.dto.UpdatedCommunityDTO;
import ru.sqwk.ssn.model.CommunityModel;
import ru.sqwk.ssn.model.CommunityProfileModel;
import ru.sqwk.ssn.repo.CommunityRepo;
import ru.sqwk.ssn.service.CommunityService;
import ru.sqwk.ssn.util.Formatter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    Community community =
        Community.builder()
            .creationDate(Formatter.format(new Date()))
            .name(newCommunityDTO.getName())
            .topic(newCommunityDTO.getTopic())
            .ageLimit(newCommunityDTO.getAgeLimit())
            .build();
    Long id = communityRepo.save(community);
    return getCommunity(id);
  }

  @Override
  public List<CommunityModel> getCommunitiesByTopic(String topic) {
    return getCommunities().stream()
        .filter(c -> c.getTopic().equals(topic))
        .collect(Collectors.toList());
  }

  @Override
  public List<CommunityModel> getCommunitiesByTopicAndName(String topic, String name) {
    if (topic.equals("All")) {
      return getCommunitiesByName(name);
    } else {
      return getCommunitiesByName(name).stream()
          .filter(c -> c.getTopic().equals(topic))
          .collect(Collectors.toList());
    }
  }

  @Override
  public void updateCommunity(Long communityId, UpdatedCommunityDTO updatedCommunityDTO) {
    communityRepo.update(Community.builder()
                    .id(communityId)
                    .name(updatedCommunityDTO.getName())
                    .ageLimit(updatedCommunityDTO.getAgeLimit())
                    .topic(updatedCommunityDTO.getTopic())
            .build());
  }

  private List<CommunityModel> getCommunitiesByName(String name) {
    return communityRepo.getCommunitiesByName(name);
  }
}
