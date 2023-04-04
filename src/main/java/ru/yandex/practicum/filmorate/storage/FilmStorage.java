package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;
import java.util.Optional;

public interface FilmStorage {
    Film create(Film film);

    Film update(Film film);

    List<Film> getAllFilms();

    Optional<Film> findById(Integer id);

    List<Film> findPopularFilms(Integer count);
}
