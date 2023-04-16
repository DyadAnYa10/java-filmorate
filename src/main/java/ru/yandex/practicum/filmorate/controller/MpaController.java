package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.service.MpaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/mpa")
public class MpaController {

    private final MpaService mpaService;

    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping
    public List<Rating> getAllGenres() {
        return mpaService.getAllMpa();
    }

    @GetMapping("/{id}")
    public Rating getMpaById(@PathVariable Integer id) {
        return mpaService.getMpaRatingById(id);
    }

}
