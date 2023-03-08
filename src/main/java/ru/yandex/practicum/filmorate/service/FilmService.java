package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class FilmService {
    private int idFilm = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    public Film addFilm(Film film){
        log.info("Request add new Film");

        if (films.values()
                .stream()
                .anyMatch(filmSaved ->
                    filmSaved.getName().equals(film.getName()) &&
                            filmSaved.getDescription().equals(film.getDescription()) &&
                            filmSaved.getReleaseDate().equals(film.getReleaseDate()) &&
                            filmSaved.getDuration().equals(film.getDuration()))) {
            log.error("Film already exist");
            throw new ValidationException("Film  already exist");
        }

        film.setId(idFilm++);
        films.put(film.getId(), film);

        log.info("Successful added new Film {}", film);
        return film;
    }

    public Film updateFilm(Film film){
        log.info("Request update film");

        if (film.getId() == null || film.getId() <= 0) {
            log.error("Id updatable user must not be null or less than 1");
            throw new ValidationException("Invalid film id='" + film.getId() + "' of updatable user");
        }

        if (films.get(film.getId()) == null) {
            log.error("Film with id='" + film.getId() + "'' s not exist");
            throw new ValidationException("Invalid id='" + film.getId() + "' of updatable user");
        }

        films.put(film.getId(), film);
        log.info("Successful updated film {}", film);
        return film;
    }

    public List<Film> getAllFilms() {
        log.info("Returned get all films");
        return new ArrayList<>(films.values());
    }
}
