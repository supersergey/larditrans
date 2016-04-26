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

DROP TABLE IF EXISTS hibernate_sequences;
CREATE TABLE hibernate_sequences (
  sequence_name varchar(255) NOT NULL,
  next_val bigint(20) DEFAULT NULL,
  PRIMARY KEY (`sequence_name`)
) DEFAULT CHARSET=utf8;

LOCK TABLES entry WRITE;
/*!40000 ALTER TABLE `entry` DISABLE KEYS */;
INSERT INTO entry VALUES (2,'c. �������, ���������� �������','+380631234567','sheva@ukr.net','�����','��������','�����������','0441234567',1),(3,'�. ������','0667989898','kov@ukr.net','���������','���������','��������','0667989898',1),(4,'�������','0500505050','aliev@tashkent.com','�������','�����','�����������','0500505050',1),(5,'','+380997776655','','�����','������','��������','',1),(6,'','+380501239966','','�����','�����������','����������','',1),(7,'','0634566699','','�������','��������','����������','0634566699',1),(8,'','+380663336644','','�����','������','����������','',1),(9,'Velika Vasulkivska Str','+380637820407','','����','��������','��������','+380637820407',1),(10,'','+380995554433','','������','�����','����������','',1),(11,'','+380447778899','','������','��������','��������','',1),(12,'','+380998887700','','�����','��������','��������','',1),(13,'','+380778889932','','�����','��������','����������','',1),(14,'','+381214546581','','����','��������','����������','',1);
/*!40000 ALTER TABLE `entry` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES user WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO user VALUES (1,'Test User','test','123456');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

LOCK TABLES hibernate_sequences WRITE;
/*!40000 ALTER TABLE `hibernate_sequences` DISABLE KEYS */;
INSERT INTO hibernate_sequences VALUES ('default',15);
/*!40000 ALTER TABLE `hibernate_sequences` ENABLE KEYS */;
UNLOCK TABLES;


