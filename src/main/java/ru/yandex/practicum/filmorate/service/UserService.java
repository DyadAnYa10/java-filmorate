package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;

    public User createUser(User user) {
        log.info("Request add new User");
        User newUser;
        try {
            newUser = userStorage.create(user);
        } catch (ValidationException exception) {
            log.error(exception.getMessage());
            throw exception;
        }
        log.info("Added new user {}", newUser);
        return newUser;
    }

    public User updateUser(User user) {
        log.info("Request update user");
        User updatableUser;
        try {
            updatableUser = userStorage.update(user);
        } catch (ValidationException exception) {
            log.error(exception.getMessage());
            throw exception;
        }
        log.info("Updatable user {}", user);
        return updatableUser;
    }

    public List<User> getAllUsers() {
        log.info("Request get all users");
        return userStorage.getAllUsers();
    }

    public User getUserById(Integer id) {
        return userStorage.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id='" + id + "' not found"));
    }

    public void addFriend(Integer userId, Integer friendId) {
        log.info("Request add friend");

        User foundUser = getUserById(userId);
        User foundFriend = getUserById(friendId);

        foundUser.addFriend(friendId);
        foundFriend.addFriend(userId);
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        log.info("Request delete friend");

        User user = getUserById(userId);
        User friend = getUserById(friendId);

        friend.deleteFriend(friendId);
        user.deleteFriend(userId);
    }

    public List<User> getFriendsByUserId(Integer userId) {
        log.info("Request get friend user by id");

        User user = getUserById(userId);

        return userStorage.getUsersByIds(user.getFriends());
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        log.info("Request get common friends by id");

        User user = getUserById(id);

        User otherUser = getUserById(otherId);

        Set<Integer> userFriendsIds = user.getFriends();
        Set<Integer> otherUserFriendsIds = otherUser.getFriends();

        Set<Integer> common = new HashSet<>(userFriendsIds);

        common.retainAll(otherUserFriendsIds);

        return userStorage.getUsersByIds(common);
    }
}
