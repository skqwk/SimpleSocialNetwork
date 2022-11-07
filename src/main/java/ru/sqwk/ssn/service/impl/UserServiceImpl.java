package ru.sqwk.ssn.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.sqwk.ssn.dto.UpdatedUserDTO;
import ru.sqwk.ssn.model.FriendChatModel;
import ru.sqwk.ssn.model.FriendModel;
import ru.sqwk.ssn.model.UserModel;
import ru.sqwk.ssn.model.UserProfileModel;
import ru.sqwk.ssn.repo.UserRepo;
import ru.sqwk.ssn.security.UserAccount;
import ru.sqwk.ssn.service.AuthService;
import ru.sqwk.ssn.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepo userRepo;
  private final AuthService authService;

  @Override
  public List<UserModel> getUsers() {
    return userRepo.getUsers();
  }

  @Override
  public List<FriendModel> getFriends() {
    List<FriendModel> friends = userRepo.getFriends();
    log.info("Request friends. Friends.size() = {}", friends.size());
    return friends;
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
    log.info(
        "New login = {}, new email = {}", updatedUserDTO.getLogin(), updatedUserDTO.getEmail());
    userRepo.update(id, updatedUserDTO);
    UserDetails userDetails = authService.loadUserByUsername(userRepo.getUser(id).get().getLogin());
    log.info("Update current auth with login = {}", userDetails.getUsername());
    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities()));
  }

  @Override
  public UserAccount getUserByName(String name) {
    return userRepo
        .findByLogin(name)
        .orElseThrow(() -> new UsernameNotFoundException("User with name not found"));
  }

  @Override
  public List<FriendChatModel> getFriendChats() {
    return userRepo.getFriendChats();
  }

  @Override
  public List<FriendModel> getFriendsByCategory(String category) {
    return getFriends().stream()
        .filter(f -> f.getCategory().equals(category))
        .collect(Collectors.toList());
  }

  @Override
  public List<FriendModel> getFriendsByCategoryAndName(String category, String name) {
    if (category.equals("All")) {
      return getFriendsByName(name);
    } else {
      return getFriendsByName(name).stream()
          .filter(f -> f.getCategory().equals(category))
          .collect(Collectors.toList());
    }
  }

  private List<FriendModel> getFriendsByName(String name) {
    return userRepo.getFriendsByName(name);
  }
}
