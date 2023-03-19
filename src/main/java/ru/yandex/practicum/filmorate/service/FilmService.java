package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;


import javax.validation.Valid;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserService userService;

    public Film createFilm(@Valid @RequestBody Film film) {
        log.info("Request add new Film");

        Film createdFilm;

        try {
            createdFilm = filmStorage.create(film);
        } catch (ValidationException exception) {
            log.error(exception.getMessage());
            throw exception;
        }

        log.info("Added new Film {}", film);
        return createdFilm;
    }

    public Film updateFilm(@RequestBody Film film) {
        log.info("Request update film");
        Film updatableFilm;
        try {
            updatableFilm = filmStorage.update(film);
        } catch (ValidationException exception) {
            log.error(exception.getMessage());
            throw exception;
        }
        log.info("Updatable film {}", film);
        return updatableFilm;
    }

    public Film getFilmById(Integer id) {
        return filmStorage.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Film with id='" + id + "' not found"));
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public void addLikeByFilmId(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        film.addLike(userId);
    }

    public void deleteLikeByFilmId(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        userService.getUserById(userId);
        film.deleteLike(userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.findPopularFilms(count);
    }
}
