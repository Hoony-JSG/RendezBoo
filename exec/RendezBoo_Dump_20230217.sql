CREATE DATABASE  IF NOT EXISTS `a107_mysql` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `a107_mysql`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: i8a107.p.ssafy.io    Database: a107_mysql
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `authority`
--

DROP TABLE IF EXISTS `authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `authority` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `role` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_seq` int unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `FKqdtnr1vq6yk6scwpdvsncei3u` (`user_seq`),
  CONSTRAINT `FKqdtnr1vq6yk6scwpdvsncei3u` FOREIGN KEY (`user_seq`) REFERENCES `user` (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `authority`
--

LOCK TABLES `authority` WRITE;
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
INSERT INTO `authority` VALUES (1,'ROLE_USER',1),(2,'ROLE_USER',2),(3,'ROLE_USER',3),(4,'ROLE_USER',4),(5,'ROLE_USER',5),(6,'ROLE_USER',6),(7,'ROLE_ADMIN',7),(8,'ROLE_USER',7),(9,'ROLE_ADMIN',8),(10,'ROLE_USER',8),(12,'ROLE_USER',10),(13,'ROLE_USER',11),(14,'ROLE_USER',12),(15,'ROLE_USER',13),(16,'ROLE_USER',14),(17,'ROLE_USER',15),(19,'ROLE_USER',17),(20,'ROLE_USER',18),(21,'ROLE_USER',19),(26,'ROLE_USER',24),(27,'ROLE_USER',25);
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `badge`
--

DROP TABLE IF EXISTS `badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `badge` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `url` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `description` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `badge`
--

LOCK TABLES `badge` WRITE;
/*!40000 ALTER TABLE `badge` DISABLE KEYS */;
INSERT INTO `badge` VALUES (1,'첫 만남','images/dock1-1.png','첫 일대일 도킹 완료'),(2,'익숙한 만남','images/dock1-5.png','일대일 도킹 5회 완료'),(3,'바람둥이','images/dock1-10.png','일대일 도킹 10회 완료'),(4,'다다익선 I','images/dock3-1.png','첫 다대다 도킹 완료'),(5,'다다익선 II','images/dock3-5.png','다대다 도킹 5회 완료'),(6,'카사노바','images/dock3-5.png','다대다 도킹 10회 완료'),(7,'분노 I','images/anger1.png','첫 분노 유발'),(8,'분노 II','images/anger5.png','분노 5회 유발'),(9,'분노유발자','images/anger10.png','분노 10회 유발'),(10,'당황 I','images/disgust1.png','첫 당황 유발'),(11,'당황 II','images/disgust5.png','당황 5회 유발'),(12,'헉','images/disgust10.png','당황 10회 유발'),(13,'공포 I','images/fear1.png','첫 공포 유발'),(14,'공포 II','images/fear5.png','공포 5회 유발'),(15,'극한의 공포','images/fear10.png','공포 10회 유발'),(16,'행복 I','images/happy1.png','첫 행복 유발'),(17,'행복 II','images/happy5.png','행복 5회 유발'),(18,'행복전도사','images/happy10.png','행복 10회 유발'),(19,'슬픔 I','images/sad1.png','첫 슬픔 유발'),(20,'슬픔 II','images/sad5.png','슬픔 5회 유발'),(21,'인간착즙기','images/sad10.png','슬픔 10회 유발'),(22,'놀람 I','images/surprised1.png','첫 놀람 유발'),(23,'놀람 II','images/surprised5.png','놀람 5회 유발'),(24,'상상도 못한 정체','images/surprised10.png','놀람 10회 유발');
/*!40000 ALTER TABLE `badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `badge_condition`
--

DROP TABLE IF EXISTS `badge_condition`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `badge_condition` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `anger_count` int DEFAULT NULL,
  `contempt_count` int DEFAULT NULL,
  `created_at` datetime DEFAULT NULL,
  `disgust_count` int DEFAULT NULL,
  `fear_count` int DEFAULT NULL,
  `fin_count` int DEFAULT NULL,
  `happiness_count` int DEFAULT NULL,
  `item_count` int DEFAULT NULL,
  `neutral_count` int DEFAULT NULL,
  `sadness_count` int DEFAULT NULL,
  `surprise_count` int DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `user_seq` bigint DEFAULT NULL,
  `many_to_many_fin_count` int DEFAULT NULL,
  `one_to_one_fin_count` int DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `badge_condition`
--

LOCK TABLES `badge_condition` WRITE;
/*!40000 ALTER TABLE `badge_condition` DISABLE KEYS */;
INSERT INTO `badge_condition` VALUES (1,0,NULL,'2023-02-14 13:09:29',0,0,NULL,0,0,NULL,0,0,'2023-02-14 13:09:29',9,0,0),(2,0,NULL,'2023-02-14 13:33:58',0,0,NULL,1,0,NULL,0,0,'2023-02-15 09:21:22',10,0,1),(3,0,NULL,'2023-02-14 13:35:42',0,0,NULL,5,0,NULL,2,0,'2023-02-17 08:31:17',11,0,7),(4,0,NULL,'2023-02-14 13:37:41',0,0,NULL,0,0,NULL,0,0,'2023-02-14 13:37:41',12,0,0),(5,0,NULL,'2023-02-14 13:43:05',0,0,NULL,0,0,NULL,0,0,'2023-02-14 13:43:05',13,0,0),(6,1,NULL,'2023-02-14 13:44:03',0,0,NULL,0,0,NULL,0,0,'2023-02-15 09:21:22',14,0,1),(7,0,NULL,'2023-02-14 13:45:45',0,0,NULL,1,0,NULL,6,0,'2023-02-17 08:31:16',15,0,7),(8,0,NULL,'2023-02-14 13:48:00',0,0,NULL,0,0,NULL,0,0,'2023-02-14 13:48:00',16,0,0),(9,0,NULL,'2023-02-14 14:47:14',0,0,NULL,0,0,NULL,0,0,'2023-02-14 14:47:14',17,0,0),(10,0,NULL,'2023-02-14 16:20:52',0,0,NULL,2,0,NULL,1,0,'2023-02-14 16:33:48',18,0,3),(11,0,NULL,'2023-02-14 16:27:24',0,0,NULL,3,0,NULL,0,0,'2023-02-14 16:33:35',19,0,3),(12,0,NULL,NULL,0,0,NULL,1,0,NULL,0,0,'2023-02-14 17:16:07',1,0,1),(13,0,NULL,NULL,0,0,NULL,1,0,NULL,0,1,'2023-02-16 15:05:04',4,0,2),(14,0,NULL,'2023-02-14 23:03:53',0,0,NULL,0,0,NULL,0,0,'2023-02-14 23:03:53',20,0,0),(15,0,NULL,'2023-02-14 23:07:33',0,0,NULL,0,0,NULL,0,0,'2023-02-14 23:07:33',21,0,0),(16,0,NULL,'2023-02-14 23:11:53',0,0,NULL,0,0,NULL,0,0,'2023-02-14 23:11:53',22,0,0),(17,0,NULL,'2023-02-14 23:18:42',0,0,NULL,0,0,NULL,0,0,'2023-02-14 23:18:42',23,0,0),(18,0,NULL,'2023-02-16 17:10:09',0,0,NULL,0,0,NULL,0,0,'2023-02-16 17:10:09',24,0,0),(19,0,NULL,'2023-02-17 08:57:58',0,0,NULL,0,0,NULL,0,0,'2023-02-17 08:57:58',25,0,0);
/*!40000 ALTER TABLE `badge_condition` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat_room`
--

DROP TABLE IF EXISTS `chat_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat_room` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `user_female_seq` int unsigned DEFAULT NULL,
  `user_male_seq` int unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `FKb8eudg6d7h0b110im1tfkb5q` (`user_female_seq`),
  KEY `FKjq3kq2ny7qsvjbipg6gnava9c` (`user_male_seq`),
  CONSTRAINT `FKb8eudg6d7h0b110im1tfkb5q` FOREIGN KEY (`user_female_seq`) REFERENCES `user` (`seq`),
  CONSTRAINT `FKjq3kq2ny7qsvjbipg6gnava9c` FOREIGN KEY (`user_male_seq`) REFERENCES `user` (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat_room`
--

LOCK TABLES `chat_room` WRITE;
/*!40000 ALTER TABLE `chat_room` DISABLE KEYS */;
INSERT INTO `chat_room` VALUES (1,'2023-02-13 09:00:18',2,4),(2,'2023-02-13 10:27:51',3,4),(3,'2023-02-14 15:01:08',11,15),(4,'2023-02-14 16:31:22',19,18),(5,'2023-02-14 17:16:09',1,4),(6,'2023-02-15 09:21:23',14,10),(11,NULL,NULL,NULL),(12,NULL,NULL,NULL),(13,'2023-02-17 02:11:48',1,6),(14,'2023-02-17 03:04:06',2,6),(15,'2023-02-17 08:54:35',3,10);
/*!40000 ALTER TABLE `chat_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `emotion_data`
--

DROP TABLE IF EXISTS `emotion_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `emotion_data` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `anger` double NOT NULL,
  `contempt` double NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `disgust` double NOT NULL,
  `fear` double NOT NULL,
  `happiness` double NOT NULL,
  `neutral` double NOT NULL,
  `sadness` double NOT NULL,
  `surprise` double NOT NULL,
  `meeting_room_seq` int unsigned NOT NULL,
  `user_seq` int unsigned NOT NULL,
  PRIMARY KEY (`seq`),
  KEY `FK1d4w2ewee6y0d6rfymgbxkwy2` (`meeting_room_seq`),
  KEY `FK1r4hknjana0q3csklv4q4ehwy` (`user_seq`),
  CONSTRAINT `FK1d4w2ewee6y0d6rfymgbxkwy2` FOREIGN KEY (`meeting_room_seq`) REFERENCES `oneto_one_meeting_room` (`seq`),
  CONSTRAINT `FK1r4hknjana0q3csklv4q4ehwy` FOREIGN KEY (`user_seq`) REFERENCES `user` (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=105 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `emotion_data`
--

LOCK TABLES `emotion_data` WRITE;
/*!40000 ALTER TABLE `emotion_data` DISABLE KEYS */;
INSERT INTO `emotion_data` VALUES (1,100,0,NULL,30,1,11,0,20,10,89,1),(60,3,0,NULL,2,0,21,0,0,0,160,4),(61,3,0,NULL,2,0,20,0,0,0,160,3),(62,329,0,NULL,9,0,11,0,44,4,164,4),(63,337,0,NULL,14,0,10,0,35,4,164,3),(64,48,0,NULL,3,1,4,0,10,2,165,3),(65,52,0,NULL,3,0,1,0,10,3,165,4),(66,89,0,NULL,0,90,10,0,23,0,173,15),(67,5,0,NULL,40,0,45,0,37,13,173,11),(68,2,0,NULL,80,68,71,0,51,0,173,15),(69,5,0,NULL,44,0,47,0,38,13,173,11),(70,0,0,NULL,12,73,4,0,0,73,174,15),(71,6,0,NULL,38,0,36,0,42,5,174,11),(72,5,0,NULL,36,0,37,0,45,12,175,11),(73,3,0,NULL,70,23,0,0,0,1,175,15),(74,2,0,NULL,5,0,34,0,50,0,178,18),(75,0,0,NULL,0,3,87,0,13,4,178,19),(76,3,0,NULL,4,0,37,0,2,3,179,18),(77,0,0,NULL,0,0,26,0,0,3,179,19),(78,3,0,NULL,0,0,6,0,0,4,180,19),(79,0,0,NULL,5,2,34,0,13,2,180,18),(80,84,0,NULL,0,0,155,0,65,28,181,4),(81,90,0,NULL,0,1,155,0,74,33,181,3),(82,2,0,NULL,1,0,9,0,3,4,189,3),(83,1,0,NULL,2,0,6,0,2,2,189,4),(84,6,0,NULL,0,0,29,0,13,5,191,1),(85,16,0,NULL,2,0,21,0,13,5,191,4),(86,8,0,NULL,0,0,23,0,3,1,192,1),(87,2,0,NULL,4,1,41,0,4,18,192,4),(88,31,0,NULL,1,0,11,0,9,10,195,14),(89,0,0,NULL,0,0,32,0,1,4,195,10),(90,7,0,NULL,9,3,45,0,9,5,201,11),(91,78,0,NULL,0,0,0,0,0,10,201,15),(92,4,0,NULL,2,0,5,0,5,0,202,11),(93,23,0,NULL,0,0,0,0,40,3,202,15),(94,0,0,NULL,0,0,0,0,0,0,203,15),(95,4,0,NULL,4,0,57,0,11,0,203,11),(96,10,0,NULL,4,0,93,0,7,8,208,2),(97,0,0,NULL,0,0,0,0,0,0,215,4),(98,0,0,NULL,0,0,0,0,0,0,215,2),(99,0,0,NULL,0,1,6,0,27,0,216,3),(100,0,0,NULL,1,0,8,0,28,0,216,5),(101,0,0,NULL,20,0,55,0,14,5,229,11),(104,3,0,NULL,23,2,81,0,7,2,237,11);
/*!40000 ALTER TABLE `emotion_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `game`
--

DROP TABLE IF EXISTS `game`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `game` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `description` varchar(300) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `game`
--

LOCK TABLES `game` WRITE;
/*!40000 ALTER TABLE `game` DISABLE KEYS */;
/*!40000 ALTER TABLE `game` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `interest`
--

DROP TABLE IF EXISTS `interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interest` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `type` tinyint NOT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `interest`
--

LOCK TABLES `interest` WRITE;
/*!40000 ALTER TABLE `interest` DISABLE KEYS */;
/*!40000 ALTER TABLE `interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `item`
--

DROP TABLE IF EXISTS `item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `item` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(30) COLLATE utf8mb4_general_ci NOT NULL,
  `type` tinyint NOT NULL,
  `url` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `item`
--

LOCK TABLES `item` WRITE;
/*!40000 ALTER TABLE `item` DISABLE KEYS */;
/*!40000 ALTER TABLE `item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multi_meeting_room`
--

DROP TABLE IF EXISTS `multi_meeting_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `multi_meeting_room` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `session_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` tinyint NOT NULL,
  `title` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=218 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multi_meeting_room`
--

LOCK TABLES `multi_meeting_room` WRITE;
/*!40000 ALTER TABLE `multi_meeting_room` DISABLE KEYS */;
INSERT INTO `multi_meeting_room` VALUES (176,'2023-02-16 20:47:51','ses_ZOaruzalDJ',0,'SSAFY 화이팅!','2023-02-16 20:47:51'),(177,'2023-02-16 20:48:27','ses_ZzUD5wQeqX',0,'Rendez-Boo화이팅!','2023-02-16 20:48:27');
/*!40000 ALTER TABLE `multi_meeting_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `multi_meeting_room_user`
--

DROP TABLE IF EXISTS `multi_meeting_room_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `multi_meeting_room_user` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `status` tinyint NOT NULL,
  `multi_meeting_room_seq` int unsigned DEFAULT NULL,
  `user_seq` int unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `FKbw79xxx21xmmfug2abg44xq6e` (`multi_meeting_room_seq`),
  KEY `FK1csncn2koec6f659qksganptb` (`user_seq`),
  CONSTRAINT `FK1csncn2koec6f659qksganptb` FOREIGN KEY (`user_seq`) REFERENCES `user` (`seq`),
  CONSTRAINT `FKbw79xxx21xmmfug2abg44xq6e` FOREIGN KEY (`multi_meeting_room_seq`) REFERENCES `multi_meeting_room` (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=843 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `multi_meeting_room_user`
--

LOCK TABLES `multi_meeting_room_user` WRITE;
/*!40000 ALTER TABLE `multi_meeting_room_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `multi_meeting_room_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notice`
--

DROP TABLE IF EXISTS `notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notice` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `hit` int NOT NULL DEFAULT '0',
  `title` varchar(40) COLLATE utf8mb4_general_ci NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notice`
--

LOCK TABLES `notice` WRITE;
/*!40000 ALTER TABLE `notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `oneto_one_meeting_room`
--

DROP TABLE IF EXISTS `oneto_one_meeting_room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `oneto_one_meeting_room` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `man_seq` int unsigned DEFAULT NULL,
  `session_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `status` tinyint NOT NULL,
  `updated_at` datetime DEFAULT NULL,
  `woman_seq` int unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=241 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `oneto_one_meeting_room`
--

LOCK TABLES `oneto_one_meeting_room` WRITE;
/*!40000 ALTER TABLE `oneto_one_meeting_room` DISABLE KEYS */;
INSERT INTO `oneto_one_meeting_room` VALUES (88,'2023-02-10 14:48:18',4,'OPENVIDU_OTO_230210054818',1,'2023-02-10 15:03:51',2),(89,'2023-02-10 15:38:50',4,'OPENVIDU_OTO_230210063849',5,'2023-02-10 15:41:07',1),(90,'2023-02-10 15:41:13',4,'OPENVIDU_OTO_230210064112',5,'2023-02-10 15:42:16',1),(91,'2023-02-10 15:42:22',4,'OPENVIDU_OTO_230210064222',5,'2023-02-10 15:44:57',1),(92,'2023-02-10 15:45:04',4,'OPENVIDU_OTO_230210064503',5,'2023-02-10 15:48:24',1),(93,'2023-02-10 15:48:36',4,'OPENVIDU_OTO_230210064836',5,'2023-02-10 15:49:12',1),(94,'2023-02-10 15:49:16',4,'OPENVIDU_OTO_230210064915',5,'2023-02-10 15:50:46',1),(95,'2023-02-10 15:58:27',4,'OPENVIDU_OTO_230210065826',5,'2023-02-10 16:01:21',1),(96,'2023-02-10 16:11:33',4,'OPENVIDU_OTO_230210071132',5,'2023-02-10 16:12:11',1),(97,'2023-02-10 16:18:12',4,'OPENVIDU_OTO_230210071811',5,'2023-02-10 16:18:48',1),(98,'2023-02-10 16:20:14',4,'OPENVIDU_OTO_230210072013',5,'2023-02-10 16:20:37',1),(99,'2023-02-10 16:22:06',4,'OPENVIDU_OTO_230210072206',5,'2023-02-10 16:22:40',1),(100,'2023-02-10 16:32:42',4,'OPENVIDU_OTO_230210073241',5,'2023-02-10 16:34:32',1),(101,'2023-02-10 17:03:01',NULL,'OPENVIDU_OTO_230210080300',5,'2023-02-10 17:03:56',1),(102,'2023-02-10 17:03:31',4,'OPENVIDU_OTO_230210080331',5,'2023-02-10 17:05:32',1),(103,'2023-02-10 17:11:16',6,'OPENVIDU_OTO_230210081115',5,'2023-02-10 17:19:22',1),(104,'2023-02-10 17:19:27',6,'OPENVIDU_OTO_230210081926',5,'2023-02-10 17:20:45',1),(105,'2023-02-10 17:22:20',6,'OPENVIDU_OTO_230210082220',5,'2023-02-10 17:30:41',1),(106,'2023-02-10 17:30:50',6,'OPENVIDU_OTO_230210083049',5,'2023-02-10 17:40:30',1),(107,'2023-02-10 17:40:38',6,'OPENVIDU_OTO_230210084038',5,'2023-02-10 17:41:16',1),(108,'2023-02-10 17:45:14',6,'OPENVIDU_OTO_230210084513',5,'2023-02-10 17:45:52',1),(109,'2023-02-10 21:34:57',6,'OPENVIDU_OTO_230210123456',5,'2023-02-10 21:36:27',1),(110,'2023-02-10 21:36:32',6,'OPENVIDU_OTO_230210123632',5,'2023-02-10 21:37:53',1),(111,'2023-02-10 21:47:18',6,'OPENVIDU_OTO_230210124718',5,'2023-02-10 21:48:35',1),(112,'2023-02-10 21:48:58',6,'OPENVIDU_OTO_230210124858',5,'2023-02-10 21:50:18',1),(113,'2023-02-10 21:50:22',6,'OPENVIDU_OTO_230210125022',5,'2023-02-10 21:55:15',1),(114,'2023-02-10 21:51:01',6,'OPENVIDU_OTO_230210125101',5,'2023-02-10 21:55:15',1),(115,'2023-02-10 21:57:41',6,'OPENVIDU_OTO_230210125740',5,'2023-02-10 21:58:48',1),(116,'2023-02-10 21:58:59',6,'OPENVIDU_OTO_230210125859',5,'2023-02-10 21:59:42',1),(117,'2023-02-11 11:47:42',5,'OPENVIDU_OTO_230211024742',5,'2023-02-11 11:49:02',1),(118,'2023-02-11 11:49:10',5,'OPENVIDU_OTO_230211024910',5,'2023-02-11 11:49:44',1),(119,'2023-02-12 17:47:45',4,'OPENVIDU_OTO_230212084745',5,'2023-02-12 17:49:33',2),(120,'2023-02-12 17:52:21',4,'OPENVIDU_OTO_230212085221',5,'2023-02-12 17:53:25',2),(121,'2023-02-12 17:54:22',4,'OPENVIDU_OTO_230212085421',5,'2023-02-12 18:08:59',2),(122,'2023-02-12 17:55:25',4,'OPENVIDU_OTO_230212085525',5,'2023-02-12 18:33:10',2),(123,'2023-02-12 18:09:03',4,'OPENVIDU_OTO_230212090902',5,'2023-02-12 18:33:10',2),(124,'2023-02-12 18:13:40',4,'OPENVIDU_OTO_230212091340',5,'2023-02-12 18:33:10',2),(125,'2023-02-12 18:29:41',4,'OPENVIDU_OTO_230212092941',5,'2023-02-12 18:33:10',2),(126,'2023-02-12 18:40:59',4,'OPENVIDU_OTO_230212094058',5,'2023-02-12 18:41:58',2),(127,'2023-02-12 18:43:18',4,'OPENVIDU_OTO_230212094317',1,'2023-02-12 18:55:43',2),(128,'2023-02-12 18:52:44',4,'OPENVIDU_OTO_230212095244',5,'2023-02-12 18:57:02',2),(129,'2023-02-12 18:53:51',4,'OPENVIDU_OTO_230212095350',5,'2023-02-12 18:56:19',2),(130,'2023-02-12 18:55:28',4,'OPENVIDU_OTO_230212095527',5,'2023-02-12 18:57:02',2),(131,'2023-02-12 18:57:32',4,'OPENVIDU_OTO_230212095732',1,'2023-02-12 19:00:44',2),(132,'2023-02-12 19:01:02',4,'OPENVIDU_OTO_230212100101',5,'2023-02-12 19:07:16',NULL),(133,'2023-02-12 19:07:27',4,'OPENVIDU_OTO_230212100726',1,'2023-02-13 01:05:15',2),(134,'2023-02-13 01:05:08',4,'OPENVIDU_OTO_230212040507',5,'2023-02-13 01:06:09',2),(135,'2023-02-13 01:10:54',4,'OPENVIDU_OTO_230212041053',5,'2023-02-13 01:11:59',2),(136,'2023-02-13 01:13:12',4,'OPENVIDU_OTO_230212041311',6,'2023-02-13 01:16:47',2),(137,'2023-02-13 09:13:22',4,'OPENVIDU_OTO_230213121322',5,'2023-02-13 09:15:26',2),(138,'2023-02-13 09:16:28',4,'OPENVIDU_OTO_230213121628',5,'2023-02-13 09:17:11',3),(139,'2023-02-13 09:18:08',4,'OPENVIDU_OTO_230213121807',5,'2023-02-13 09:18:44',3),(140,'2023-02-13 09:23:34',4,'OPENVIDU_OTO_230213122333',5,'2023-02-13 09:41:41',2),(141,'2023-02-13 09:24:00',4,'OPENVIDU_OTO_230213122400',5,'2023-02-13 09:24:38',3),(142,'2023-02-13 09:26:00',4,'OPENVIDU_OTO_230213122559',5,'2023-02-13 09:26:38',3),(143,'2023-02-13 09:38:06',4,'OPENVIDU_OTO_230213123806',5,'2023-02-13 09:40:00',3),(144,'2023-02-13 09:40:43',4,'OPENVIDU_OTO_230213124043',5,'2023-02-13 09:41:27',3),(145,'2023-02-13 09:43:06',4,'OPENVIDU_OTO_230213124305',5,'2023-02-13 09:48:13',2),(146,'2023-02-13 09:46:48',4,'OPENVIDU_OTO_230213124647',5,'2023-02-13 09:47:22',3),(147,'2023-02-13 09:49:13',4,'OPENVIDU_OTO_230213124913',5,'2023-02-13 09:50:13',3),(148,'2023-02-13 09:50:33',4,'OPENVIDU_OTO_230213125032',5,'2023-02-13 09:54:24',2),(149,'2023-02-13 09:51:57',4,'OPENVIDU_OTO_230213125156',5,'2023-02-13 09:52:31',3),(150,'2023-02-13 09:55:40',4,'OPENVIDU_OTO_230213125539',5,'2023-02-13 09:56:27',3),(151,'2023-02-13 10:19:08',4,'OPENVIDU_OTO_230213011907',5,'2023-02-13 10:20:28',2),(152,'2023-02-13 10:19:31',4,'OPENVIDU_OTO_230213011931',5,'2023-02-13 10:20:17',3),(153,'2023-02-13 10:25:51',4,'OPENVIDU_OTO_230213012551',5,'2023-02-13 10:27:51',3),(154,'2023-02-13 10:28:41',4,'OPENVIDU_OTO_230213012841',5,'2023-02-13 10:29:37',2),(155,'2023-02-13 10:30:25',4,'OPENVIDU_OTO_230213013025',5,'2023-02-13 10:31:09',3),(156,'2023-02-13 10:36:19',4,'OPENVIDU_OTO_230213013619',5,'2023-02-13 10:37:11',3),(157,'2023-02-13 10:36:56',4,'OPENVIDU_OTO_230213013655',5,'2023-02-13 10:39:29',1),(158,'2023-02-13 10:40:18',4,'OPENVIDU_OTO_230213014017',5,'2023-02-13 10:41:02',3),(159,'2023-02-13 10:42:40',4,'OPENVIDU_OTO_230213014239',5,'2023-02-13 10:44:33',3),(160,'2023-02-13 10:54:46',4,'OPENVIDU_OTO_230213015445',5,'2023-02-13 10:55:24',3),(161,'2023-02-13 11:06:20',4,'OPENVIDU_OTO_230213020619',5,'2023-02-13 11:12:40',3),(162,'2023-02-13 12:45:21',4,'OPENVIDU_OTO_230213034520',5,'2023-02-13 12:59:29',3),(163,'2023-02-13 12:59:33',4,'OPENVIDU_OTO_230213035933',5,'2023-02-13 13:06:50',3),(164,'2023-02-13 13:07:59',4,'OPENVIDU_OTO_230213040759',5,'2023-02-13 13:13:25',3),(165,'2023-02-13 13:13:40',4,'OPENVIDU_OTO_230213041339',5,'2023-02-13 13:15:40',3),(166,'2023-02-13 13:15:45',4,'OPENVIDU_OTO_230213041544',5,'2023-02-13 13:17:07',3),(167,'2023-02-13 14:18:14',4,'OPENVIDU_OTO_230213051813',5,'2023-02-13 14:19:05',3),(168,'2023-02-13 14:29:50',4,'OPENVIDU_OTO_230213052949',5,'2023-02-13 14:31:30',2),(169,'2023-02-14 02:45:17',NULL,'OPENVIDU_OTO_230213054516',5,'2023-02-14 02:45:58',1),(170,'2023-02-14 04:32:16',NULL,'OPENVIDU_OTO_230213073215',5,'2023-02-14 04:33:17',1),(171,'2023-02-14 10:30:10',4,'OPENVIDU_OTO_230214013010',5,'2023-02-14 10:34:07',3),(172,'2023-02-14 14:46:52',15,'OPENVIDU_OTO_230214054651',5,'2023-02-14 14:47:33',11),(173,'2023-02-14 14:47:36',15,'OPENVIDU_OTO_230214054736',5,'2023-02-14 14:51:05',11),(174,'2023-02-14 14:59:11',15,'OPENVIDU_OTO_230214055911',5,'2023-02-14 15:01:09',11),(175,'2023-02-14 15:04:45',15,'OPENVIDU_OTO_230214060445',5,'2023-02-14 15:06:32',11),(176,'2023-02-14 15:43:45',4,'OPENVIDU_OTO_230214034344',5,'2023-02-14 16:03:54',3),(177,'2023-02-14 16:05:29',4,'OPENVIDU_OTO_230214040528',5,'2023-02-14 16:06:47',3),(178,'2023-02-14 16:23:56',18,'OPENVIDU_OTO_230214042356',5,'2023-02-14 16:29:24',19),(179,'2023-02-14 16:29:29',18,'OPENVIDU_OTO_230214042928',5,'2023-02-14 16:31:22',19),(180,'2023-02-14 16:31:31',18,'OPENVIDU_OTO_230214043131',5,'2023-02-14 16:33:48',19),(181,'2023-02-14 16:32:32',4,'OPENVIDU_OTO_230214043231',5,'2023-02-14 16:53:41',3),(182,'2023-02-14 16:44:09',18,'OPENVIDU_OTO_230214044408',5,'2023-02-14 16:44:24',NULL),(183,'2023-02-14 16:44:17',18,'OPENVIDU_OTO_230214044416',5,'2023-02-14 16:44:24',NULL),(184,'2023-02-14 16:44:36',18,'OPENVIDU_OTO_230214044436',5,'2023-02-14 16:44:42',NULL),(185,'2023-02-14 16:44:44',18,'OPENVIDU_OTO_230214044444',5,'2023-02-14 16:46:06',NULL),(186,'2023-02-14 16:45:09',18,'OPENVIDU_OTO_230214044508',5,'2023-02-14 16:46:06',NULL),(187,'2023-02-14 16:46:03',18,'OPENVIDU_OTO_230214044602',5,'2023-02-14 16:46:06',NULL),(188,'2023-02-14 16:46:08',18,'OPENVIDU_OTO_230214044607',5,'2023-02-14 16:46:23',NULL),(189,'2023-02-14 16:55:54',4,'OPENVIDU_OTO_230214045553',5,'2023-02-14 16:58:00',3),(190,'2023-02-14 17:08:17',4,'OPENVIDU_OTO_230214050816',5,'2023-02-14 17:08:34',1),(191,'2023-02-14 17:08:59',4,'OPENVIDU_OTO_230214050859',5,'2023-02-14 17:11:15',1),(192,'2023-02-14 17:14:20',4,'OPENVIDU_OTO_230214051419',5,'2023-02-14 17:16:09',1),(193,'2023-02-15 09:14:58',10,'OPENVIDU_OTO_230215091457',5,'2023-02-15 09:17:59',14),(194,'2023-02-15 09:17:21',15,'OPENVIDU_OTO_230215091721',5,'2023-02-15 09:21:23',14),(195,'2023-02-15 09:18:16',10,'OPENVIDU_OTO_230215091815',5,'2023-02-15 09:21:23',14),(196,'2023-02-15 09:23:22',NULL,'OPENVIDU_OTO_230215092322',5,'2023-02-15 09:23:34',14),(197,'2023-02-15 09:23:57',NULL,'OPENVIDU_OTO_230215092356',5,'2023-02-15 09:23:58',14),(198,'2023-02-15 09:24:42',NULL,'OPENVIDU_OTO_230215092442',5,'2023-02-15 09:24:47',14),(199,'2023-02-15 09:26:09',NULL,'OPENVIDU_OTO_230215092608',5,'2023-02-15 09:26:15',14),(200,'2023-02-15 10:15:46',10,'OPENVIDU_OTO_230215101545',5,'2023-02-15 10:15:50',NULL),(201,'2023-02-15 15:40:47',15,'OPENVIDU_OTO_230215034046',5,'2023-02-15 15:43:16',11),(202,'2023-02-15 15:52:37',15,'OPENVIDU_OTO_230215035236',5,'2023-02-15 15:54:41',11),(203,'2023-02-15 15:56:38',15,'OPENVIDU_OTO_230215035637',5,'2023-02-15 15:58:42',11),(204,'2023-02-15 16:01:07',NULL,'OPENVIDU_OTO_230215040107',5,'2023-02-15 16:40:51',14),(205,'2023-02-15 19:36:01',4,'OPENVIDU_OTO_230215073600',1,'2023-02-16 14:31:13',14),(206,'2023-02-15 19:44:34',4,'OPENVIDU_OTO_230215074433',1,'2023-02-16 14:31:59',14),(207,'2023-02-16 03:46:56',NULL,'OPENVIDU_OTO_230216034655',5,'2023-02-16 03:47:07',2),(208,'2023-02-16 14:31:15',4,'OPENVIDU_OTO_230216023114',5,'2023-02-16 14:35:31',2),(209,'2023-02-16 14:36:01',NULL,'OPENVIDU_OTO_230216023601',5,'2023-02-16 14:36:26',2),(210,'2023-02-16 14:36:03',NULL,'OPENVIDU_OTO_230216023602',5,'2023-02-16 14:36:29',2),(211,'2023-02-16 14:38:01',NULL,'OPENVIDU_OTO_230216023800',5,'2023-02-16 14:38:28',2),(212,'2023-02-16 14:38:03',NULL,'OPENVIDU_OTO_230216023802',5,'2023-02-16 14:39:00',2),(213,'2023-02-16 14:38:36',NULL,'OPENVIDU_OTO_230216023835',5,'2023-02-16 14:38:58',2),(214,'2023-02-16 14:51:27',4,'OPENVIDU_OTO_230216025127',1,'2023-02-16 15:00:50',2),(215,'2023-02-16 15:00:53',4,'OPENVIDU_OTO_230216030052',5,'2023-02-16 15:07:56',2),(216,'2023-02-16 15:27:08',5,'OPENVIDU_OTO_230216032708',5,'2023-02-16 15:29:21',3),(217,'2023-02-16 17:10:17',24,'OPENVIDU_OTO_230216051017',5,'2023-02-16 17:10:36',NULL),(218,'2023-02-16 17:14:29',4,'OPENVIDU_OTO_230216051428',5,'2023-02-16 17:17:07',NULL),(219,'2023-02-16 17:43:05',6,'OPENVIDU_OTO_230216054304',5,'2023-02-16 17:44:18',NULL),(220,'2023-02-16 19:16:37',4,'OPENVIDU_OTO_230216071636',5,'2023-02-16 19:16:45',NULL),(221,'2023-02-16 19:48:19',15,'OPENVIDU_OTO_230216074818',5,'2023-02-16 19:48:27',NULL),(222,'2023-02-16 21:29:55',15,'OPENVIDU_OTO_230216092955',5,'2023-02-16 21:30:46',NULL),(223,'2023-02-16 21:55:57',15,'OPENVIDU_OTO_230216095556',5,'2023-02-16 21:58:44',3),(224,'2023-02-16 21:57:04',15,'OPENVIDU_OTO_230216095704',1,'2023-02-16 21:58:48',3),(225,'2023-02-16 21:58:21',15,'OPENVIDU_OTO_230216095820',1,'2023-02-16 21:59:16',1),(226,'2023-02-16 21:59:05',15,'OPENVIDU_OTO_230216095905',5,'2023-02-16 21:59:52',1),(227,'2023-02-16 22:00:11',15,'OPENVIDU_OTO_230216100011',5,'2023-02-16 22:01:45',1),(228,'2023-02-17 04:08:34',NULL,'OPENVIDU_OTO_230217040833',5,'2023-02-17 04:08:47',13),(229,'2023-02-17 07:58:01',15,'OPENVIDU_OTO_230217075801',5,'2023-02-17 07:59:48',11),(230,'2023-02-17 08:17:53',15,'OPENVIDU_OTO_230217081752',5,'2023-02-17 08:18:36',11),(231,'2023-02-17 08:19:51',15,'OPENVIDU_OTO_230217081950',5,'2023-02-17 08:22:45',11),(232,'2023-02-17 08:21:44',15,'OPENVIDU_OTO_230217082143',5,'2023-02-17 08:22:29',NULL),(233,'2023-02-17 08:22:49',15,'OPENVIDU_OTO_230217082249',5,'2023-02-17 08:23:47',11),(234,'2023-02-17 08:23:51',15,'OPENVIDU_OTO_230217082350',5,'2023-02-17 08:24:00',NULL),(235,'2023-02-17 08:26:56',15,'OPENVIDU_OTO_230217082655',5,'2023-02-17 08:27:57',11),(236,'2023-02-17 08:28:02',15,'OPENVIDU_OTO_230217082802',5,'2023-02-17 08:28:17',NULL),(237,'2023-02-17 08:29:15',15,'OPENVIDU_OTO_230217082914',5,'2023-02-17 08:31:18',11),(238,'2023-02-17 08:58:28',15,'OPENVIDU_OTO_230217085828',5,'2023-02-17 08:59:20',11),(239,'2023-02-17 09:06:30',NULL,'OPENVIDU_OTO_230217090630',5,'2023-02-17 09:07:40',14),(240,'2023-02-17 09:07:44',10,'OPENVIDU_OTO_230217090744',5,'2023-02-17 09:09:28',14);
/*!40000 ALTER TABLE `oneto_one_meeting_room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `report`
--

DROP TABLE IF EXISTS `report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `report` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `report_detail` text COLLATE utf8mb4_general_ci,
  `report_type` tinyint DEFAULT NULL,
  `reporter_seq` int unsigned DEFAULT NULL,
  `target_seq` int unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `FKl2x2o0xfl02u0xu7xsd2srkk2` (`reporter_seq`),
  KEY `FKc6kgqbs593q18prprf7boeqf5` (`target_seq`),
  CONSTRAINT `FKc6kgqbs593q18prprf7boeqf5` FOREIGN KEY (`target_seq`) REFERENCES `user` (`seq`),
  CONSTRAINT `FKl2x2o0xfl02u0xu7xsd2srkk2` FOREIGN KEY (`reporter_seq`) REFERENCES `user` (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `report`
--

LOCK TABLES `report` WRITE;
/*!40000 ALTER TABLE `report` DISABLE KEYS */;
/*!40000 ALTER TABLE `report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `birthday` datetime NOT NULL,
  `city` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `created_at` datetime DEFAULT NULL,
  `email` varchar(40) COLLATE utf8mb4_general_ci NOT NULL,
  `gender` bit(1) NOT NULL,
  `is_admin` bit(1) NOT NULL,
  `is_valid` bit(1) NOT NULL,
  `mbti` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `password` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `phone_number` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `point` bigint DEFAULT NULL,
  `profile_image_path` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `badge_seq` int unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `FKplsb90p43j9eic5jaily57xt2` (`badge_seq`),
  CONSTRAINT `FKplsb90p43j9eic5jaily57xt2` FOREIGN KEY (`badge_seq`) REFERENCES `badge` (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'1997-10-19 09:00:00','서울','2023-02-09 17:49:21','user1@email.com',_binary '\0',_binary '\0',_binary '','ENTJ','LHJ','$2a$10$z2b.Nm6qB5X.Yl3XLVPCye5V1NhVv1YP5URzGbRM2SfRn5CujY2/q','01012345678',200,'images/167651782276220171114_171716_IMG_4617.JPG','2023-02-16 12:23:43',NULL),(2,'1997-10-19 09:00:00','서울','2023-02-09 17:50:55','user2@email.com',_binary '\0',_binary '\0',_binary '','ENTJ','woman2','$2a$10$2Bem556gf5YnRtAMDezreepFsCQOVjmYD9oyNEFm8AJZ6dOifU3uS','01022222222',0,'','2023-02-09 17:50:55',NULL),(3,'1997-10-19 09:00:00','서울','2023-02-09 17:51:08','user3@email.com',_binary '\0',_binary '\0',_binary '','ENTJ','woman3','$2a$10$O6OnbxmA6N6aS5pE3PJ6euhPrI64MYjTOzXs37aR3I1Ylu2iDZZCC','01033333333',0,'','2023-02-09 17:51:08',NULL),(4,'1997-10-19 09:00:00','서울','2023-02-09 17:51:21','user4@email.com',_binary '',_binary '\0',_binary '','ENTJ','man4','$2a$10$OJrB.IMqcJu6uH6HSUgBxunigvEhGodDjap5yOqTWIrxL7JnhJkWK','01044444444',300,'images/1676556338165Yes or no-amico.png','2023-02-16 23:05:38',NULL),(5,'1997-10-19 09:00:00','서울','2023-02-09 17:51:34','user5@email.com',_binary '',_binary '\0',_binary '','ENTJ','김명준-그림자분신','$2a$10$XsP9nwSqU9xB5I32HXoJmO8xWXiJD6cD87zDXHOhvc/j3S4zgRPS6','01055555555',0,'','2023-02-09 17:51:34',NULL),(6,'1997-10-19 09:00:00','서울','2023-02-09 17:51:45','user6@email.com',_binary '',_binary '\0',_binary '','ENTJ','man6','$2a$10$DY1VTOjy6XFI5RHZEsDe7ueY7VHmM8NjrEI6RSPxgXVeWy7Q3QnTy','01066666666',0,'','2023-02-09 17:51:45',NULL),(7,'1997-10-19 09:00:00','서울','2023-02-09 17:52:37','admin@ssafy.com',_binary '\0',_binary '',_binary '','ENTJ','admin','$2a$10$njH7GDSg75qUzrkh0KOOUeZZh3ClIgM1DdH/NGvmhZK4CWuwqo5Im','01098765432',0,'','2023-02-09 17:52:37',NULL),(8,'1997-10-19 09:00:00','Seoul','2023-02-12 00:01:18','test@gmail.com',_binary '',_binary '',_binary '','INTP','이재원','$2a$10$UGLCGcGCoWUUoJe.RHvsn.HTHStpZBrbJqdtn9tbc3GirlZcmQE.u','01011111119',0,'','2023-02-12 00:01:18',NULL),(10,'1995-08-12 09:00:00','','2023-02-14 13:33:58','audwnswmf@naver.com',_binary '',_binary '\0',_binary '','ISFP','김명준','$2a$10$VnTlDGKks75MApBfrsxKiurboMZzpQU4wTTdUg5jInHwOUPBYBCV.','01133055629',200,'images/1676383317085김명준_사인.png','2023-02-15 09:21:22',NULL),(11,'1997-10-19 09:00:00','','2023-02-14 13:35:42','ljaewon97@naver.com',_binary '\0',_binary '\0',_binary '','INTP','이재원','$2a$10$thdm4bIwG8bDSWgmAhbSzeMgJv9C9cLqfs1rt6QWfIKRvVXPNSUtK','01099065911',700,'images/1676587367011emo-happy.png','2023-02-17 08:31:17',NULL),(12,'1990-02-07 09:00:00','','2023-02-14 13:37:41','ssafya107@kakao.com',_binary '',_binary '\0',_binary '','INTP','이재원','$2a$10$3/0ZqbSC16PTYSD31ZAUbeaQfQXrCJjaiLuKlhVH44QDfmBwgFKKq','01099065910',0,'','2023-02-14 13:37:41',NULL),(13,'1995-02-01 09:00:00','','2023-02-14 13:43:05','wjdgnsxhsl@naver.com',_binary '\0',_binary '\0',_binary '','ISFP','정훈','$2a$10$SQGRXDnK1LWrpYU0AJVPmegSSehIHdACDVKiz7wKm93HlnkqbTP.G','01095021904',0,'images/167658007511320171114_171716_IMG_4617.JPG','2023-02-17 05:41:15',NULL),(14,'1995-12-01 09:00:00','','2023-02-14 13:44:03','wx776654@naver.com',_binary '\0',_binary '\0',_binary '','ENTJ','이홍주','$2a$10$WXxXEIwnUudyVo1Kr6sCmebiyy/b0hWAMMZqZ0yciFOUFBFaqLLWa','01072613467',200,'','2023-02-15 09:21:22',NULL),(15,'1996-12-18 09:00:00','','2023-02-14 13:45:45','zephyr728@naver.com',_binary '',_binary '\0',_binary '','INTP','유선준','$2a$10$hyP8gLOer29m3jiEdLoytuVwiLxQIaSMtCLMH8Y29WxTGk/S4fVui','01033983224',700,'images/1676552142205프로필 사진.png','2023-02-17 08:31:16',NULL),(17,'1980-02-06 09:00:00','','2023-02-14 14:47:14','audwnswmf@hanmail.net',_binary '\0',_binary '\0',_binary '','ISFP','김명준','$2a$10$Xr2YTyXlqD1Xjz5YIFnHvuxkRPp8a2GfCUY4gUR.RfNhUpOv8Z68e','01233055629',0,'','2023-02-14 14:47:14',NULL),(18,'1980-02-06 00:00:00','','2023-02-14 16:20:52','mstkang@gmail.com',_binary '',_binary '\0',_binary '','ESFP','강컨','$2a$10$8o51/ZbP05qb58F835ZTVuVFK8fKVrVKHY2JUdt3giGXPIY5BKfv2','01090884416',300,'','2023-02-14 16:31:21',NULL),(19,'1998-03-04 00:00:00','','2023-02-14 16:27:24','thintheul34@naver.com',_binary '\0',_binary '\0',_binary '','INTJ','신슬기','$2a$10$CX/UJYdFcUZdqjx7AuP3zuQf9myYcWf5TAEWEuGNNO32HbN9ExmHG','01099653849',200,'','2023-02-14 16:29:24',NULL),(24,'1997-06-11 00:00:00','','2023-02-16 17:10:09','xkhg0611x@naver.com',_binary '',_binary '\0',_binary '','ESFJ','하상재','$2a$10$VXb3jHFcaZ8PrQNUd9VivuIxjXQZ4m8g7WjDoG0VJE5xkDKfXCWn6','01048015358',0,'','2023-02-16 17:10:09',NULL),(25,'2003-07-01 00:00:00','','2023-02-17 08:57:58','yena0426@kakao.com',_binary '\0',_binary '\0',_binary '','ENFP','안예나','$2a$10$md6NtFss/.cY1xKA4rRCBOJguWRSpNRllXEYX5/yfQ3kzjTJEYFqO','01089040426',0,'','2023-02-17 08:57:58',NULL);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_badge`
--

DROP TABLE IF EXISTS `user_badge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_badge` (
  `badge_seq` int unsigned NOT NULL,
  `user_seq` int unsigned NOT NULL,
  PRIMARY KEY (`badge_seq`,`user_seq`),
  KEY `FKp7xoklvn71f0r3l4w4nsmlvd2` (`user_seq`),
  CONSTRAINT `FKp7xoklvn71f0r3l4w4nsmlvd2` FOREIGN KEY (`user_seq`) REFERENCES `user` (`seq`),
  CONSTRAINT `FKrac1x18atp0augipdrle87apr` FOREIGN KEY (`badge_seq`) REFERENCES `badge` (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_badge`
--

LOCK TABLES `user_badge` WRITE;
/*!40000 ALTER TABLE `user_badge` DISABLE KEYS */;
INSERT INTO `user_badge` VALUES (1,1),(2,1),(3,1),(5,1),(6,1),(7,1),(8,1),(9,1),(10,1),(16,1),(1,4),(4,4),(16,4),(1,10),(2,10),(16,10),(1,11),(2,11),(16,11),(17,11),(19,11),(1,14),(7,14),(1,15),(2,15),(7,15),(8,15),(9,15),(16,15),(19,15),(20,15),(1,19),(16,19);
/*!40000 ALTER TABLE `user_badge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_blocked`
--

DROP TABLE IF EXISTS `user_blocked`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_blocked` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `target_seq` int unsigned DEFAULT NULL,
  `user_seq` int unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `FKgs18rtti2i12ot8ply64c7ohv` (`target_seq`),
  KEY `FKh08juw8e07j4bvrv6a2tjvlih` (`user_seq`),
  CONSTRAINT `FKgs18rtti2i12ot8ply64c7ohv` FOREIGN KEY (`target_seq`) REFERENCES `user` (`seq`),
  CONSTRAINT `FKh08juw8e07j4bvrv6a2tjvlih` FOREIGN KEY (`user_seq`) REFERENCES `user` (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_blocked`
--

LOCK TABLES `user_blocked` WRITE;
/*!40000 ALTER TABLE `user_blocked` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_blocked` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_friend`
--

DROP TABLE IF EXISTS `user_friend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_friend` (
  `seq` int unsigned NOT NULL AUTO_INCREMENT,
  `created_at` datetime DEFAULT NULL,
  `user_female_seq` int unsigned DEFAULT NULL,
  `user_male_seq` int unsigned DEFAULT NULL,
  PRIMARY KEY (`seq`),
  KEY `FKla08ro4lb5v7mnn7n1yw7jc5e` (`user_female_seq`),
  KEY `FKh07pu01evr2u496fk97rqn1bj` (`user_male_seq`),
  CONSTRAINT `FKh07pu01evr2u496fk97rqn1bj` FOREIGN KEY (`user_male_seq`) REFERENCES `user` (`seq`),
  CONSTRAINT `FKla08ro4lb5v7mnn7n1yw7jc5e` FOREIGN KEY (`user_female_seq`) REFERENCES `user` (`seq`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_friend`
--

LOCK TABLES `user_friend` WRITE;
/*!40000 ALTER TABLE `user_friend` DISABLE KEYS */;
INSERT INTO `user_friend` VALUES (1,'2023-02-13 15:14:50',3,4),(2,'2023-02-13 15:15:00',2,4),(3,'2023-02-14 15:01:08',11,15),(4,'2023-02-14 16:31:22',19,18),(5,'2023-02-14 17:16:09',1,4),(6,'2023-02-15 09:21:22',14,10),(8,'2023-02-17 02:11:48',1,6),(9,'2023-02-17 03:04:06',2,6),(10,'2023-02-17 08:54:35',3,10);
/*!40000 ALTER TABLE `user_friend` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_interest`
--

DROP TABLE IF EXISTS `user_interest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_interest` (
  `interest_seq` int unsigned NOT NULL,
  `user_seq` int unsigned NOT NULL,
  PRIMARY KEY (`interest_seq`,`user_seq`),
  KEY `FK9ry12q7d032bpt5dpq7jgtkoh` (`user_seq`),
  CONSTRAINT `FK8bkdvsxu32diislo8ebrskqek` FOREIGN KEY (`interest_seq`) REFERENCES `interest` (`seq`),
  CONSTRAINT `FK9ry12q7d032bpt5dpq7jgtkoh` FOREIGN KEY (`user_seq`) REFERENCES `user` (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_interest`
--

LOCK TABLES `user_interest` WRITE;
/*!40000 ALTER TABLE `user_interest` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_interest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_item`
--

DROP TABLE IF EXISTS `user_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_item` (
  `item_seq` int unsigned NOT NULL,
  `user_seq` int unsigned NOT NULL,
  PRIMARY KEY (`item_seq`,`user_seq`),
  KEY `FKlftjmdhiqt26dh112a1r5xcqs` (`user_seq`),
  CONSTRAINT `FKb1lriv2jjkjww4l8rx52v89x9` FOREIGN KEY (`item_seq`) REFERENCES `item` (`seq`),
  CONSTRAINT `FKlftjmdhiqt26dh112a1r5xcqs` FOREIGN KEY (`user_seq`) REFERENCES `user` (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_item`
--

LOCK TABLES `user_item` WRITE;
/*!40000 ALTER TABLE `user_item` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_item` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-02-17  9:37:30
