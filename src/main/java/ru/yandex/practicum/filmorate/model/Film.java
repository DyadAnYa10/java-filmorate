package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.constraint.DateRelease;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Film {

    @Null
    private Integer id;

    @NotBlank(message = "Title of film can must be not empty")
    private String name;

    @Size(min = 10, max = 200, message = "Length of film description mist be: max = 200 characters, min = 10 characters")
    @NotNull
    private String description;

    @DateRelease(day = 28, month = 12, year = 1895, message = "Date of release must be after than 28 December 1895")
    private LocalDate releaseDate;

    @Positive(message = "Duration of film must be positive value")
    private int duration;

}
