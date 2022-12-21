-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: k7a205.p.ssafy.io    Database: ssafy_web_db
-- ------------------------------------------------------
-- Server version	8.0.31-0ubuntu0.20.04.1

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
-- Table structure for table `challenge`
--

DROP TABLE IF EXISTS `challenge`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `challenge` (
  `challenge_id` bigint NOT NULL AUTO_INCREMENT,
  `challenge_description` varchar(300) COLLATE utf8mb4_general_ci NOT NULL,
  `challenge_end_date` date NOT NULL,
  `challenge_start_date` date NOT NULL,
  `challenge_title` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `challenge_reward_type` int NOT NULL,
  `challenge_leader_id` bigint NOT NULL,
  `challenge_leader_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `challenge_participant_point` int DEFAULT NULL,
  `challenge_reward_point` int DEFAULT NULL,
  `challenge_active` int NOT NULL,
  PRIMARY KEY (`challenge_id`)
) ENGINE=InnoDB AUTO_INCREMENT=88 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenge`
--

LOCK TABLES `challenge` WRITE;
/*!40000 ALTER TABLE `challenge` DISABLE KEYS */;
INSERT INTO `challenge` VALUES (71,'꾸준히 물을 마시고 건강을 챙기자','2022-12-12','2022-11-10','매일 물마시기',1,2,'이승훈',1000,3000,1),(72,'꾸준히 물을 마시고 건강을 챙기자','2022-11-16','2022-11-01','매일 물마시기',1,2,'이승훈',100,500,2),(73,'꾸준히 커밋해서 기록 잘 남기자','2022-11-03','2022-10-02','1일 1커밋하기',2,2,'이승훈',0,0,2),(74,'꾸준히 커밋해서 기록 잘 남기자','2022-12-03','2022-11-02','1일 1커밋하기',2,2,'이승훈',0,0,1),(84,'Q','2022-11-26','2022-11-23','Q',2,3,'전민재',0,0,0),(85,'꾸준히 커밋해서 기록 잘 남기자','2022-11-16','2022-10-25','1일 1커밋하기',2,2,'이승훈',0,0,2),(86,'꾸준히 커밋해서 기록 잘 남기자','2022-11-16','2022-10-25','1일 1커밋하기',2,2,'이승훈',0,0,2),(87,'꾸준히 커밋해서 기록 잘 남기자','2022-11-16','2022-10-25','1일 1커밋하기',2,2,'이승훈',0,0,2);
/*!40000 ALTER TABLE `challenge` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `challenge_auth`
--

DROP TABLE IF EXISTS `challenge_auth`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `challenge_auth` (
  `challenge_auth_id` bigint NOT NULL AUTO_INCREMENT,
  `auth_user_id` bigint DEFAULT NULL,
  `challenge_date` date DEFAULT NULL,
  `challenge_path` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `is_confirm` int DEFAULT NULL,
  `vote_cnt` int DEFAULT NULL,
  `vote_user_id` bigint DEFAULT NULL,
  `challenge_info_id` bigint DEFAULT NULL,
  `challenger_cnt` int DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `challenges_id` bigint DEFAULT NULL,
  PRIMARY KEY (`challenge_auth_id`),
  KEY `FK8bmdk92cuws9hp3pw99ln2jux` (`challenge_info_id`),
  CONSTRAINT `FK8bmdk92cuws9hp3pw99ln2jux` FOREIGN KEY (`challenge_info_id`) REFERENCES `challenge_info` (`challenge_info_id`)
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenge_auth`
--

LOCK TABLES `challenge_auth` WRITE;
/*!40000 ALTER TABLE `challenge_auth` DISABLE KEYS */;
INSERT INTO `challenge_auth` VALUES (7,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',1,3,NULL,66,4,2,71),(8,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',1,3,NULL,66,4,2,71),(9,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',0,1,NULL,66,4,2,71),(10,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',1,3,NULL,66,4,2,71),(11,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',1,6,NULL,67,6,2,72),(12,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',1,5,NULL,67,6,2,72),(13,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',0,1,NULL,67,6,2,72),(14,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',1,4,NULL,67,6,2,72),(15,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',1,4,NULL,67,6,2,72),(16,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',1,4,NULL,67,6,2,72),(20,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',1,3,NULL,67,6,2,72),(21,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',0,0,NULL,69,4,2,74),(22,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',0,0,NULL,69,4,2,74),(23,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',0,0,NULL,69,4,2,74),(24,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',0,0,NULL,69,4,2,74),(25,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',0,0,NULL,69,4,2,74),(26,NULL,'2021-10-28','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG',0,0,NULL,69,4,2,74),(27,NULL,'2022-11-21','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121010941201.JPEG',0,0,NULL,66,4,2,71),(28,NULL,'2022-11-21','https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121012407405.JPEG',0,0,NULL,66,4,2,71);
/*!40000 ALTER TABLE `challenge_auth` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `challenge_info`
--

DROP TABLE IF EXISTS `challenge_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `challenge_info` (
  `challenge_info_id` bigint NOT NULL AUTO_INCREMENT,
  `success_cnt` int DEFAULT '0',
  `challenge_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `winner_name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT '',
  `my_rank` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`challenge_info_id`),
  KEY `FKsqriyq69qfhu5p0u1rb4atsvf` (`challenge_id`),
  KEY `FKmsuo7yjtlyv8m8cvk1hfndbuj` (`user_id`),
  CONSTRAINT `FKmsuo7yjtlyv8m8cvk1hfndbuj` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKsqriyq69qfhu5p0u1rb4atsvf` FOREIGN KEY (`challenge_id`) REFERENCES `challenge` (`challenge_id`)
) ENGINE=InnoDB AUTO_INCREMENT=104 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenge_info`
--

LOCK TABLES `challenge_info` WRITE;
/*!40000 ALTER TABLE `challenge_info` DISABLE KEYS */;
INSERT INTO `challenge_info` VALUES (66,3,71,2,NULL,0),(67,6,72,2,NULL,0),(68,0,73,2,NULL,0),(69,0,74,2,NULL,0),(71,0,71,3,NULL,0),(72,0,71,4,NULL,0),(73,0,71,14,NULL,0),(74,0,72,14,NULL,0),(75,0,72,4,NULL,0),(76,1,72,3,NULL,0),(77,0,73,3,NULL,0),(78,0,73,4,NULL,0),(79,0,73,14,NULL,0),(80,0,74,14,NULL,0),(81,0,74,4,NULL,0),(82,0,74,3,NULL,0),(85,0,72,16,NULL,0),(86,0,72,17,NULL,0),(87,0,73,17,NULL,0),(88,0,73,16,NULL,0),(97,0,84,3,NULL,0),(98,0,85,2,NULL,0),(99,0,86,2,NULL,0),(100,0,87,2,NULL,0),(101,0,85,3,NULL,0),(102,0,86,3,NULL,0),(103,0,87,3,NULL,0);
/*!40000 ALTER TABLE `challenge_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `challenge_invite`
--

DROP TABLE IF EXISTS `challenge_invite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `challenge_invite` (
  `challenge_invite_id` bigint NOT NULL AUTO_INCREMENT,
  `challenge_id` bigint DEFAULT NULL,
  `user_id` bigint DEFAULT NULL,
  `invite_status` int DEFAULT NULL,
  PRIMARY KEY (`challenge_invite_id`),
  KEY `FK5yqhwfr045vuis5myfce37y72` (`challenge_id`),
  KEY `FKhdpfjnyjd5g2aii3b9mee9cy0` (`user_id`),
  CONSTRAINT `FK5yqhwfr045vuis5myfce37y72` FOREIGN KEY (`challenge_id`) REFERENCES `challenge` (`challenge_id`),
  CONSTRAINT `FKhdpfjnyjd5g2aii3b9mee9cy0` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenge_invite`
--

LOCK TABLES `challenge_invite` WRITE;
/*!40000 ALTER TABLE `challenge_invite` DISABLE KEYS */;
INSERT INTO `challenge_invite` VALUES (40,84,4,1);
/*!40000 ALTER TABLE `challenge_invite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `challenge_reward`
--

DROP TABLE IF EXISTS `challenge_reward`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `challenge_reward` (
  `challenge_reward_id` bigint NOT NULL AUTO_INCREMENT,
  `challenge_id` bigint DEFAULT NULL,
  `gifticon_id` bigint DEFAULT NULL,
  PRIMARY KEY (`challenge_reward_id`),
  KEY `FKk832q33f34t35qcyo3h72n2tj` (`challenge_id`),
  KEY `FKnyoxw72lf99ycx2g368mayecp` (`gifticon_id`),
  CONSTRAINT `FKk832q33f34t35qcyo3h72n2tj` FOREIGN KEY (`challenge_id`) REFERENCES `challenge` (`challenge_id`),
  CONSTRAINT `FKnyoxw72lf99ycx2g368mayecp` FOREIGN KEY (`gifticon_id`) REFERENCES `gifticon` (`gifticon_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `challenge_reward`
--

LOCK TABLES `challenge_reward` WRITE;
/*!40000 ALTER TABLE `challenge_reward` DISABLE KEYS */;
INSERT INTO `challenge_reward` VALUES (35,84,35),(36,84,35),(37,84,37),(38,84,38),(39,84,79),(40,84,54),(41,84,54),(42,84,36),(43,84,39),(44,84,86),(45,84,85),(46,84,84),(47,84,82);
/*!40000 ALTER TABLE `challenge_reward` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_info`
--

DROP TABLE IF EXISTS `event_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_info` (
  `event_id` bigint NOT NULL,
  `event_end_date` date NOT NULL,
  `event_name` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `person_limit` int NOT NULL,
  `event_start_date` date NOT NULL,
  `winner_cnt` int NOT NULL,
  `event_type_id` bigint DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `FKlbgypqoyjtv4g0h639wh1k86d` (`event_type_id`),
  CONSTRAINT `FKlbgypqoyjtv4g0h639wh1k86d` FOREIGN KEY (`event_type_id`) REFERENCES `event_type` (`event_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_info`
--

LOCK TABLES `event_info` WRITE;
/*!40000 ALTER TABLE `event_info` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `event_type`
--

DROP TABLE IF EXISTS `event_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event_type` (
  `event_type_id` bigint NOT NULL,
  `event_type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`event_type_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event_type`
--

LOCK TABLES `event_type` WRITE;
/*!40000 ALTER TABLE `event_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `event_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `friends_list`
--

DROP TABLE IF EXISTS `friends_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `friends_list` (
  `friends_list_id` bigint NOT NULL AUTO_INCREMENT,
  `follower_id` bigint NOT NULL,
  `following_id` bigint NOT NULL,
  `is_friend` bit(1) NOT NULL,
  PRIMARY KEY (`friends_list_id`)
) ENGINE=InnoDB AUTO_INCREMENT=78 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `friends_list`
--

LOCK TABLES `friends_list` WRITE;
/*!40000 ALTER TABLE `friends_list` DISABLE KEYS */;
INSERT INTO `friends_list` VALUES (32,3,4,_binary ''),(37,4,3,_binary ''),(42,2,4,_binary ''),(43,4,2,_binary ''),(44,2,3,_binary ''),(58,3,14,_binary ''),(59,14,3,_binary ''),(68,14,2,_binary ''),(69,2,14,_binary ''),(70,14,4,_binary ''),(71,2,17,_binary ''),(72,17,2,_binary ''),(73,4,14,_binary ''),(74,3,2,_binary ''),(76,2,16,_binary ''),(77,16,2,_binary '');
/*!40000 ALTER TABLE `friends_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `gifticon`
--

DROP TABLE IF EXISTS `gifticon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `gifticon` (
  `gifticon_id` bigint NOT NULL AUTO_INCREMENT,
  `gifticon_path` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `gifticon_period` date NOT NULL,
  `gifticon_store` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `gifticon_used` bit(1) NOT NULL,
  `user_id` bigint DEFAULT NULL,
  `gifticon_code` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`gifticon_id`),
  KEY `FKp5c6mrbo13y8s3ug01s52br2r` (`user_id`),
  CONSTRAINT `FKp5c6mrbo13y8s3ug01s52br2r` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `gifticon`
--

LOCK TABLES `gifticon` WRITE;
/*!40000 ALTER TABLE `gifticon` DISABLE KEYS */;
INSERT INTO `gifticon` VALUES (32,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/KeWPDRPJLXnXJWJPfzrNgiiTRo-zQ81NNRdUyw6qCj1y6wAAAYSEgIvI20221117080449208.JPEG','2022-09-15','GS25 1',_binary '',14,'9894719165995578'),(34,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/psdzU7q1fNSISjgKixgQ33rvO-0L_3iISniiwqkxCinI2AAAAYR_DvHf20221117081818187.JPEG','2022-10-30','네이버페이포인트',_binary '\0',3,'900524513757'),(35,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/psdzU7q1fNSISjgKixgQ33rvO-0L_3iISniiwqkxCinI2AAAAYR_DvHf20221117081830507.JPEG','2022-10-30','네이버페이포인트',_binary '\0',3,'900050249017'),(36,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/psdzU7q1fNSISjgKixgQ33rvO-0L_3iISniiwqkxCinI2AAAAYR_DvHf20221117081843433.JPEG','2022-10-28','이마트',_binary '\0',3,'708305361007'),(37,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/psdzU7q1fNSISjgKixgQ33rvO-0L_3iISniiwqkxCinI2AAAAYR_DvHf20221117081853762.JPEG','2022-10-28','GS25',_binary '\0',3,'9893581715587828'),(38,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/psdzU7q1fNSISjgKixgQ33rvO-0L_3iISniiwqkxCinI2AAAAYR_DvHf20221117081908759.JPEG','2022-09-23','CU',_binary '\0',3,'9070374269227382'),(39,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/psdzU7q1fNSISjgKixgQ33rvO-0L_3iISniiwqkxCinI2AAAAYR_DvHf20221117081921661.JPEG','2022-09-23','CU',_binary '\0',3,'9071776226955927'),(40,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/KeWPDRPJLXnXJWJPfzrNgiiTRo-zQ81NNRdUyw6qCj1y6wAAAYSEgIvI20221117082225888.JPEG','2023-08-18','맥도날드',_binary '',14,'521735681127'),(42,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/KeWPDRPJLXnXJWJPfzrNgiiTRo-zQ81NNRdUyw6qCj1y6wAAAYSEgIvI20221117082409705.JPEG','2022-09-15','GS25 2222',_binary '',14,'9894719165995578'),(43,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/KeWPDRPJLXnXJWJPfzrNgiiTRo-zQ81NNRdUyw6qCj1y6wAAAYSEgIvI20221117082429797.JPEG','2023-08-18','맥도날드 2222',_binary '',14,'521735681127'),(44,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/KeWPDRPJLXnXJWJPfzrNgiiTRo-zQ81NNRdUyw6qCj1y6wAAAYSEgIvI20221117083120464.JPEG','2022-09-15','GS25 5555',_binary '',14,'9894719165995578'),(45,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/KeWPDRPJLXnXJWJPfzrNgiiTRo-zQ81NNRdUyw6qCj1y6wAAAYSEgIvI20221117084343571.JPEG','2023-08-18','맥도날드6666',_binary '\0',14,'521735681127'),(46,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/TJqITfWneZf64crCQ_RHFhFu0FceBBdThzWmlK2qCisMqAAAAYSIflE820221118022216754.JPEG','2022-12-16','스타벅스',_binary '\0',14,'999948226900'),(48,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/TJqITfWneZf64crCQ_RHFhFu0FceBBdThzWmlK2qCisMqAAAAYSIflE820221118022237658.JPEG','2023-10-07','뚜레쥬르',_binary '\0',2,'9930744034245876'),(50,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/4MUEG1uxHbW_0zsH_FUhwkOdyz7Q1OjKfxwRYrACCisM0wAAAYSI7tem20221118044113993.JPEG','2020-10-27','BHC',_binary '\0',15,'946116133562'),(53,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/iXyBE3U6eWWGJ3jhh9nbJkEsBbhx9vg-2V451OcvCj102gAAAYSJkjc-20221118071032702.JPEG','2020-10-27','BHC',_binary '\0',15,'946116133562'),(54,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/psdzU7q1fNSISjgKixgQ33rvO-0L_3iISniiwqkxCinI2AAAAYR_DvHf20221120093742566.jpg','2022-12-31','바나프레소',_binary '\0',3,'123456781234'),(55,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/w16fF8No28BVQQ3WdYR8IzwkcBgd73B7OxkKtF_SCinI2QAAAYSUYMI120221120093958864.JPEG','2022-10-15','스타벅스 아메리카노',_binary '',2,'999995562089'),(56,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221120094240943.JPEG','2022-11-20','인증샷',_binary '\0',1,'123456781234'),(57,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221120094253645.JPEG','2022-11-20','인증샷',_binary '\0',1,'123456781234'),(58,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221120094334765.JPEG','2022-11-20','인증샷',_binary '\0',1,'123456781234'),(59,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221120094337932.JPEG','2022-11-20','인증샷',_binary '\0',1,'123456781234'),(60,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221120094439728.JPEG','2022-11-20','인증샷',_binary '\0',1,'123456781234'),(61,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221120094744052.JPEG','2022-11-20','인증샷',_binary '\0',1,'123456781234'),(64,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/JJ2m6yE9Ayc9-x7UadU_VStNqe-k8Dn0WZ1_myxGCj10mQAAAYSVR8d620221120014727925.JPEG','2022-10-15','스타벅스 아메리카노',_binary '',2,'9999955620899999'),(65,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/JJ2m6yE9Ayc9-x7UadU_VStNqe-k8Dn0WZ1_myxGCj10mQAAAYSVR8d620221120015023046.JPEG','2023-01-07','스타벅스 아메리카노',_binary '\0',14,'999547697217'),(66,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/JJ2m6yE9Ayc9-x7UadU_VStNqe-k8Dn0WZ1_myxGCj10mQAAAYSVR8d620221120015047345.JPEG','2022-12-24','스타벅스 아메리카노',_binary '\0',2,'999167121162'),(67,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/JJ2m6yE9Ayc9-x7UadU_VStNqe-k8Dn0WZ1_myxGCj10mQAAAYSVR8d620221120015229914.JPEG','2022-12-16','스타벅스 아메리카노',_binary '\0',14,'999948226900'),(69,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/JJ2m6yE9Ayc9-x7UadU_VStNqe-k8Dn0WZ1_myxGCj10mQAAAYSVR8d620221120020842699.JPEG','2022-11-24','GS25',_binary '',2,'9892532141723349'),(70,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/JJ2m6yE9Ayc9-x7UadU_VStNqe-k8Dn0WZ1_myxGCj10mQAAAYSVR8d620221120020952974.JPEG','2022-11-24','GS25',_binary '\0',14,'9892532141723349'),(71,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221120062522058.JPEG','2022-11-21','인증샷',_binary '\0',1,'123456781234'),(72,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/bhdbonAj0nN281yNyPBxmlzW554Gd8T9wToFaBzaCj11nAAAAYSXWivH20221120113755622.JPEG','2022-12-16','스타벅스 아메리카노',_binary '\0',2,'999948226900'),(73,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221120113807026.JPEG','2022-11-21','인증샷',_binary '\0',1,'123456781234'),(74,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122431732.jpg','2022-12-31','인증샷',_binary '\0',1,'123456781234'),(75,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121122619994.JPG','2022-12-31','인증샷2',_binary '\0',1,'123456781234'),(76,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121010941201.JPEG','2022-11-21','인증샷',_binary '\0',1,'123456781234'),(77,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/0mbgLAjgx8zPe_zf9FNd10T5xCP56LT8SyRmwLuICinJXwAAAYSXoJa520221121012137517.JPEG','2022-11-18','스타벅스 아메리카노',_binary '',2,'999663240163'),(78,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/121220221121012407405.JPEG','2022-11-21','인증샷',_binary '\0',1,'123456781234'),(79,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/TP65wMupqWtG_uHLqqMjyYRdGcvX_mybRBKUly2WCj1z7AAAAYSYaakX20221121050235450.JPEG','2022-11-18','스타벅스',_binary '\0',3,'999663240163'),(80,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/0mbgLAjgx8zPe_zf9FNd10T5xCP56LT8SyRmwLuICinJXwAAAYSXoJa520221121055608913.JPEG','2022-11-24','GS25',_binary '\0',2,'9892532141723349'),(81,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/TP65wMupqWtG_uHLqqMjyYRdGcvX_mybRBKUly2WCj1z7AAAAYSYaakX20221121060232975.JPEG','2022-10-28','이마트',_binary '\0',3,'708305361007'),(82,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/TP65wMupqWtG_uHLqqMjyYRdGcvX_mybRBKUly2WCj1z7AAAAYSYaakX20221121061154333.JPEG','2022-10-28','이마트',_binary '\0',3,'708305361007'),(83,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/TP65wMupqWtG_uHLqqMjyYRdGcvX_mybRBKUly2WCj1z7AAAAYSYaakX20221121061203340.JPEG','2022-10-28','이마트',_binary '\0',3,'708305361007'),(84,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/TP65wMupqWtG_uHLqqMjyYRdGcvX_mybRBKUly2WCj1z7AAAAYSYaakX20221121061212321.JPEG','2022-10-28','이마트',_binary '\0',3,'708305361007'),(85,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/TP65wMupqWtG_uHLqqMjyYRdGcvX_mybRBKUly2WCj1z7AAAAYSYaakX20221121061220751.JPEG','2022-10-28','이마트',_binary '\0',3,'708305361007'),(86,'https://mygimochi.s3.ap-northeast-2.amazonaws.com/TP65wMupqWtG_uHLqqMjyYRdGcvX_mybRBKUly2WCj1z7AAAAYSYaakX20221121061229334.JPEG','2022-10-28','이마트',_binary '\0',3,'708305361007');
/*!40000 ALTER TABLE `gifticon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session`
--

DROP TABLE IF EXISTS `session`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session` (
  `session_id` bigint NOT NULL AUTO_INCREMENT,
  `anniversary` date NOT NULL,
  `expire_time` date NOT NULL,
  `name` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `session_type_id` bigint NOT NULL,
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`session_id`),
  KEY `FK1bi1pmqjgipw7dx3j6bl37dja` (`user_id`),
  KEY `FKhxxv3baghr2qwupsg0uem0udm` (`session_type_id`),
  CONSTRAINT `FK1bi1pmqjgipw7dx3j6bl37dja` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`),
  CONSTRAINT `FKhxxv3baghr2qwupsg0uem0udm` FOREIGN KEY (`session_type_id`) REFERENCES `session_type` (`session_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=63 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session`
--

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;
INSERT INTO `session` VALUES (55,'2022-11-21','2022-11-28','자율프로젝트 끝',5,14),(56,'2022-12-25','2023-01-01',NULL,3,14),(57,'2022-12-30','2023-01-06',NULL,2,17),(58,'2022-12-17','2022-12-24',NULL,1,17),(59,'2023-04-17','2023-04-24',NULL,1,4),(60,'2022-12-25','2023-01-01',NULL,3,2),(61,'2022-11-25','2022-12-02','싸피 끗',5,2),(62,'2022-11-24','2022-12-01',NULL,2,2);
/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_message`
--

DROP TABLE IF EXISTS `session_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_message` (
  `session_message_id` bigint NOT NULL AUTO_INCREMENT,
  `expire_time` date NOT NULL,
  `field` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `nickname` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `gifticon_id` bigint DEFAULT NULL,
  `session_id` bigint DEFAULT NULL,
  `message_type` int DEFAULT NULL,
  PRIMARY KEY (`session_message_id`),
  KEY `FKcyhysqgg0i9qjyy1o39tmqly9` (`gifticon_id`),
  KEY `FKnuy8pa0xhq1prbpgrkjgwvm93` (`session_id`),
  CONSTRAINT `FKcyhysqgg0i9qjyy1o39tmqly9` FOREIGN KEY (`gifticon_id`) REFERENCES `gifticon` (`gifticon_id`),
  CONSTRAINT `FKnuy8pa0xhq1prbpgrkjgwvm93` FOREIGN KEY (`session_id`) REFERENCES `session` (`session_id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_message`
--

LOCK TABLES `session_message` WRITE;
/*!40000 ALTER TABLE `session_message` DISABLE KEYS */;
INSERT INTO `session_message` VALUES (39,'2022-11-27','안녕? 친구가 되자','다익스트라',NULL,56,1),(40,'2022-11-27','올해 너랑 친구가 될수 있어서 좋았어.','김동욱산타클로스',NULL,56,2),(41,'2022-11-27','메리 크리스마스','이승훈',NULL,56,4),(42,'2022-11-27','메리 크리스마스!','친구된지3일',67,56,2),(43,'2022-11-27','크리스마스 잘보내','이승훈',70,56,4),(44,'2022-11-28','테스트','이승훈',65,56,2);
/*!40000 ALTER TABLE `session_message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `session_type`
--

DROP TABLE IF EXISTS `session_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `session_type` (
  `session_type_id` bigint NOT NULL AUTO_INCREMENT,
  `type` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`session_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `session_type`
--

LOCK TABLES `session_type` WRITE;
/*!40000 ALTER TABLE `session_type` DISABLE KEYS */;
INSERT INTO `session_type` VALUES (1,'생일'),(2,'졸업'),(3,'크리스마스'),(4,'설날'),(5,'사용자정의');
/*!40000 ALTER TABLE `session_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT,
  `user_birthday` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_email` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `user_fb_token` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_kakao_id` bigint NOT NULL,
  `user_nickname` varchar(255) COLLATE utf8mb4_general_ci NOT NULL,
  `user_point` int NOT NULL,
  `user_profile` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_social_refresh_token` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `user_social_token` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `expires_in` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `used_count` int NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'11212','1212','1212',12121,'21212',21212,'1212','1212','1212',NULL,0),(2,'1006','shhh1006@daum.net','eyRcxjxSQEmGE1LbaAm3df:APA91bFDnQWScGYy3YnkZrsfqugvavSHNXM5mPPhQEaHj-AmAdSBwTGJ5FnuFUSPHCQGiUi1D4MG5E0Sk8yf0wwSn0BWa_F_GI9dVUDeTtx-FKrze-LoxkqWnNW8N66iWYFtm3fVNLv1',2508138935,'이승훈',0,'http://k.kakaocdn.net/dn/bMIw0f/btqyogbC4Yb/XkFJ6Y4NhpGRJs76VvOKR0/img_110x110.jpg','_lvfjYicjIrPehdlWFpK-Z0H-LmbcNtLqn6ILvDeCinJXwAAAYSXoJa4','0mbgLAjgx8zPe_zf9FNd10T5xCP56LT8SyRmwLuICinJXwAAAYSXoJa5','2022-11-21 06:39:54',27),(3,'0221','minjae77777@naver.com','fo3oQaL3Tt28xgd0wsAMV5:APA91bGfcHhwv0iIuI_JOTn31OJZIrgZxq5buvOsOgPv4kyExladMA6WPBenoXUCI28Eixln9b_QBRbP6ANG5opWJHIUxtPJ8Tsz3XohDDG0J8LN0i3H2qCJKqyJFOax_S9CBDshAuv9',2507326486,'전민재',0,'http://k.kakaocdn.net/dn/cbGiB5/btrG8l3dLJz/PVb4fTZxEnSWOPnY18kcJ1/img_110x110.jpg','VXGrOro-Pa6htv4fZxaPbqpN8gcZxMC6I6JFm_ovCj1z7AAAAYSYaakV','TP65wMupqWtG_uHLqqMjyYRdGcvX_mybRBKUly2WCj1z7AAAAYSYaakX','2022-11-21 10:19:32',1),(4,'0417','kdw508@naver.com','cpXgRc7GQNG4Hj3GOxSF9r:APA91bHn6dRLb1wgJjzVOHmK4mOuevCC-9kp_dWaVDmuEeAXo9PAZpNlrXDf71DUSZYI9-XMRZDBGNPtVgj6SWM6jCWO1wdm-t2MlWvPRv3L0MBegNq8KuX98tLn9UhNOXcV8Spz3KWe',2500662774,'김동욱',0,'http://k.kakaocdn.net/dn/h100C/btrIxBkx2lw/bVD6gXE94EkMeQO7KScycK/img_110x110.jpg','2DwmffPpD3dBycRMlLYAhfiCxgIN3LiHHNgJyoBzCil1KQAAAYSXsL2g','qOvar1YeOyXcr0YBmmub-lEcAvMZwFBg6rmIF4jPCil1KQAAAYSXsL2i','2022-11-21 06:57:33',2),(14,'0525','te4438@naver.com','dHYnbvCBR4aPeBEc3SXjEY:APA91bGZxNskW3gnTtUcA2J_MJX25Rw60TPvbbZ0p6lyupMvcJ7OxVgqtJTbXmkW_8a9ShtSp-LwAq10wd0QROeHzSHxmolqtqs_Xa30ueSYKBuFn09p1-BpbjumcX-opH9Z4e4AAxZv',2501734120,'최태순',0,'http://k.kakaocdn.net/dn/0bfIG/btrRfIhAt8i/pIZeVSizwK8qbJJv2FpBc0/img_110x110.jpg','PUOT3srYhMs9ysn-EA4nWj9xTmNB691ObBWzf761CilwUAAAAYSDJsGM','SyW83twRhiszq5XzXKOYCWtmJsgzWFB0Ete5sj4UCisM0wAAAYSU-Ju8','2022-11-17 15:57:45',6),(15,'0218','onestore789@gmail.com','dx23ZT9XT4KEJUMBeOqq2N:APA91bEK8UcKVbSbl4lIO3XrnP9OdvYe8G8HRR2j7N-XUYpFmgVUJ32_6KiQCdzUyCqs7y7614Kl-C7pliYkM05C5L1krcCJsbQ6yitcuaJ-tj2yhZc2gSER-sBZP2E4Q8yLYsHMUM__',2539375302,'니가',0,'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg','A7Z-t5qC4J7_9VM0AL74A94HWaiv7Z-4XkJVHs40Cj1ymAAAAYSJk1eJ','DugCNXS0TAhuPIojZqDSBuXnJZjCMZS_xVRDxt5RCj1ymAAAAYSJk1eK','2022-11-18 10:08:12',8),(16,'0322','onestore1233@gmail.com','cLWOhnXiQjun10xhtc3eYH:APA91bFIQ7_ql13PBUhLHzBnM9UkUtY1ru7HZcisT5KBjrZ0tqf1ABG9__60HzT5BqC-a_ypH3-HhjUXDHXFmryBd1a7QlDFaV_xPjWz9E1JRVwu-6DUK2lgIPVWCTqFbJB9k6U8ocPo',2539716167,'원투',0,'http://k.kakaocdn.net/dn/dpk9l1/btqmGhA2lKL/Oz0wDuJn1YV2DIn92f6DVK/img_110x110.jpg','wDmQ06pcBhLWlRek3gYCnlUl06oWKDxG4Qio1NNsCj11GwAAAYSJ0S9i','WClCQvRvbhl9HxvksEwoGl15oJeWmqZfOWCWJwCpCj11GwAAAYSJ0S9k','2022-11-18 14:18:18',0),(17,'1217','daeunblue@nate.com','dnRADJv0TCuM0L_FLXY1Oq:APA91bEtlLBNAncI1ICKHHW4ZjSjZtd7C6onmu3Eue9iNoshg8TGUpqwESv4VlN9sQqiRHIBd0Ekgksnqx2izF-svKDrCm5oKKbih2yqVHOIVjHz6NZd42BpcPJDbo-XeZO11-mZ9PjQ',2500837675,'ㄷㅇ',0,'http://k.kakaocdn.net/dn/0C9nu/btrq4p1wT0T/QmNJTYkMMcysvi35bIKGkK/img_110x110.jpg','JYqSuatdARYTBggZ251kkohg84PBu-ohnBoW3iKgCj10EQAAAYSVCFY4','LkxIBqyyIY3fPXb57xMPGM7w6zECQoSm6Rhyfoz7Cj10EQAAAYSVCFY5','2022-11-20 18:34:22',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vote`
--

DROP TABLE IF EXISTS `vote`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vote` (
  `vote_id` bigint NOT NULL AUTO_INCREMENT,
  `auth_user_id` bigint NOT NULL,
  `vote_user_id` bigint NOT NULL,
  `challenge_auth_id` bigint DEFAULT NULL,
  PRIMARY KEY (`vote_id`),
  KEY `FKtd1h5bxl4t5xka1dtb3d2wn7n` (`challenge_auth_id`),
  CONSTRAINT `FKtd1h5bxl4t5xka1dtb3d2wn7n` FOREIGN KEY (`challenge_auth_id`) REFERENCES `challenge_auth` (`challenge_auth_id`)
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vote`
--

LOCK TABLES `vote` WRITE;
/*!40000 ALTER TABLE `vote` DISABLE KEYS */;
INSERT INTO `vote` VALUES (8,2,3,7),(9,2,4,7),(10,2,14,7),(11,2,14,8),(12,2,4,8),(13,2,3,8),(14,2,3,10),(15,2,4,10),(16,2,14,10),(17,2,14,9),(18,2,3,11),(19,2,4,11),(20,2,4,11),(21,2,14,11),(22,2,16,11),(23,2,17,11),(24,2,17,12),(25,2,14,12),(26,2,3,12),(27,2,3,12),(28,2,4,12),(29,2,4,13),(30,2,4,14),(31,2,3,14),(32,2,14,14),(33,2,16,14),(34,2,16,15),(35,2,14,15),(36,2,3,15),(37,2,4,15),(38,2,4,16),(39,2,3,16),(40,2,14,16),(41,2,17,16),(45,17,3,20),(46,2,3,20),(47,2,4,20);
/*!40000 ALTER TABLE `vote` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-11-22 10:41:49
