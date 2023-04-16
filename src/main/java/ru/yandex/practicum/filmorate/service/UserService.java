package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ExistException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserDao;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserDao userRepository;
    private final FriendshipService friendshipService;

    public User createUser(User user) {
        log.info("Request create new User");

        Optional<User> userOptional = userRepository.findById(user.getId());
        if (userOptional.isPresent()) {
            log.error("User with id='{}' already exist", user.getId());
            throw new ExistException("User with login='" + user.getLogin() + "' already exist");
        }

        if (user.getName() == null || user.getName().isEmpty()) {
            user.setName(user.getLogin());
        }

        userRepository.create(user);

        return getUserByLogin(user.getLogin());
    }

    public User updateUser(User user) {
        User findedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NoSuchElementException("User with id='" + user.getId() + "' not found"));

        return userRepository.update(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id='" + id + "' not found"));
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new NoSuchElementException("User with login='" + login + "' not found"));
    }

    public void addFriend(Integer userId, Integer friendId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id='" + userId + "' not found"));
        userRepository.findById(friendId)
                .orElseThrow(() -> new NoSuchElementException("User with id='" + friendId + "' not found"));

        List<User> friend = friendshipService.getFriend(userId, friendId);

        if (friend.isEmpty()) {
            friendshipService.addFriend(userId, friendId);
        }
    }

    public void deleteFriend(Integer userId, Integer friendId) {
        log.info("Request delete friend");
        friendshipService.deleteFriendship(userId, friendId);
    }

    public List<User> getFriendsByUserId(Integer userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id='" + userId + "' not found"));

        return friendshipService.getFriendsByUserId(userId);
    }

    public List<User> getCommonFriends(Integer id, Integer otherId) {
        return friendshipService.getCommonFriends(id, otherId);
    }

}
