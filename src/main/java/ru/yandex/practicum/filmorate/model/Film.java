package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.constraint.DateRelease;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

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
    private Integer duration;

    private final Set<Integer> userLikes = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Film film = (Film) o;
        return Objects.equals(name, film.name)
                && Objects.equals(description, film.description)
                && Objects.equals(releaseDate, film.releaseDate)
                && Objects.equals(duration, film.duration);
    }

    public void addLike(Integer idUser){
        userLikes.add(idUser);
    }

    public void deleteLike(Integer idUser) {
        userLikes.remove(idUser);
    }
}
