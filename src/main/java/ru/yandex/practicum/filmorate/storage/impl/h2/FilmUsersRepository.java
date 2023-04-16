package ru.yandex.practicum.filmorate.storage.impl.h2;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class FilmUsersRepository {

    private final JdbcTemplate jdbcTemplate;


    public void save(Integer filmId, Integer userId) {
        jdbcTemplate.update("INSERT INTO films_users VALUES (?,?)", filmId, userId);
    }

    public void delete(Integer filmId, Integer userId) {
        jdbcTemplate.update("DELETE FROM films_users WHERE film_id = ? AND user_id = ?", filmId, userId);

    }

    public int selectCountLikeByFilmId(Integer filmId) {
        return jdbcTemplate.query("SELECT COUNT(user_id) FROM films_users WHERE film_id = ?",
                        new BeanPropertyRowMapper<>(Integer.class),
                        filmId)
                .stream().findAny()
                .orElse(0);

    }

}
