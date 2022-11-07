package ru.sqwk.ssn.repo;

import ru.sqwk.ssn.domain.User;
import ru.sqwk.ssn.dto.UpdatedUserDTO;
import ru.sqwk.ssn.model.AuthorModel;
import ru.sqwk.ssn.model.FriendChatModel;
import ru.sqwk.ssn.model.FriendModel;
import ru.sqwk.ssn.model.UserModel;
import ru.sqwk.ssn.model.UserProfileModel;
import ru.sqwk.ssn.security.UserAccount;

import java.util.List;
import java.util.Optional;

public interface UserRepo {
    List<UserModel> getUsers();

    Optional<UserProfileModel> getUser(Long id);

    Optional<UserAccount> findByLogin(String username);

    void saveRegisteredUser(User user);

    AuthorModel getAuthor(long authorId);

    List<UserModel> getUsersByName(String name);

    void update(Long id, UpdatedUserDTO updatedUserDTO);

    List<FriendModel> getFriends();

    List<FriendChatModel> getFriendChats();

    List<FriendModel> getFriendsByName(String name);
}
