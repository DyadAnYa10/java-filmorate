# java-filmorate
Template repository for Filmorate project.

База данных состоит из следующих таблиц:
+ users – содержит сведения о пользователях;
+ friendship_status – содержит сведения о дружбе пользователей.
+ films – содержит сведения о фильмах;
+ films_users - содержит связи между фильмами и пользователями (лайки)
+ films_genres - содержит связи между фильмами и жанрами;
+ genres – содержит справочник жанров фильмов;
+ mpa_ratings – содержит справочник рейтинга Ассоциации кинокомпаний;

![Схема базы данных](/database/scheme.jpg)

Пример кода sql запросов:
Выборка всех фильмов с жанром комедия.
```roomsql
SELECT * FROM films WHERE genre = "comedy";
```

Выбор первых 10 фильмов из всех имеющихся:
```roomsql
SELECT * FROM films LIMIT 10;
```