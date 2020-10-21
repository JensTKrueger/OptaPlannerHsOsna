-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server Version:               10.4.14-MariaDB - mariadb.org binary distribution
-- Server Betriebssystem:        Win64
-- HeidiSQL Version:             11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Exportiere Datenbank Struktur für planungstoolv2
CREATE DATABASE IF NOT EXISTS planungstoolv2 /*!40100 DEFAULT CHARACTER SET utf8mb4 */;
USE planungstoolv2;

-- Exportiere Struktur von Tabelle planungstoolv2.building
CREATE TABLE IF NOT EXISTS `building` (
                                          `id` int(11) NOT NULL AUTO_INCREMENT,
                                          `name` text NOT NULL DEFAULT '',
                                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.cohort
CREATE TABLE IF NOT EXISTS `cohort` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                        `min_hours` int(11) NOT NULL DEFAULT 2,
                                        `max_hours` int(11) NOT NULL DEFAULT 4,
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.communication
CREATE TABLE IF NOT EXISTS `communication` (
                                               `id` int(11) NOT NULL AUTO_INCREMENT,
                                               `command` text NOT NULL,
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=117 DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.event
CREATE TABLE IF NOT EXISTS `event` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                       `hours_per_week` int(11) NOT NULL,
                                       `hours_per_meeting` int(11) DEFAULT NULL,
                                       `is_hard` tinyint(1) NOT NULL DEFAULT 0,
                                       PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=69 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.event_feature
CREATE TABLE IF NOT EXISTS `event_feature` (
                                               `event_id` int(11) NOT NULL,
                                               `feature_id` int(11) NOT NULL,
                                               PRIMARY KEY (`event_id`,`feature_id`),
                                               KEY `event_feature_ibfk_2` (`feature_id`),
                                               CONSTRAINT `event_feature_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                               CONSTRAINT `event_feature_ibfk_2` FOREIGN KEY (`feature_id`) REFERENCES `feature` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.event_participation
CREATE TABLE IF NOT EXISTS `event_participation` (
                                                     `event_id` int(11) NOT NULL,
                                                     `special_id` int(11) NOT NULL,
                                                     PRIMARY KEY (`special_id`,`event_id`),
                                                     KEY `event_id` (`event_id`),
                                                     CONSTRAINT `event_participation_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                                     CONSTRAINT `event_participation_ibfk_2` FOREIGN KEY (`special_id`) REFERENCES `special` (`special_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.feature
CREATE TABLE IF NOT EXISTS `feature` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `name` text NOT NULL DEFAULT '',
                                         PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.results
CREATE TABLE IF NOT EXISTS `results` (
                                         `id` int(11) NOT NULL AUTO_INCREMENT,
                                         `event_id` int(11) DEFAULT NULL,
                                         `room_id` int(11) DEFAULT NULL,
                                         `timeslot` int(11) DEFAULT NULL,
                                         `day` int(11) DEFAULT NULL,
                                         PRIMARY KEY (`id`),
                                         KEY `event_id` (`event_id`),
                                         KEY `room_id` (`room_id`),
                                         CONSTRAINT `results_ibfk_1` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                         CONSTRAINT `results_ibfk_2` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7748 DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.room
CREATE TABLE IF NOT EXISTS `room` (
                                      `id` int(11) NOT NULL AUTO_INCREMENT,
                                      `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                      `building_id` int(11) NOT NULL DEFAULT 0,
                                      `capacity` int(11) NOT NULL,
                                      PRIMARY KEY (`id`),
                                      KEY `building_id` (`building_id`),
                                      CONSTRAINT `room_ibfk_1` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.room_feature
CREATE TABLE IF NOT EXISTS `room_feature` (
                                              `room_id` int(11) NOT NULL,
                                              `feature_id` int(11) NOT NULL,
                                              PRIMARY KEY (`room_id`,`feature_id`) USING BTREE,
                                              KEY `feature_id` (`feature_id`),
                                              CONSTRAINT `room_feature_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `room` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                              CONSTRAINT `room_feature_ibfk_2` FOREIGN KEY (`feature_id`) REFERENCES `feature` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.special
CREATE TABLE IF NOT EXISTS `special` (
                                         `cohort_id` int(11) NOT NULL,
                                         `special_cat_id` int(11) NOT NULL,
                                         `special_id` int(11) NOT NULL AUTO_INCREMENT,
                                         `name` text NOT NULL DEFAULT '',
                                         `size` int(11) DEFAULT 0,
                                         PRIMARY KEY (`special_id`,`special_cat_id`,`cohort_id`) USING BTREE,
                                         KEY `special_cat_id` (`special_cat_id`),
                                         KEY `cohort_id` (`cohort_id`),
                                         CONSTRAINT `special_ibfk_1` FOREIGN KEY (`special_cat_id`) REFERENCES `special_cat` (`special_cat_id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                         CONSTRAINT `special_ibfk_2` FOREIGN KEY (`cohort_id`) REFERENCES `cohort` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.special_cat
CREATE TABLE IF NOT EXISTS `special_cat` (
                                             `cohort_id` int(11) NOT NULL,
                                             `special_cat_id` int(11) NOT NULL AUTO_INCREMENT,
                                             `name` text NOT NULL,
                                             PRIMARY KEY (`special_cat_id`,`cohort_id`) USING BTREE,
                                             KEY `cohort_id` (`cohort_id`),
                                             CONSTRAINT `special_cat_ibfk_1` FOREIGN KEY (`cohort_id`) REFERENCES `cohort` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.teacher_event
CREATE TABLE IF NOT EXISTS `teacher_event` (
                                               `teacher_id` int(11) NOT NULL,
                                               `event_id` int(11) NOT NULL,
                                               PRIMARY KEY (`teacher_id`,`event_id`) USING BTREE,
                                               KEY `event_id` (`event_id`),
                                               CONSTRAINT `teacher_event_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                                               CONSTRAINT `teacher_event_ibfk_2` FOREIGN KEY (`event_id`) REFERENCES `event` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.timepreference
CREATE TABLE IF NOT EXISTS `timepreference` (
                                                `user_id` int(11) NOT NULL,
                                                `day` int(11) NOT NULL,
                                                `timeslot` int(11) NOT NULL,
                                                `pref` int(11) NOT NULL DEFAULT 2,
                                                PRIMARY KEY (`user_id`,`day`,`timeslot`) USING BTREE,
                                                CONSTRAINT `timepreference_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.usergroup
CREATE TABLE IF NOT EXISTS `usergroup` (
                                           `id` int(11) NOT NULL,
                                           `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                           PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Daten Export vom Benutzer nicht ausgewählt

-- Exportiere Struktur von Tabelle planungstoolv2.users
CREATE TABLE IF NOT EXISTS `users` (
                                       `id` int(11) NOT NULL AUTO_INCREMENT,
                                       `title` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
                                       `prename` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                       `surname` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                       `email` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                       `phone` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                       `usergroup` int(11) NOT NULL DEFAULT 1,
                                       `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
                                       `creation_timestamp` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
                                       PRIMARY KEY (`id`) USING BTREE,
                                       KEY `users_ibfk_1` (`usergroup`),
                                       CONSTRAINT `users_ibfk_1` FOREIGN KEY (`usergroup`) REFERENCES `usergroup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Legt die Benutzergruppen an
INSERT INTO `usergroup` (id, name) VALUES ('1', 'Administrator');
INSERT INTO `usergroup` (id, name) VALUES ('2', 'Dozent');


-- Lege Administratoraccount zur ersten Benutzung an
INSERT INTO `users` (prename, surname, email, phone, usergroup, password)
VALUES ('', 'Standardbenutzer', 'admin@admin.de', '', '1', '$2y$10$0aizOrCUy6hG.zOy.WjLUOHA2mZLvx0hUc1onsRq72Rwl5T3ePqwi');

-- Daten Export vom Benutzer nicht ausgewählt

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
