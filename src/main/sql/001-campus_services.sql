-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  jeu. 05 juil. 2018 à 19:58
-- Version du serveur :  10.2.14-MariaDB
-- Version de PHP :  7.2.4

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

-- --------------------------------------------------------

--
-- Structure de la table `announce`
--

DROP TABLE IF EXISTS `announce`;
CREATE TABLE IF NOT EXISTS `announce` (
  `ANN_ANNOUNCE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ANN_OWNER_ID` int(11) NOT NULL,
  `ANN_TITLE` varchar(255) NOT NULL,
  `ANN_DESCRIPTION` text NOT NULL,
  `ANN_CREATED_AT` datetime NOT NULL,
  `ANN_STATUS` varchar(20) NOT NULL,
  `ANN_TYPE` varchar(20) NOT NULL,
  PRIMARY KEY (`ANN_ANNOUNCE_ID`),
  KEY `FK_ANN_USER` (`ANN_OWNER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `announce_h`
--

DROP TABLE IF EXISTS `announce_h`;
CREATE TABLE IF NOT EXISTS `announce_h` (
  `ANH_ANNOUNCE_H_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ANH_TITLE` varchar(255) NOT NULL,
  `ANH_DESCRIPTION` text NOT NULL,
  `ANH_CREATED_AT` datetime NOT NULL,
  `ANH_STATUS` varchar(20) NOT NULL,
  `ANH_REVISION` int(11) NOT NULL,
  `ANH_ORIGINAL_ID` int(11) DEFAULT NULL,
  `ANH_MODIFIER_ID` int(11) NOT NULL,
  PRIMARY KEY (`ANH_ANNOUNCE_H_ID`),
  KEY `FK_ANH_ORIGINAL` (`ANH_ORIGINAL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `article_picture`
--

DROP TABLE IF EXISTS `article_picture`;
CREATE TABLE IF NOT EXISTS `article_picture` (
  `ARP_EXCHANGE_ART_ID` int(11) NOT NULL,
  `ARP_PICTURE_ID` int(11) NOT NULL,
  PRIMARY KEY (`ARP_EXCHANGE_ART_ID`,`ARP_PICTURE_ID`),
  KEY `FK_PIC_ARP` (`ARP_PICTURE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `carpooling_a`
--

DROP TABLE IF EXISTS `carpooling_a`;
CREATE TABLE IF NOT EXISTS `carpooling_a` (
  `CAR_CARPOOLING_ID` int(11) NOT NULL AUTO_INCREMENT,
  `CAR_USER_ID` int(11) NOT NULL,
  `CAR_CREATED_AT` datetime NOT NULL,
  PRIMARY KEY (`CAR_CARPOOLING_ID`),
  KEY `FK_DRIVER` (`CAR_USER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `equipment`
--

DROP TABLE IF EXISTS `equipment`;
CREATE TABLE IF NOT EXISTS `equipment` (
  `HEQ_HOUSING_EQP_ID` int(11) NOT NULL,
  `HEQ_EQUIPMENT_KEY` varchar(30) NOT NULL,
  PRIMARY KEY (`HEQ_HOUSING_EQP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `event`
--

DROP TABLE IF EXISTS `event`;
CREATE TABLE IF NOT EXISTS `event` (
  `EVT_EVENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EVT_TITLE` varchar(255) NOT NULL,
  `EVT_START_DATE` datetime NOT NULL,
  `EVT_END_DATE` datetime DEFAULT NULL,
  `EVT_FREQUENCY` varchar(15) DEFAULT NULL,
  `EVT_OWNER_ID` int(11) NOT NULL,
  PRIMARY KEY (`EVT_EVENT_ID`),
  KEY `FK_EVT_OWNER` (`EVT_OWNER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `exchange_a`
--

DROP TABLE IF EXISTS `exchange_a`;
CREATE TABLE IF NOT EXISTS `exchange_a` (
  `EXG_EXCHANGE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EXG_EXCHANGE_CAT_ID` int(11) DEFAULT NULL,
  `EXG_EXCHANGE_TYPE` varchar(20) NOT NULL,
  PRIMARY KEY (`EXG_EXCHANGE_ID`),
  KEY `FK_EXG_CATEGORY` (`EXG_EXCHANGE_CAT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `exchange_article`
--

DROP TABLE IF EXISTS `exchange_article`;
CREATE TABLE IF NOT EXISTS `exchange_article` (
  `EXA_EXCHANGE_ART_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EXA_NAME` varchar(255) NOT NULL,
  `EXA_UNIT_PRICE` float NOT NULL,
  `EXA_EXCHANGE_PACK_ID` int(11) NOT NULL,
  PRIMARY KEY (`EXA_EXCHANGE_ART_ID`),
  KEY `FK_EXP_ARTICLE` (`EXA_EXCHANGE_PACK_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `exchange_category`
--

DROP TABLE IF EXISTS `exchange_category`;
CREATE TABLE IF NOT EXISTS `exchange_category` (
  `EXC_EXCHANGE_CAT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EXC_CATEGORY_KEY` varchar(30) NOT NULL,
  PRIMARY KEY (`EXC_EXCHANGE_CAT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `exchange_package`
--

DROP TABLE IF EXISTS `exchange_package`;
CREATE TABLE IF NOT EXISTS `exchange_package` (
  `EXP_EXCHANGE_PACK_ID` int(11) NOT NULL AUTO_INCREMENT,
  `EXP_PRICE` float DEFAULT NULL,
  `EXP_ANNOUNCE_ID` int(11) NOT NULL,
  PRIMARY KEY (`EXP_EXCHANGE_PACK_ID`),
  KEY `FK_ANN_EXCHANGE` (`EXP_ANNOUNCE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `exchange_picture`
--

DROP TABLE IF EXISTS `exchange_picture`;
CREATE TABLE IF NOT EXISTS `exchange_picture` (
  `EXP_EXCHANGE_ID` int(11) NOT NULL,
  `EXP_PICTURE_ID` int(11) NOT NULL,
  PRIMARY KEY (`EXP_EXCHANGE_ID`,`EXP_PICTURE_ID`),
  KEY `FK_EXG_PICTURE` (`EXP_PICTURE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `hobby`
--

DROP TABLE IF EXISTS `hobby`;
CREATE TABLE IF NOT EXISTS `hobby` (
  `HOB_ID` int(11) NOT NULL AUTO_INCREMENT,
  `HOB_KEY` varchar(20) NOT NULL,
  PRIMARY KEY (`HOB_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `hobby_exchange`
--

DROP TABLE IF EXISTS `hobby_exchange`;
CREATE TABLE IF NOT EXISTS `hobby_exchange` (
  `HBE_HOBBY_ID` int(11) NOT NULL,
  `HBE_EXCHANGE_ART_ID` int(11) NOT NULL,
  PRIMARY KEY (`HBE_HOBBY_ID`,`HBE_EXCHANGE_ART_ID`),
  KEY `FK_ARTICLE_PICTURE` (`HBE_EXCHANGE_ART_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `housing_a`
--

DROP TABLE IF EXISTS `housing_a`;
CREATE TABLE IF NOT EXISTS `housing_a` (
  `HSA_HOUSING_ID` int(11) NOT NULL AUTO_INCREMENT,
  `HSA_AREA` float DEFAULT NULL,
  `HSA_RENT` int(11) DEFAULT NULL,
  `HSA_ROOMS_NB` int(11) DEFAULT NULL,
  `HSA_HOUSING_TYPE` varchar(30) NOT NULL,
  PRIMARY KEY (`HSA_HOUSING_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `housing_eqpt`
--

DROP TABLE IF EXISTS `housing_eqpt`;
CREATE TABLE IF NOT EXISTS `housing_eqpt` (
  `HSE_HOUSING_ID` int(11) NOT NULL,
  `HSE_HOUSING_EQP_ID` int(11) NOT NULL,
  PRIMARY KEY (`HSE_HOUSING_ID`,`HSE_HOUSING_EQP_ID`),
  KEY `FK_EX_ANN_PACKAGES` (`HSE_HOUSING_EQP_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `housing_picture`
--

DROP TABLE IF EXISTS `housing_picture`;
CREATE TABLE IF NOT EXISTS `housing_picture` (
  `HSP_HOUSING_ID` int(11) NOT NULL,
  `HSP_PICTURE_ID` int(11) NOT NULL,
  PRIMARY KEY (`HSP_HOUSING_ID`,`HSP_PICTURE_ID`),
  KEY `FK_HSP_PICTURE` (`HSP_PICTURE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `i18n_label`
--

DROP TABLE IF EXISTS `i18n_label`;
CREATE TABLE IF NOT EXISTS `i18n_label` (
  `LAB_LABEL_LANG` varchar(5) NOT NULL,
  `LAB_LABEL_KEY` varchar(30) NOT NULL,
  `LAB_LABEL_VALUE` varchar(255) NOT NULL,
  PRIMARY KEY (`LAB_LABEL_LANG`,`LAB_LABEL_KEY`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `lag`
--

DROP TABLE IF EXISTS `lag`;
CREATE TABLE IF NOT EXISTS `lag` (
  `LAG_LAG_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LAG_HAPPEN_DATE` datetime NOT NULL,
  `LAG_CAUSE` varchar(20) NOT NULL,
  `LAG_BROADCAST_MESSAGE` longtext DEFAULT NULL,
  `lag_carpooling_id` int(11) NOT NULL,
  PRIMARY KEY (`LAG_LAG_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `location`
--

DROP TABLE IF EXISTS `location`;
CREATE TABLE IF NOT EXISTS `location` (
  `LOC_LOCATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `LOC_ADDRESS` varchar(120) NOT NULL,
  `LOC_ADDRESS_2` longtext DEFAULT NULL,
  `LOC_ADDRESS_3` longtext DEFAULT NULL,
  `LOC_LONGITUDE_GPS` float NOT NULL,
  `LOC_LATITUDE_GPS` float NOT NULL,
  `LOC_COUNTRY` longtext NOT NULL,
  `LOC_CITY` longtext NOT NULL,
  PRIMARY KEY (`LOC_LOCATION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `MES_MESSAGE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MES_SENDER_ID` int(11) NOT NULL,
  `MES_RECEIVER_ID` int(11) NOT NULL,
  `MES_SENT_AT` datetime NOT NULL,
  `MES_STATE` varchar(20) NOT NULL,
  PRIMARY KEY (`MES_MESSAGE_ID`),
  KEY `FK_MES_RECEIVER` (`MES_RECEIVER_ID`),
  KEY `FK_MES_SENDER` (`MES_SENDER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `moderation_event`
--

DROP TABLE IF EXISTS `moderation_event`;
CREATE TABLE IF NOT EXISTS `moderation_event` (
  `MEV_MOD_EVENT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `MEV_ANN_HISTORY_ID` int(11) NOT NULL,
  `MEV_MODERATOR_ID` int(11) NOT NULL,
  `MEV_ANNOUNCE_ID` int(11) NOT NULL,
  `MEV_EVENT_DATE` datetime NOT NULL,
  `MEV_COMMENT` text DEFAULT NULL,
  `MEV_EVENT_TYPE` varchar(20) NOT NULL,
  `MEV_REJECTION_CAUSE` longtext NOT NULL,
  PRIMARY KEY (`MEV_MOD_EVENT_ID`),
  KEY `FK_MEV_ANNOUNCE` (`MEV_ANNOUNCE_ID`),
  KEY `FK_MEV_HISTORY` (`MEV_ANN_HISTORY_ID`),
  KEY `FK_MEV_MODERATOR` (`MEV_MODERATOR_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `notation`
--

DROP TABLE IF EXISTS `notation`;
CREATE TABLE IF NOT EXISTS `notation` (
  `NOT_NOTATION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NOT_RANKER_ID` int(11) NOT NULL,
  `NOT_RANKED_ID` int(11) NOT NULL,
  `NOT_NOTE_VALUE` float NOT NULL,
  `NOT_COMMENT` longtext DEFAULT NULL,
  `NOT_LEFT_AT` datetime NOT NULL,
  PRIMARY KEY (`NOT_NOTATION_ID`),
  KEY `FK_IS_NOTED_BY` (`NOT_RANKED_ID`),
  KEY `FK_SUBMITS` (`NOT_RANKER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `passenger`
--

DROP TABLE IF EXISTS `passenger`;
CREATE TABLE IF NOT EXISTS `passenger` (
  `PAS_CARPOOLING_ID` int(11) NOT NULL,
  `PAS_USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`PAS_CARPOOLING_ID`,`PAS_USER_ID`),
  KEY `FK_PAS_USER` (`PAS_USER_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `picture`
--

DROP TABLE IF EXISTS `picture`;
CREATE TABLE IF NOT EXISTS `picture` (
  `PIC_PICTURE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PIC_STORAGE_PATH` varchar(255) NOT NULL,
  `PIC_ORIGINAL_NAME` varchar(255) NOT NULL,
  `PIC_MIME_TYPE` varchar(255) NOT NULL,
  PRIMARY KEY (`PIC_PICTURE_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `role`
--

DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `ROL_ROLE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ROL_KEY` varchar(20) NOT NULL,
  `ROL_PARENT_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`ROL_ROLE_ID`),
  UNIQUE KEY `ROL_KEY` (`ROL_KEY`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `school`
--

DROP TABLE IF EXISTS `school`;
CREATE TABLE IF NOT EXISTS `school` (
  `SCH_SCHOOL_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SCH_NAME` varchar(100) NOT NULL,
  `SCH_LOCATION_ID` int(11) DEFAULT NULL,
  PRIMARY KEY (`SCH_SCHOOL_ID`),
  KEY `FK_SCH_LOCATION` (`SCH_LOCATION_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `service_a`
--

DROP TABLE IF EXISTS `service_a`;
CREATE TABLE IF NOT EXISTS `service_a` (
  `SVA_SERVICE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SVA_SERVICE_CAT_ID` int(11) DEFAULT NULL,
  `SVA_PRICE` float DEFAULT NULL,
  PRIMARY KEY (`SVA_SERVICE_ID`),
  KEY `FK_SVA_CATEGORY` (`SVA_SERVICE_CAT_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `service_category`
--

DROP TABLE IF EXISTS `service_category`;
CREATE TABLE IF NOT EXISTS `service_category` (
  `SVC_SERVICE_CAT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `SVC_SERVICE_KEY` varchar(30) NOT NULL,
  PRIMARY KEY (`SVC_SERVICE_CAT_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `step`
--

DROP TABLE IF EXISTS `step`;
CREATE TABLE IF NOT EXISTS `step` (
  `STP_STEP_ID` int(11) NOT NULL AUTO_INCREMENT,
  `STP_CARPOOLING_ID` int(11) NOT NULL,
  `STP_LOCATION_ID` int(11) NOT NULL,
  `STP_ORDER` int(11) NOT NULL,
  `STP_ESTIMATED_DEPARTURE_TIME` datetime NOT NULL,
  `STP_EFFECTIVE_DEPARTURE_TIME` datetime NOT NULL,
  `STP_ESTIMATED_ARRIVING_TIME` datetime NOT NULL,
  `STP_EFFECTIVE_ARRIVING_TIME` datetime NOT NULL,
  PRIMARY KEY (`STP_STEP_ID`),
  KEY `FK_IS_COMPOSED_OF` (`STP_CARPOOLING_ID`),
  KEY `FK_IS_LOCATED_AT` (`STP_LOCATION_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `USR_USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `USR_SCHOOL_ID` int(11) DEFAULT NULL,
  `USR_FIRST_NAME` varchar(40) NOT NULL,
  `USR_LAST_NAME` varchar(40) NOT NULL,
  `USR_USERNAME` varchar(40) NOT NULL,
  `USR_PASSWORD` varchar(128) NOT NULL,
  `USR_EMAIL` varchar(80) NOT NULL,
  `USR_PHONE` varchar(15) DEFAULT NULL,
  `USR_SALT` varchar(255) NOT NULL,
  `USR_CREATION_DATETIME` datetime DEFAULT NULL,
  `USR_ACTIVE` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`USR_USER_ID`),
  UNIQUE KEY `UNQ_USERNAME` (`USR_USERNAME`),
  UNIQUE KEY `UNQ_EMAIL` (`USR_EMAIL`),
  KEY `FK_STUDIES_AT` (`USR_SCHOOL_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `user_hobbies`
--

DROP TABLE IF EXISTS `user_hobbies`;
CREATE TABLE IF NOT EXISTS `user_hobbies` (
  `USH_USER_ID` int(11) NOT NULL,
  `USH_HOBBY_ID` int(11) NOT NULL,
  PRIMARY KEY (`USH_USER_ID`,`USH_HOBBY_ID`),
  KEY `FK_HOUSING_EQPT` (`USH_HOBBY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `user_roles`
--

DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE IF NOT EXISTS `user_roles` (
  `USR_USER_ID` int(11) NOT NULL,
  `USR_ROLE_ID` int(11) NOT NULL,
  PRIMARY KEY (`USR_USER_ID`,`USR_ROLE_ID`),
  KEY `FK_USR_ROLE` (`USR_ROLE_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `announce`
--
ALTER TABLE `announce`
  ADD CONSTRAINT `FK_ANN_USER` FOREIGN KEY (`ANN_OWNER_ID`) REFERENCES `user` (`USR_USER_ID`);

--
-- Contraintes pour la table `announce_h`
--
ALTER TABLE `announce_h`
  ADD CONSTRAINT `FK_ANH_ORIGINAL` FOREIGN KEY (`ANH_ORIGINAL_ID`) REFERENCES `announce` (`ANN_ANNOUNCE_ID`);

--
-- Contraintes pour la table `article_picture`
--
ALTER TABLE `article_picture`
  ADD CONSTRAINT `FK_ARP_EXA` FOREIGN KEY (`ARP_EXCHANGE_ART_ID`) REFERENCES `exchange_article` (`EXA_EXCHANGE_ART_ID`),
  ADD CONSTRAINT `FK_PIC_ARP` FOREIGN KEY (`ARP_PICTURE_ID`) REFERENCES `picture` (`PIC_PICTURE_ID`);

--
-- Contraintes pour la table `carpooling_a`
--
ALTER TABLE `carpooling_a`
  ADD CONSTRAINT `FK_DRIVER` FOREIGN KEY (`CAR_USER_ID`) REFERENCES `user` (`USR_USER_ID`);

--
-- Contraintes pour la table `event`
--
ALTER TABLE `event`
  ADD CONSTRAINT `FK_EVT_OWNER` FOREIGN KEY (`EVT_OWNER_ID`) REFERENCES `user` (`USR_USER_ID`);

--
-- Contraintes pour la table `exchange_a`
--
ALTER TABLE `exchange_a`
  ADD CONSTRAINT `FK_EXG_CATEGORY` FOREIGN KEY (`EXG_EXCHANGE_CAT_ID`) REFERENCES `exchange_category` (`EXC_EXCHANGE_CAT_ID`);

--
-- Contraintes pour la table `exchange_article`
--
ALTER TABLE `exchange_article`
  ADD CONSTRAINT `FK_EXP_ARTICLE` FOREIGN KEY (`EXA_EXCHANGE_PACK_ID`) REFERENCES `exchange_package` (`EXP_EXCHANGE_PACK_ID`);

--
-- Contraintes pour la table `exchange_package`
--
ALTER TABLE `exchange_package`
  ADD CONSTRAINT `FK_ANN_EXCHANGE` FOREIGN KEY (`EXP_ANNOUNCE_ID`) REFERENCES `exchange_a` (`EXG_EXCHANGE_ID`);

--
-- Contraintes pour la table `exchange_picture`
--
ALTER TABLE `exchange_picture`
  ADD CONSTRAINT `FK_EXG_PICTURE` FOREIGN KEY (`EXP_PICTURE_ID`) REFERENCES `picture` (`PIC_PICTURE_ID`);

--
-- Contraintes pour la table `hobby_exchange`
--
ALTER TABLE `hobby_exchange`
  ADD CONSTRAINT `FK_ARTICLE_PICTURE` FOREIGN KEY (`HBE_EXCHANGE_ART_ID`) REFERENCES `exchange_article` (`EXA_EXCHANGE_ART_ID`),
  ADD CONSTRAINT `FK_EX_PACK_ARTICLES` FOREIGN KEY (`HBE_HOBBY_ID`) REFERENCES `hobby` (`HOB_ID`);

--
-- Contraintes pour la table `housing_eqpt`
--
ALTER TABLE `housing_eqpt`
  ADD CONSTRAINT `FK_EX_ANN_PACKAGES` FOREIGN KEY (`HSE_HOUSING_EQP_ID`) REFERENCES `equipment` (`HEQ_HOUSING_EQP_ID`),
  ADD CONSTRAINT `FK_HOBBY_EXCHANGE` FOREIGN KEY (`HSE_HOUSING_ID`) REFERENCES `housing_a` (`HSA_HOUSING_ID`);

--
-- Contraintes pour la table `housing_picture`
--
ALTER TABLE `housing_picture`
  ADD CONSTRAINT `FK_HSP_HOUSING` FOREIGN KEY (`HSP_HOUSING_ID`) REFERENCES `housing_a` (`HSA_HOUSING_ID`),
  ADD CONSTRAINT `FK_HSP_PICTURE` FOREIGN KEY (`HSP_PICTURE_ID`) REFERENCES `picture` (`PIC_PICTURE_ID`);

--
-- Contraintes pour la table `message`
--
ALTER TABLE `message`
  ADD CONSTRAINT `FK_MES_RECEIVER` FOREIGN KEY (`MES_RECEIVER_ID`) REFERENCES `user` (`USR_USER_ID`),
  ADD CONSTRAINT `FK_MES_SENDER` FOREIGN KEY (`MES_SENDER_ID`) REFERENCES `user` (`USR_USER_ID`);

--
-- Contraintes pour la table `moderation_event`
--
ALTER TABLE `moderation_event`
  ADD CONSTRAINT `FK_MEV_ANNOUNCE` FOREIGN KEY (`MEV_ANNOUNCE_ID`) REFERENCES `announce` (`ANN_ANNOUNCE_ID`),
  ADD CONSTRAINT `FK_MEV_HISTORY` FOREIGN KEY (`MEV_ANN_HISTORY_ID`) REFERENCES `announce_h` (`ANH_ANNOUNCE_H_ID`),
  ADD CONSTRAINT `FK_MEV_MODERATOR` FOREIGN KEY (`MEV_MODERATOR_ID`) REFERENCES `user` (`USR_USER_ID`);

--
-- Contraintes pour la table `notation`
--
ALTER TABLE `notation`
  ADD CONSTRAINT `FK_IS_NOTED_BY` FOREIGN KEY (`NOT_RANKED_ID`) REFERENCES `user` (`USR_USER_ID`),
  ADD CONSTRAINT `FK_SUBMITS` FOREIGN KEY (`NOT_RANKER_ID`) REFERENCES `user` (`USR_USER_ID`);

--
-- Contraintes pour la table `passenger`
--
ALTER TABLE `passenger`
  ADD CONSTRAINT `FK_PAS_CARPOOLING` FOREIGN KEY (`PAS_CARPOOLING_ID`) REFERENCES `carpooling_a` (`CAR_CARPOOLING_ID`),
  ADD CONSTRAINT `FK_PAS_USER` FOREIGN KEY (`PAS_USER_ID`) REFERENCES `user` (`USR_USER_ID`);

--
-- Contraintes pour la table `school`
--
ALTER TABLE `school`
  ADD CONSTRAINT `FK_SCH_LOCATION` FOREIGN KEY (`SCH_LOCATION_ID`) REFERENCES `location` (`LOC_LOCATION_ID`);

--
-- Contraintes pour la table `service_a`
--
ALTER TABLE `service_a`
  ADD CONSTRAINT `FK_SVA_CATEGORY` FOREIGN KEY (`SVA_SERVICE_CAT_ID`) REFERENCES `service_category` (`SVC_SERVICE_CAT_ID`);

--
-- Contraintes pour la table `step`
--
ALTER TABLE `step`
  ADD CONSTRAINT `FK_IS_COMPOSED_OF` FOREIGN KEY (`STP_CARPOOLING_ID`) REFERENCES `carpooling_a` (`CAR_CARPOOLING_ID`),
  ADD CONSTRAINT `FK_IS_LOCATED_AT` FOREIGN KEY (`STP_LOCATION_ID`) REFERENCES `location` (`LOC_LOCATION_ID`);

--
-- Contraintes pour la table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK_STUDIES_AT` FOREIGN KEY (`USR_SCHOOL_ID`) REFERENCES `school` (`SCH_SCHOOL_ID`);

--
-- Contraintes pour la table `user_hobbies`
--
ALTER TABLE `user_hobbies`
  ADD CONSTRAINT `FK_HOUSING_EQPT` FOREIGN KEY (`USH_HOBBY_ID`) REFERENCES `hobby` (`HOB_ID`),
  ADD CONSTRAINT `FK_USER_HOBBIES` FOREIGN KEY (`USH_USER_ID`) REFERENCES `user` (`USR_USER_ID`);

--
-- Contraintes pour la table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `FK_USR_ROLE` FOREIGN KEY (`USR_ROLE_ID`) REFERENCES `role` (`ROL_ROLE_ID`),
  ADD CONSTRAINT `FK_USR_USER` FOREIGN KEY (`USR_USER_ID`) REFERENCES `user` (`USR_USER_ID`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
