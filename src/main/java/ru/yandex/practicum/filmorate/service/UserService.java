package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserStorage userStorage;

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Request add new User");
        User newUser;
        try {
            newUser = userStorage.create(user);
        } catch (ValidationException exception) {
            log.error(exception.getMessage());
            throw exception;
        }
        log.info("Successful added new user {}", newUser);
        return newUser;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
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

    @GetMapping
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

        foundFriend.addFriend(friendId);
        foundUser.addFriend(userId);

        log.info("Friend Success added");
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        log.info("Request delete friend");

        User foundUser = getUserById(userId);
        User foundFriend = getUserById(friendId);

        foundFriend.deleteFriend(friendId);
        foundUser.deleteFriend(userId);

        log.info("Friend Success deleted");
    }

    public Set<Integer> getFriendsByUserId(Integer userId) {
        log.info("Request delete friend");

        User user = getUserById(userId);

        log.info("Friend Success deleted");
        return user.getFriends();
    }

}
