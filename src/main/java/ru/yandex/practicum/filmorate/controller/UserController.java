package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    private int idUser = 1;
    private final Map<Integer, User> users = new HashMap<>();

    @PostMapping
    public User addUser(@Valid @RequestBody User user) {
        log.info("Request add new User");

        if (users.values().stream().anyMatch(userSaved -> userSaved.getLogin().equals(user.getLogin()))) {
            log.error("User with login {} already exist", user.getLogin());
            throw new ValidationException("User with login='" + user.getLogin() + "' already exist");
        }
        user.setId(idUser++);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);

        log.info("Successful added new user: {}", user);
        return user;
    }

    @PutMapping
    public User updateUser(@RequestBody User user) {
        log.info("Request update user");

        if (user.getId() == null || user.getId() <= 0) {
            log.error("Id updatable user must not be null or less than 1");
            throw new ValidationException("Invalid id='" + user.getId() + "' of updatable user");
        }

        if (users.get(user.getId()) == null) {
            log.error("User with id='" + user.getId() + "' is not exist");
            throw new ValidationException("Invalid user id='" + user.getId() + "' of updatable user");
        }

        users.put(user.getId(), user);
        log.info("Successful updated user {}", user);
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        log.info("Returned get all users");
        return new ArrayList<>(users.values());
    }
}