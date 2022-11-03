package ru.sqwk.ssn.service;

import ru.sqwk.ssn.model.UserModel;
import ru.sqwk.ssn.model.UserProfileModel;

import java.util.List;

public interface UserService {
    public List<UserModel> getUsers();

    UserProfileModel getUser(Long id);
}
