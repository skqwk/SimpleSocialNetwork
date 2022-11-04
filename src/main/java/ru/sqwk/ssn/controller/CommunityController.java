package ru.sqwk.ssn.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sqwk.ssn.dto.NewCommunityDTO;
import ru.sqwk.ssn.model.CommunityProfileModel;
import ru.sqwk.ssn.service.CommunityService;

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


}
