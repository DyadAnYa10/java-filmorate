DROP TABLE IF EXISTS films_genres;
DROP TABLE IF EXISTS friendship;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS films_users;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS mpa;
DROP TABLE IF EXISTS users;


CREATE TABLE IF NOT EXISTS mpa (
    id SMALLINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS films (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     name VARCHAR(50),
     description VARCHAR(200),
     release_date DATE,
     duration BIGINT,
     mpa_id BIGINT,
     FOREIGN KEY (mpa_id) REFERENCES mpa
);

CREATE TABLE IF NOT EXISTS genres (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS films_genres (
    film_id BIGINT,
    genre_id INTEGER,
    PRIMARY KEY(film_id, genre_id),
    FOREIGN KEY(film_id) REFERENCES films(id),
    FOREIGN KEY(genre_id) REFERENCES genres(id)
);

CREATE TABLE IF NOT EXISTS users (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     email VARCHAR(30),
     login VARCHAR(20),
     name VARCHAR(50),
     birthday DATE
);

CREATE TABLE IF NOT EXISTS friendship (
    user_id BIGINT,
    friend_id BIGINT,
    approved BOOLEAN,
    FOREIGN KEY (user_id) REFERENCES users(id) ,
    FOREIGN KEY (friend_id) REFERENCES users(id),
    PRIMARY KEY (user_id, friend_id)
);

CREATE TABLE IF NOT EXISTS films_users (
   film_id BIGINT,
   user_id INTEGER,
   FOREIGN KEY(film_id) REFERENCES films (id),
   FOREIGN KEY(user_id) REFERENCES users (id),
   CONSTRAINT film_likes_pk PRIMARY KEY (film_id, user_id)
)