package ru.yandex.practicum.filmorate.storage.imp;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class InMemoryFilmStorage implements FilmStorage {

    private int idFilm = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @Override
    public Film create(Film film) {
        if (films.values()
                .stream()
                .anyMatch(filmSaved -> (filmSaved.equals(film)))) {
            throw new ValidationException("Film already exists");
        }

        film.setId(idFilm++);

        films.put(film.getId(), film);
        return film;
    }

    @Override
    public Film update(Film film) {

        if (film.getId() == null || film.getId() <= 0) {
            throw new IllegalArgumentException("Invalid film id='" + film.getId() + "' of updatable user");
        }

        if (!films.containsKey(film.getId())) {
            throw new IllegalArgumentException("Invalid id='" + film.getId() + "' of updatable user");
        }

        films.put(film.getId(), film);

        return film;
    }

    @Override
    public List<Film> getAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Optional<Film> findById(Integer id) {
        return Optional.ofNullable(films.get(id));
    }

    @Override
    public List<Film> findPopularFilms(Integer count) {
        return films.values()
                .stream()
                .sorted((o1, o2) -> Integer.compare(o2.getUserLikes().size(), o1.getUserLikes().size()))
                .limit(count)
                .collect(Collectors.toList());
    }

}
