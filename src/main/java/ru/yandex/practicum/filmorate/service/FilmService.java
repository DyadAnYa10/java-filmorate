package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmDao;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final FilmDao filmStorage;
    private final UserService userService;

    public FilmService(@Qualifier("filmRepository") FilmDao filmStorage, UserService userService) {
        this.filmStorage = filmStorage;
        this.userService = userService;
    }

    public List<Film> getAllFilms() {
        return filmStorage.findAllFilm();
    }

    public Film getFilmById(Long id) {
        return filmStorage.findFilmById(id).orElseThrow(
                () -> new NoSuchElementException("Film with id='" + id + "' not found"));
    }

    public Film createFilm(Film film) {
        return filmStorage.save(film);
    }

    public Film updateFilm(Film film) {
        filmStorage.findFilmById(film.getId())
                .orElseThrow(() -> new NoSuchElementException("Film with id='" + film.getId() + "' not found"));

        return filmStorage.update(film);
    }

    public void addLikeByFilmId(Long filmId, Integer userId) {
        filmStorage.findFilmById(filmId)
                .orElseThrow(() -> new NoSuchElementException("Film with id='" + filmId + "' not found"));

        userService.getUserById(userId);
        filmStorage.addLike(filmId, userId);
    }

    public void deleteLikeByFilmId(Long filmId, Integer userId) {
        filmStorage.findFilmById(filmId)
                .orElseThrow(() -> new NoSuchElementException("Film with id='" + filmId + "' not found"));

        userService.getUserById(userId);
        filmStorage.deleteLike(filmId, userId);
    }

    public List<Film> getPopularFilms(int count) {
        return filmStorage.findAllFilm()
                .stream()
                .sorted((o1, o2) -> o2.getUserLikes().size() - o1.getUserLikes().size())
                .limit(count)
                .collect(Collectors.toList());
    }

}
