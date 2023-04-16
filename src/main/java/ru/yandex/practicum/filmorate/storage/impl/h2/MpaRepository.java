package ru.yandex.practicum.filmorate.storage.impl.h2;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Rating;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MpaRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Rating> findAll() {
        return jdbcTemplate.query("SELECT * FROM MPA", new BeanPropertyRowMapper<>(Rating.class));
    }

    public Optional<Rating> findById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM MPA WHERE ID = ?",
                new BeanPropertyRowMapper<>(Rating.class), id));
    }

}
