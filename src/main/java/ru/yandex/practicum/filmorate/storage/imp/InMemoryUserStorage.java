package ru.yandex.practicum.filmorate.storage.imp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;


import java.util.*;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {

    private int idUser = 1;

    private final Map<Integer, User> users = new HashMap<>();

    @Override
    public User create(User user) {

        if (users.values()
                .stream()
                .anyMatch(userSaved -> userSaved.getLogin().equals(user.getLogin()))) {
            log.error("User with login='" + user.getLogin() + "' already exist");
            throw new ValidationException("User with login='" + user.getLogin() + "' already exist");
        }
        user.setId(idUser++);

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        users.put(user.getId(), user);
        return user;
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User update(User user) {

        if (user.getId() == null || user.getId() <= 0) {
            throw new IllegalArgumentException("Invalid user id='" + user.getId() + "' of updatable user");
        }

        if (!users.containsKey(user.getId())) {
            throw new IllegalArgumentException("Invalid user id='" + user.getId() + "' of updatable user");
        }

        users.put(user.getId(), user);
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<User> getUsersByIds(Set<Integer> usersIds) {
        List<User> foundUsers = new ArrayList<>();

        for (Integer id : usersIds) {
            foundUsers.add(users.get(id));
        }

        return foundUsers;
    }

}
