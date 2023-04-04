package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserStorage {
    User create(User user);

    Optional<User> findById(Integer id);

    User update(User user);

    List<User> getAllUsers();

    List<User> getUsersByIds(Set<Integer> ids);
}

