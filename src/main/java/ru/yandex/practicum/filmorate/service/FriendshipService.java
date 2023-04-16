package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.impl.h2.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class FriendshipService {

    private final FriendshipRepository friendshipRepository;

    public void addFriend(Integer userId, Integer friendId) {
        friendshipRepository.addFriend(userId, friendId);
    }

    public List<User> getFriend(Integer id, Integer friendId) {
        return friendshipRepository.getFriend(id, friendId);
    }

    public void deleteFriendship(Integer userId, Integer friendId) {
        friendshipRepository.deleteFriendship(userId, friendId);
    }

    public List<User> getFriendsByUserId(Integer id) {
        return friendshipRepository.getFriendsByUserId(id);
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        return friendshipRepository.getCommonFriends(userId, friendId);
    }

}
