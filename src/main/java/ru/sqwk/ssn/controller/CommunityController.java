package ru.sqwk.ssn.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sqwk.ssn.dto.NewCommunityDTO;
import ru.sqwk.ssn.dto.UpdatedCommunityDTO;
import ru.sqwk.ssn.model.CommunityModel;
import ru.sqwk.ssn.model.CommunityProfileModel;
import ru.sqwk.ssn.service.CommunityService;

import java.util.List;

@RestController
@RequestMapping("api/v1/")
@AllArgsConstructor
public class CommunityController {
    private final CommunityService communityService;

    @DeleteMapping("community/{id}")
    public void deleteCommunity(@PathVariable("id") Long id) {
        communityService.deleteCommunity(id);
    }

    @PostMapping("community")
    public CommunityProfileModel createCommunity(@RequestBody NewCommunityDTO newCommunityDTO) {
       return communityService.createCommunity(newCommunityDTO);
    }

    @PutMapping("community/{communityId}")
    public void updateCommunity(@PathVariable Long communityId, @RequestBody UpdatedCommunityDTO updatedCommunityDTO) {
      communityService.updateCommunity(communityId, updatedCommunityDTO);
    }


    @GetMapping("/communities")
    public List<CommunityModel> findAllCommunities(
            @RequestParam(name = "name", required = false, defaultValue = "") String name,
            @RequestParam(name = "topic", required = false, defaultValue = "All") String topic
    ) {
        if (name.isEmpty()) {
            if (topic.equals("All")) {
                return communityService.getCommunities();
            } else {
                return communityService.getCommunitiesByTopic(topic);
            }
        } else {
            return communityService.getCommunitiesByTopicAndName(topic, name);
        }
    }



}
