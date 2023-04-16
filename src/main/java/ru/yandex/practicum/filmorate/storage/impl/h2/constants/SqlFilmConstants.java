package ru.yandex.practicum.filmorate.storage.impl.h2.constants;

public class SqlFilmConstants {

    public static final String SELECT_ALL_FILMS =
            "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id, m.id, m.name " +
                    "FROM films f JOIN mpa m ON f.mpa_id = m.id";
    public static final String SELECT_FILMS_BY_FILM_ID =
            "SELECT f.id, f.name, f.description, f.release_date, f.duration, f.mpa_id FROM films f WHERE ID = ?";

    public static final String SELECT_GENRES_BY_FILM_ID =
            "SELECT g.id, g.name FROM genres g JOIN films_genres fg ON g.id = fg.genre_id WHERE fg.film_id = ?";

    public static final String SELECT_MPA_BY_FILM_ID =
            "SELECT m.id, m.name FROM mpa m JOIN films f ON f.mpa_id = m.id WHERE f.id = ?";

    public static final String SELECT_FILMS_USERS_BY_FILM_ID =
            "SELECT l.user_id FROM films f JOIN FILMS_USERS l ON f.id = l.film_id WHERE f.id = ?";

    public static final String INSERT_FILM =
            "INSERT INTO FILMS (NAME, DESCRIPTION, RELEASE_DATE, DURATION, MPA_ID) VALUES ( ?, ?, ?, ?, ?)";

    public static final String INSERT_FILMS_GENRES = "INSERT INTO films_genres (film_id, genre_id) VALUES (?, ?)";

    public static final String INSERT_FILMS_USERS = "INSERT INTO FILMS_USERS (user_id, film_id) VALUES (?, ?)";

    public static final String UPDATE_FILM_BY_ID =
            "UPDATE films SET name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ? WHERE id = ?";

    public static final String DELETE_FILMS_USERS_BY_FILM_ID = "DELETE FROM FILMS_USERS WHERE film_id = ?";

    public static final String DELETE_FILMS_GENRES_BY_FILM_ID = "DELETE FROM films_genres WHERE film_id = ?";

    public static final String DELETE_FILMS_USERS_BY_FILM_ID_AND_USER_ID =
            "DELETE FROM FILMS_USERS WHERE film_id = ? AND user_id = ?";


}
