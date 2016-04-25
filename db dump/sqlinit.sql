CREATE DATABASE IF NOT EXISTS larditrans;
USE larditrans;

DROP TABLE IF EXISTS entry;
CREATE TABLE entry
(
    id BIGINT PRIMARY KEY NOT NULL,
    address VARCHAR(255),
    cellNumber VARCHAR(255) NOT NULL,
    email VARCHAR(255),
    firstName VARCHAR(255) NOT NULL,
    lastName VARCHAR(255) NOT NULL,
    patronymic VARCHAR(255) NOT NULL,
    phoneNumber VARCHAR(255),
    owner_id BIGINT
) DEFAULT CHARSET=utf8;
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id BIGINT PRIMARY KEY NOT NULL,
    login VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    fullName VARCHAR(500) NOT NULL
) DEFAULT CHARSET=utf8;

ALTER TABLE entry ADD FOREIGN KEY (owner_id) REFERENCES user (id);
CREATE INDEX foreign_index ON entry (owner_id);
CREATE UNIQUE INDEX unique_id ON user (id);
CREATE UNIQUE INDEX unique_login ON user (login);

CREATE TABLE hibernate_sequences (
  sequence_name varchar(255) NOT NULL,
  next_val bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sequence_name`)
) DEFAULT CHARSET=utf8;