package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.storage.impl.h2.FilmUsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FilmUsersService {

  private final FilmUsersRepository filmUsersRepository;

  public void save(Integer filmId, Integer userId) {
    filmUsersRepository.save(filmId, userId);
  }

  public void delete(Integer filmId, Integer userId) {
    filmUsersRepository.delete(filmId, userId);
  }

  public int getCountLikeByFilmId(Integer filmId) {
    return filmUsersRepository.selectCountLikeByFilmId(filmId);
  }

}
