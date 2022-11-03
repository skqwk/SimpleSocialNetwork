package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.dto.UpdatedUserDTO;
import ru.sqwk.ssn.model.UserModel;
import ru.sqwk.ssn.model.UserProfileModel;
import ru.sqwk.ssn.repo.UserRepo;
import ru.sqwk.ssn.service.UserService;

import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    @Override
    public List<UserModel> getUsers() {
        return userRepo.getUsers();
    }

    @Override
    public UserProfileModel getUser(Long id) {
        return userRepo.getUser(id).orElseThrow(() -> new RuntimeException("Not found"));
    }

    @Override
    public List<UserModel> getUsersByName(String name) {
        log.info("Find users with name = {}", name);
        return userRepo.getUsersByName(name);
    }

    @Override
    public void updateUser(Long id, UpdatedUserDTO updatedUserDTO) {
        log.info("Update user with id = {}", id);
        log.info("New login = {}, new email = {}", updatedUserDTO.getLogin(), updatedUserDTO.getEmail());
        userRepo.update(id, updatedUserDTO);
    }
}
