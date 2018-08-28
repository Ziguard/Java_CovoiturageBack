-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  mer. 21 fév. 2018 à 11:19
-- Version du serveur :  5.7.19
-- Version de PHP :  7.1.9

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `campus_services`
--

--
-- Déchargement des données de la table `role`
--

INSERT INTO `role` (`ROL_ROLE_ID`, `ROL_KEY`, `ROL_PARENT_ID`) VALUES
(1, 'USER', NULL),
(2, 'MODERATOR', 1),
(3, 'ADMIN', 2);

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`USR_USER_ID`, `USR_SCHOOL_ID`, `USR_FIRST_NAME`, `USR_LAST_NAME`, `USR_USERNAME`, `USR_PASSWORD`, `USR_EMAIL`, `USR_PHONE`, `USR_SALT`, `USR_ACTIVE`) VALUES
(1, NULL, 'Administrator', '.', 'admin', '$2a$10$IwAZVLoC6uXuWAliBKk/nOT/xsGVJLHEtnffGkKSJd3NsIq4ZmmIm', 'admin@monsite.org', '0000000000', '$2a$10$IwAZVLoC6uXuWAliBKk/nO', 1),
(2, NULL, 'Michel', 'Durand', 'micheld', '$2a$10$17C4COKBzi4q9oEWufASUe.8JvBBLCRYQ3oKi8CEAla28roj6h9Uq', 'michel.durand@yahoo.com', '0000000000', '$2a$10$17C4COKBzi4q9oEWufASUe', 1),
(3, NULL, 'Sandrine', 'Legal', 'lg.sandrine', '$2a$10$Frt76CmAKET0p1t6tAhIpOaqNxSe4nwb5NNeMSO0lTb87zaTHMrZC', 'sandrine.legal@google.com', '000000000', '$2a$10$Frt76CmAKET0p1t6tAhIpO', 1),
(4, NULL, 'User', 'user', 'user', '$2a$12$vvCe9ELvm6qJDFP1t9/8mO/1af0dZNtVuVrTCFctcfa5px9P4ifTW', 'user@gmail.com', '0000000000', '$2a$12$vvCe9ELvm6qJDFP1t9/8mO', 1);

-- admin : P@ssword [ADMIN]
-- micheld : mot2passe [USER]
-- lg.sandrine : Soleil123 [MODERATOR]
-- user : user [USER]


--
-- Déchargement des données de la table `user_roles`
--

INSERT INTO `user_roles` (`USR_USER_ID`, `USR_ROLE_ID`) VALUES
(2, 1),
(3, 2),
(1, 3),
(4, 1);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
