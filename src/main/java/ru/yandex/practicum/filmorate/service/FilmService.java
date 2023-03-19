package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class FilmService {

    private final FilmStorage filmStorage;

    public Film addFilm(Film film){
        log.info("Request add new Film");

        Film createdFilm;

        try {
            createdFilm = filmStorage.create(film);
        } catch (ValidationException exception) {
            log.error(exception.getMessage());
            throw exception;
        }

        log.info("Successful added new Film {}", film);
        return createdFilm;
    }

    public Film updateFilm(Film film){
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

    public List<Film> getAllFilms() {
        log.info("Request get all films");
        return filmStorage.getAllFilms();
    }

    public Film getFilmById(Integer id) {
        return filmStorage.findById(id)
                .orElseThrow(() -> new NoSuchElementException("User with id='" + id + "' not found"));
    }

    public void addLikeByFilmId(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        film.addLike(userId);
    }

    public void deleteLikeByFilmId(Integer filmId, Integer userId) {
        Film film = getFilmById(filmId);
        film.deleteLike(userId);
    }

    public List<Film> getPopularFilms(Integer count) {
        return filmStorage.findPopularFilms(count);
    }
}