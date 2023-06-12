DROP DATABASE IF EXISTS hiring;
CREATE DATABASE `hiring`;
USE `hiring`;

CREATE TABLE user
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email VARCHAR(50) NOT NULL,
    login VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    lastName VARCHAR(50) NOT NULL,
    middleName VARCHAR(50),
    firstName VARCHAR(50) NOT NULL
)  ENGINE = INNODB
DEFAULT CHARSET = utf8;


CREATE TABLE employee
(
    id INT(11) NOT NULL PRIMARY KEY,
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
)  ENGINE = INNODB
DEFAULT CHARSET = utf8;


CREATE TABLE employer
(
    id INT(11) NOT NULL PRIMARY KEY,
    companyName VARCHAR(50) NOT NULL,
    companyAddress VARCHAR(50) NOT NULL,
    FOREIGN KEY (id) REFERENCES user(id) ON DELETE CASCADE
)  ENGINE = INNODB
DEFAULT CHARSET = utf8;


CREATE TABLE vacancy
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    position VARCHAR(50) NOT NULL,
    salary INT NOT NULL,
    employer_id INT(11) NOT NULL,
    FOREIGN KEY (employer_id) REFERENCES employer(id) ON DELETE CASCADE
)  ENGINE = INNODB
DEFAULT CHARSET = utf8;


CREATE TABLE skill
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    skillName VARCHAR(50) NOT NULL,
    profLevel INT(1) NOT NULL,
    employee_id INT(11) NOT NULL,
    FOREIGN KEY (employee_id) REFERENCES employee(id) ON DELETE CASCADE
)  ENGINE = INNODB
DEFAULT CHARSET = utf8;


CREATE TABLE vacancy_requirement
(
    id INT(11) NOT NULL PRIMARY KEY AUTO_INCREMENT,
    requirementName VARCHAR(50) NOT NULL,
    profLevel INT(1) NOT NULL,
    isNecessary BOOL NOT NULL,
    vacancy_id INT(11) NOT NULL,
    FOREIGN KEY (vacancy_id) REFERENCES vacancy(id) ON DELETE CASCADE
)  ENGINE = INNODB
DEFAULT CHARSET = utf8;

CREATE TABLE session (
  id INT NOT NULL,
  uuid VARCHAR(36),
  PRIMARY KEY (id)
)  ENGINE = INNODB
DEFAULT CHARSET = utf8;




