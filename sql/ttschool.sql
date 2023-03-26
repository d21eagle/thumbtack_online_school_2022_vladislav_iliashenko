DROP DATABASE IF EXISTS ttschool;
CREATE DATABASE `ttschool`;
USE `ttschool`;

CREATE TABLE `school`
(
    id   INT primary key auto_increment,
    name VARCHAR(50) not null,
    year INT         not null,
    CONSTRAINT school UNIQUE (name, year)
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE `group`
(
    id       INT primary key auto_increment,
    name     varchar(50) not null,
    room     varchar(50) not null,
    idSchool int         NOT NULL,
    FOREIGN KEY (idSchool) REFERENCES `school` (id)
        ON DELETE CASCADE
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE trainee
(
    id        INT primary key auto_increment,
    firstname VARCHAR(50) not null,
    lastname  VARCHAR(50) not null,
    rating    INT,
    idGroup   INT,
    FOREIGN KEY (idGroup) REFERENCES `group` (id)
        ON DELETE SET NULL
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE subject
(
    id   INT primary key auto_increment,
    name varchar(50) not null,
    CONSTRAINT subject
) ENGINE = INNODB
  DEFAULT CHARSET = utf8;

CREATE TABLE `ttschool`.`timetable`
(
    `id`         INT NOT NULL AUTO_INCREMENT,
    `id_group`   INT NOT NULL,
    `id_subject` INT NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (id_group) REFERENCES `group` (id) ON DELETE CASCADE,
    FOREIGN KEY (id_subject) REFERENCES `subject` (id) ON DELETE CASCADE
);