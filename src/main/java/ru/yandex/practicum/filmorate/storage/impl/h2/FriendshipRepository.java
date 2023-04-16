package ru.yandex.practicum.filmorate.storage.impl.h2;

import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.impl.h2.mapper.UserMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class FriendshipRepository {

    private final JdbcTemplate jdbcTemplate;

    public void addFriend(Integer userId, Integer friendId) {
        jdbcTemplate.update("INSERT INTO friendship (user_id, friend_id, approved) VALUES (?, ?, ?)",
                userId, friendId, 0);
    }

    public List<User> getFriend(Integer id, Integer friendId) {
        return jdbcTemplate.query(
                "SELECT u.* FROM users u RIGHT JOIN friendship f ON u.id = f.friend_id WHERE f.user_id = ? AND f.friend_id = ?",
                new UserMapper(), friendId, id);
    }

    public void deleteFriendship(Integer userId, Integer friendId) {
        jdbcTemplate.update("DELETE FROM friendship WHERE (user_id = ? AND friend_id = ?) " +
                        "OR (user_id = ? AND friend_id = ?)",
                userId, friendId, friendId, userId);
    }

    public List<User> getFriendsByUserId(Integer id) {
        return jdbcTemplate.query(
                "SELECT u.* FROM users u RIGHT JOIN friendship f ON u.id = f.friend_id WHERE f.user_id = ?",
                new UserMapper(), id);
    }

    public List<User> getCommonFriends(int userId, int friendId) {
        return jdbcTemplate.query("SELECT * FROM users us " +
                        "JOIN friendship AS fr1 ON us.id = fr1.friend_id " +
                        "JOIN friendship AS fr2 ON us.id = fr2.friend_id " +
                        "WHERE fr1.user_id = ? AND fr2.user_id = ?",
                new UserMapper(), userId, friendId);
    }

}
