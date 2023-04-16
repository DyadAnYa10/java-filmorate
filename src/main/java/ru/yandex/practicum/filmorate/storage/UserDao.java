package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserDao {
    void create(User user);

    List<User> findAll();

    Optional<User> findById(Integer id);

    User update(User user);

    void deleteById(Integer id);

    List<User> findUsersByIds(Set<Integer> ids);

    Optional<User> findByLogin(String login);
}

