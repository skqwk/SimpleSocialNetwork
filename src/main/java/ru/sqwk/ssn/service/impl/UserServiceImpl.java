package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.model.UserModel;
import ru.sqwk.ssn.model.UserProfileModel;
import ru.sqwk.ssn.repo.UserRepo;
import ru.sqwk.ssn.service.UserService;

import java.util.List;

@Service
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
}
