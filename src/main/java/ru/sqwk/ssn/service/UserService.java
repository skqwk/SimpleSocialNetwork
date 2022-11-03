package ru.sqwk.ssn.service;

import ru.sqwk.ssn.dto.UpdatedUserDTO;
import ru.sqwk.ssn.model.UserModel;
import ru.sqwk.ssn.model.UserProfileModel;

import java.util.List;

public interface UserService {
    public List<UserModel> getUsers();

    UserProfileModel getUser(Long id);

    List<UserModel> getUsersByName(String name);

    void updateUser(Long id, UpdatedUserDTO updatedUserDTO);
}
