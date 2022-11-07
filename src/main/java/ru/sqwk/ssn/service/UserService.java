package ru.sqwk.ssn.service;

import ru.sqwk.ssn.dto.UpdatedUserDTO;
import ru.sqwk.ssn.model.FriendChatModel;
import ru.sqwk.ssn.model.FriendModel;
import ru.sqwk.ssn.model.UserModel;
import ru.sqwk.ssn.model.UserProfileModel;
import ru.sqwk.ssn.security.UserAccount;

import java.util.List;

public interface UserService {
    List<UserModel> getUsers();
    List<FriendModel> getFriends();

    UserProfileModel getUser(Long id);

    List<UserModel> getUsersByName(String name);

    void updateUser(Long id, UpdatedUserDTO updatedUserDTO);
    UserAccount getUserByName(String name);

    List<FriendChatModel> getFriendChats();

    List<FriendModel> getFriendsByCategory(String category);

    List<FriendModel> getFriendsByCategoryAndName(String category, String name);
}
