package ru.yandex.practicum.filmorate.storage.impl.h2;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.FilmDao;
import ru.yandex.practicum.filmorate.storage.impl.h2.constants.SqlFilmConstants;
import ru.yandex.practicum.filmorate.storage.impl.h2.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.storage.impl.h2.mapper.MpaMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Repository("filmRepository")
public class FilmRepository implements FilmDao {

    private final JdbcTemplate jdbcTemplate;

    private final FilmMapper filmMapper;

    @Override
    public List<Film> findAllFilm() {
        List<Film> films = jdbcTemplate.query(SqlFilmConstants.SELECT_ALL_FILMS, filmMapper);

        for (Film film : films) {
            film.setMpa(getMpaById(film.getId()));
            film.setGenres(getGenresById(film.getId()));
            film.setUserLikes(getLikesByFilmId(film.getId()));
        }

        return films;
    }


    @Override
    public Film save(Film film) {
        KeyHolder kh = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            final PreparedStatement ps =
                    con.prepareStatement(SqlFilmConstants.INSERT_FILM, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setLong(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, kh);

        final Long filmId = kh.getKeyAs(Long.class);
        film.setId(filmId);

        Set<Genre> genres = film.getGenres();
        if (genres != null) {
            for (Genre genre : genres) {
                jdbcTemplate.update(SqlFilmConstants.INSERT_FILMS_GENRES, filmId, genre.getId());
            }
            film.setGenres(getGenresById(filmId));
        }

        Set<Long> likes = film.getUserLikes();
        if (likes != null) {
            for (Long userId : likes) {
                jdbcTemplate.update(SqlFilmConstants.INSERT_FILMS_USERS, userId, filmId);
            }
            film.setUserLikes(getLikesByFilmId(filmId));
        }

        if (film.getMpa() != null) {
            film.setMpa(getMpaById(film.getId()));
        }
        return film;
    }

    @Override
    public Film update(Film film) {
        int update = jdbcTemplate.update(SqlFilmConstants.UPDATE_FILM_BY_ID,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId());

        if (update == 0) {
            return null;
        }

        if (film.getGenres() != null) {
            updateGenres(film);
        }

        if (film.getUserLikes() != null) {
            updateLikes(film);
        }

        film.setMpa(getMpaById(film.getId()));
        film.setGenres(getGenresById(film.getId()));
        return film;

    }

    private void updateLikes(Film film) {
        jdbcTemplate.update(SqlFilmConstants.DELETE_FILMS_USERS_BY_FILM_ID, film.getId());
        Set<Long> newLikes = film.getUserLikes();
        newLikes.forEach(userId ->
                jdbcTemplate.update(SqlFilmConstants.INSERT_FILMS_USERS, userId, film.getId()));
    }

    private void updateGenres(Film film) {
        jdbcTemplate.update(SqlFilmConstants.DELETE_FILMS_GENRES_BY_FILM_ID, film.getId());
        List<Long> filmGenreIds = film.getGenres().stream().map(Genre::getId).collect(Collectors.toList());
        filmGenreIds.forEach(filmGenreId ->
                jdbcTemplate.update(SqlFilmConstants.INSERT_FILMS_GENRES, film.getId(), filmGenreId));
    }

    public Optional<Film> findFilmById(Long filmId) {
        try {
            Optional<Film> film = Optional.ofNullable(jdbcTemplate
                    .queryForObject(SqlFilmConstants.SELECT_FILMS_BY_FILM_ID, filmMapper, filmId));
            film.ifPresent(value -> value.setMpa(getMpaById(filmId)));
            film.ifPresent(value -> value.setGenres(getGenresById(filmId)));
            film.ifPresent(value -> value.setUserLikes(getLikesByFilmId(filmId)));
            return film;
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public void addLike(long filmId, long userId) {
        jdbcTemplate.update(SqlFilmConstants.INSERT_FILMS_USERS, userId, filmId);
    }

    @Override
    public void deleteLike(long filmId, long userId) {
        jdbcTemplate.update(SqlFilmConstants.DELETE_FILMS_USERS_BY_FILM_ID_AND_USER_ID, filmId, userId);
    }

    private Set<Genre> getGenresById(Long filmId) {
        SqlRowSet genresRowSet = jdbcTemplate.queryForRowSet(SqlFilmConstants.SELECT_GENRES_BY_FILM_ID, filmId);
        Set<Genre> filmGenres = new HashSet<>();
        while (genresRowSet.next()) {
            Genre genre = new Genre(genresRowSet.getLong("id"),
                    genresRowSet.getString("name"));
            filmGenres.add(genre);
        }
        return filmGenres;
    }

    private Rating getMpaById(Long filmId) {
        return jdbcTemplate.queryForObject(SqlFilmConstants.SELECT_MPA_BY_FILM_ID, new MpaMapper(), filmId);
    }

    private Set<Long> getLikesByFilmId(Long filmId) {
        SqlRowSet likesRowSet = jdbcTemplate.queryForRowSet(SqlFilmConstants.SELECT_FILMS_USERS_BY_FILM_ID, filmId);
        Set<Long> likes = new HashSet<>();

        while (likesRowSet.next()) {
            likes.add(likesRowSet.getLong("user_id"));
        }
        return likes;
    }
}
