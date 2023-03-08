package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;

import javax.validation.Validation;
import javax.validation.Validator;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private static Validator validator;
    private UserController userController;
    private UserService userService;
    private User user;

    @BeforeAll
    static void beforeAll() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
    }

    @BeforeEach
    void init(){
        user = new User(null, "user@mail.ru", "userLogin", "userName", LocalDate.now());
        userService = new UserService();
        userController = new UserController(userService);
    }

    @Test
    void createUser_acceptId_idIsNull(){
        assertTrue(validator.validate(user).isEmpty(), "Валидатор не пропустил null в поле id");
    }

    @Test
    void getAllUsers_size1ListUsers_createOneUser(){
        userController.addUser(user);
        List<User> allUsers = userController.getAllUsers();

        assertEquals(1, allUsers.size(), "Размер списка не равен 1");
    }

    @Test
    void createUser_rejectEmail_emailIsNull(){
        user.setEmail(null);
        assertEquals(1, validator.validate(user).size(), "Валидатор пропустил null в email");
    }

    @Test
    void createUser_rejectEmail_emailIsEmpty(){
        user.setEmail("");
        assertEquals(1, validator.validate(user).size(), "Валидатор пропустил пустую строку в email");

    }

    @Test
    void createUser_acceptEmail_emailIsNotEmptyAndNotNull(){
        assertTrue(validator.validate(user).isEmpty(), "Валидатор отклонил корректное значение");
    }

    @Test
    void createUser_rejectLogin_loginIsNull(){
        user.setLogin(null);
        assertEquals(1, validator.validate(user).size(), "Валидатор пропустил null в login");

    }

    @Test
    void createUser_rejectLogin_loginIsEmpty(){
        user.setLogin("");
        assertEquals(1, validator.validate(user).size(), "Валидатор пропустил пустую строку в login");
    }

    @Test
    void createUser_acceptLogin_loginIsNotEmptyAndNotNull(){
        assertTrue(validator.validate(user).isEmpty(), "Валидатор отклонил корректное значение");
    }

    @Test
    void createUser_rejectBirthday_birthdayIsFuture(){
        user.setBirthday(LocalDate.now().plusDays(10));
        assertEquals(1, validator.validate(user).size(),
                "Валидатор пропустил значение дня рождения, позднее настоящей даты");
    }

    @Test
    void updateUser_validationException_userWithIdIsNull(){
        userController.addUser(user);

        user.setId(null);
        assertThrows(ValidationException.class, () -> userController.updateUser(user),
                "Не возникло ошибки при создании пользователя с id = null");
    }

    @Test
    void updateUser_validationException_userWithIdIsIncorrect(){
        userController.addUser(user);

        user.setId(-5);
        assertThrows(ValidationException.class, () -> userController.updateUser(user),
                "Не возникло ошибки при создании пользователя с отрицательным значением id");
    }

    @Test
    void addUser_validationException_userWithLoginIsAlreadyExistInBase(){
        userController.addUser(user);

        User newUser = new User(null, "user1@mail.ru", "userLogin", "userName", LocalDate.now());

        assertThrows(ValidationException.class, () -> userController.updateUser(newUser),
                "Не возникло ошибки при обновлении пользователя с уже имеющимся в базе логином");
    }

    @Test
    void updateUser_validationException_userIdIsNotExistInBase(){
        userController.addUser(user);

        User newUser = new User(10, "user@mail.ru", "userLogin1", "userName", LocalDate.now());

        assertThrows(ValidationException.class, () -> userController.updateUser(newUser),
                "Не возникло ошибки при обновлении пользователя с id, которого в базе нет");
    }
}