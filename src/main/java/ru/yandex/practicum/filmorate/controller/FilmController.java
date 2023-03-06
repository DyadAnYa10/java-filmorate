package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private int idFilm = 1;
    private final Map<Integer, Film> films = new HashMap<>();

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        log.info("Request add new Film");

        if (films.values().stream().anyMatch(filmSaved -> filmSaved.equals(film))) {
            log.error("Film already exist");
            throw new ValidationException("Film  already exist");
        }

        film.setId(idFilm++);
        films.put(film.getId(), film);

        log.info("Successful added new Film {}", film);
        return film;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) {
        log.info("Request update film");

        if (film.getId() == null || film.getId() <= 0) {
            log.error("Id updatable user must not be null or less than 1");
            throw new ValidationException("Invalid film id='" + film.getId() + "' of updatable user");
        }

        if(films.get(film.getId()) == null){
            log.error("Film with id='" + film.getId() + "'' s not exist");
            throw new ValidationException("Invalid id='" + film.getId() + "' of updatable user");
        }

        films.put(film.getId(), film);
        log.info("Successful updated film {}", film);
        return film;
    }

    @GetMapping
    public List<Film> getAllFilms() {
        log.info("Returned get all films");
        return new ArrayList<>(films.values());
    }
}