package ru.yandex.practicum.filmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.yandex.practicum.filmorate.constraint.DateRelease;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Film {

    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private Set<Long> userLikes = new HashSet<>();

    @EqualsAndHashCode.Exclude
    private Long id;

    @NotBlank(message = "Title of film must be not empty")
    private String name;

    @Size(min = 10, max = 200, message = "Length of film description: max = 200 and min = 10")
    @NotNull(message = "Description of film must be not null")
    private String description;

    @DateRelease(day = 28, month = 12, year = 1895, message = "Date of release must be after 28 December 1895")
    private LocalDate releaseDate;

    @Positive(message = "Duration of film must be positive value")
    private Long duration;

    @EqualsAndHashCode.Exclude
    private Rating mpa;

    @EqualsAndHashCode.Exclude
    private Set<Genre> genres = new HashSet<>();

    public void addLike(Long idUser) {
        userLikes.add(idUser);
    }

    public void deleteLike(Long idUser) {
        userLikes.remove(idUser);
    }

}
