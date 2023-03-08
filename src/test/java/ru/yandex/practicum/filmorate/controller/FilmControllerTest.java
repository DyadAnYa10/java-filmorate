package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {

    private FilmController filmController;
    private FilmService filmService;
    private Film film;
    private static Validator validator;

    @BeforeAll
    static void beforeAll() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    void init(){
        film = new Film(null, "Title film", "Description film", LocalDate.now(), 60);
        filmService = new FilmService();
        filmController = new FilmController(filmService);
    }

    @Test
    void createFilm_acceptId_idIsNull(){
        assertTrue(validator.validate(film).isEmpty(), "Валидатор не пропустил значение null в id");
    }


    @Test
    void getAllFilms_size1List(){
        filmController.addFilm(film);
        List<Film> allFilms = filmController.getAllFilms();
        assertEquals(1, allFilms.size(), "Размер списка не равен 1");
    }

    @Test
    void Film_rejectName_nameIsEmpty(){
        film.setName("");
        assertEquals(1, validator.validate(film).size(),
                "Валидатор пропустил пустую строку в поле name");
    }

    @Test
    void addFilm_rejectName_nameIsNull(){
        film.setName(null);
        assertEquals(1, validator.validate(film).size(), "Валидатор пропустил null в поле name");
    }

    @Test
    void addFilm_rejectName_nameIsNotEmptyAndNotNull(){
        assertTrue(validator.validate(film).isEmpty(), "Валидатор отклонил корректное значение");
    }

    @Test
    void addFilm_rejectDescription_descriptionIsNotEmptyAndNotNull(){
        assertTrue(validator.validate(film).isEmpty(), "Валидатор отклонил корректное значение");
    }

    @Test
    void addFilm_rejectDescription_descriptionIsNull(){
        film.setDescription(null);
        assertEquals(1, validator.validate(film).size(), "Валидатор пропустил null в поле description");
    }

    @Test
    void addFilm_rejectDescription_descriptionIsEmpty(){
        film.setDescription("");
        assertEquals(1, validator.validate(film).size(),
                "Валидатор пропустил пустое значение в поле description");
    }

    @Test
    void addFilm_rejectReleaseDate_releaseDateIsIncorrect(){
        film.setReleaseDate(LocalDate.of(1000, 1, 1));
        assertEquals(1, validator.validate(film).size(),
                "Валидатор пропустил значение даты релизы больше указанной в настройках создания");
    }

    @Test
    void addFilm_acceptReleaseDate_releaseDateIsCorrect(){
        assertTrue(validator.validate(film).isEmpty(), "Валидатор отклонил корректное значение");
    }

    @Test
    void addFilm_rejectDuration_durationIsCorrect(){
        assertTrue(validator.validate(film).isEmpty(), "Валидатор отклонил корректное значение");
    }

    @Test
    void addFilm_validationException_filmAlreadyExist(){
        filmController.addFilm(film);

        Film newFilm = new Film(null, "Title film", "Description film", LocalDate.now(), 60);

        assertThrows(ValidationException.class, () -> filmController.addFilm(newFilm));
    }

    @Test
    void updateFilm_validationException_filmIdIsNull(){
        filmController.addFilm(film);

        Film newFilm = new Film(null, "Title film", "Description film", LocalDate.now(), 60);

        assertThrows(ValidationException.class, () -> filmController.updateFilm(newFilm),
                "Не возникло ошибки при обновлении фильма с id = null");
    }

    @Test
    void updateFilm_validationException_filmIdIsIncorrect(){
        filmController.addFilm(film);

        Film newFilm = new Film(-5, "Title film", "Description film", LocalDate.now(), 60);

        assertThrows(ValidationException.class, () -> filmController.updateFilm(newFilm),
                "Не возникло ошибки при обновлении фильма с некорректным значением id");
    }

    @Test
    void updateFilm_validationException_filmIdIsNotExist(){
        filmController.addFilm(film);

        Film newFilm = new Film(10, "Title film", "Description film", LocalDate.now(), 60);

        assertThrows(ValidationException.class, () -> filmController.updateFilm(newFilm),
                "Не возникло ошибки при обновлении фильма со значением id, которого нет в базе");
    }

}
