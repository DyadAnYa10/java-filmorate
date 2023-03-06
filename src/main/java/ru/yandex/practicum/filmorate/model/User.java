package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Null
    private Integer id;

    @NotBlank(message = "Email must be not empty and not null")
    @Email
    private String email;

    @NotBlank(message = "Login must be not empty and not null")
    private String login;

    private String name;

    @PastOrPresent(message = "Birthday must be in the past, not future")
    private LocalDate birthday;

}
