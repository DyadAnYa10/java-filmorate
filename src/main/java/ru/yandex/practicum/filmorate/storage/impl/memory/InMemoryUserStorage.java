package ru.yandex.practicum.filmorate.storage.impl.memory;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDao;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class InMemoryUserStorage implements UserDao {

    private final Map<Integer, User> users = new HashMap<>();
    private int idUser = 1;

    @Override
    public void create(User user) {
        user.setId(idUser++);
        users.put(user.getId(), user);
    }

    @Override
    public Optional<User> findById(Integer id) {
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public User update(User user) {
        users.put(user.getId(), user);
        return user;
    }

    @Override
    public void deleteById(Integer id) {
        Optional<User> optionalUser = findById(id);
        optionalUser.ifPresent(user -> users.remove(user.getId()));
    }

    @Override
    public List<User> findAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public List<User> findUsersByIds(Set<Integer> usersIds) {
        List<User> foundUsers = new ArrayList<>();

        for (Integer id : usersIds) {
            foundUsers.add(users.get(id));
        }

        return foundUsers;
    }

    @Override
    public Optional<User> findByLogin(String login) {
        for (User user : users.values()) {
            if (user.getLogin().equals(login)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

}
