package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.impl.h2.MpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@Service
public class MpaService {

    private final MpaRepository mpaRepository;

    public List<Rating> getAllMpa() {
        return mpaRepository.findAll();
    }

    public Rating getMpaRatingById(Integer id) {
        try {
            return mpaRepository.findById(id).get();
        } catch (DataAccessException exception) {
            throw new NoSuchElementException("Mpa rating with id='" + id + "' not found");
        }
    }

}
