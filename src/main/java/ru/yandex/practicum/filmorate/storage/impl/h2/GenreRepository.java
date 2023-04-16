package ru.yandex.practicum.filmorate.storage.impl.h2;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.impl.h2.mapper.GenreMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class GenreRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Genre> findAll() {
        return jdbcTemplate.query("SELECT * FROM genres",
                new GenreMapper());
    }

    public Optional<Genre> findById(Integer id) {
        Genre genre = jdbcTemplate.queryForObject("SELECT * FROM genres WHERE id = ?",
                new GenreMapper(), id);

        return Optional.ofNullable(genre);
    }


    public List<Genre> findAllByFilmId(Long id) {
        return jdbcTemplate.query("SELECT g.id, g.name " +
                        "FROM films_genres AS fg " +
                        "LEFT JOIN genres AS g ON fg.genre_id=g.id " +
                        "WHERE film_id = ?",
                new GenreMapper(), id);

    }

    public List<Genre> findGenresIdByFilmId(Long id) {
        return jdbcTemplate.query("SELECT genre_id FROM films_genres WHERE film_id = ?",
                new RowMapper<Genre>() {
                    @Override
                    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
                        return Genre.builder()
                                .id(rs.getLong("genre_id"))
                                .build();
                    }
                }, id);
    }

}
