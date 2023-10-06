-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: localhost    Database: suldaksuldak
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `tb_consent_item`
--

DROP TABLE IF EXISTS `tb_consent_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_consent_item` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `item_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `item_seq` int unsigned NOT NULL,
  `item_text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_consent_item`
--

LOCK TABLES `tb_consent_item` WRITE;
/*!40000 ALTER TABLE `tb_consent_item` DISABLE KEYS */;
INSERT INTO `tb_consent_item` VALUES (1,'TEAM_OF_SERVICE',1,'제1조(목적) 이 약관은 술닥술닥 (이하 \'회사\' 라고 합니다)가 제공하는 제반 서비스의 이용과 관련하여 회사와 회원 과의 권리, 의무 및 책임사항, 기타 필요한 사항을 규정함을 목적으로 합니다.'),(2,'TEAM_OF_SERVICE',2,'제2조(정의) 이 약관에서 사용하는 주요 용어의 정의는 다음과 같습니다. \'서비스\'라 함은 구현되는 단말기(PC, TV, 휴대형단말기 등의 각종 유무선 장치를 포함)와 상관없이 \'이용자\'가 이용할 수 있는 회사가 제공하는 제반 서비스를 의미합니다.');
/*!40000 ALTER TABLE `tb_consent_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_cut_off_user`
--

DROP TABLE IF EXISTS `tb_cut_off_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_cut_off_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `created_at` timestamp NOT NULL,
  `modified_at` timestamp NOT NULL,
  `cut_user_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_cut_off_user_FK` (`user_id`),
  KEY `tb_cut_off_user_FK_1` (`cut_user_id`),
  CONSTRAINT `tb_cut_off_user_FK` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_cut_off_user_FK_1` FOREIGN KEY (`cut_user_id`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_cut_off_user`
--

LOCK TABLES `tb_cut_off_user` WRITE;
/*!40000 ALTER TABLE `tb_cut_off_user` DISABLE KEYS */;
INSERT INTO `tb_cut_off_user` VALUES (2,6,'2023-10-05 04:15:15','2023-10-05 04:15:15',7);
/*!40000 ALTER TABLE `tb_cut_off_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_drinking_capacity`
--

DROP TABLE IF EXISTS `tb_drinking_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_drinking_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_drinking_capacity_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주량 (좋아하는 정도)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_drinking_capacity`
--

LOCK TABLES `tb_drinking_capacity` WRITE;
/*!40000 ALTER TABLE `tb_drinking_capacity` DISABLE KEYS */;
INSERT INTO `tb_drinking_capacity` VALUES (4,'입문자'),(3,'전문가'),(2,'평균');
/*!40000 ALTER TABLE `tb_drinking_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_file_base`
--

DROP TABLE IF EXISTS `tb_file_base`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_file_base` (
  `file_nm` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_location` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `ori_file_nm` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `file_ext` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp NOT NULL,
  `modified_at` timestamp NOT NULL,
  PRIMARY KEY (`file_nm`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_file_base`
--

LOCK TABLES `tb_file_base` WRITE;
/*!40000 ALTER TABLE `tb_file_base` DISABLE KEYS */;
INSERT INTO `tb_file_base` VALUES ('10c2b6de2d9d4ec9be73dd7c35298837_1696402175140','C:\\Jh\\work\\study_workspace\\multi-server-suldak\\temp','infoWarningDark','.png','2023-10-04 06:49:35','2023-10-04 06:49:35');
/*!40000 ALTER TABLE `tb_file_base` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_liquor`
--

DROP TABLE IF EXISTS `tb_liquor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_liquor` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `summary_explanation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `detail_explanation` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp NOT NULL,
  `modified_at` timestamp NOT NULL,
  `liquor_abv_id` bigint unsigned DEFAULT NULL,
  `liquor_detail_id` bigint unsigned DEFAULT NULL,
  `drinking_capacity_id` bigint unsigned DEFAULT NULL,
  `liquor_name_id` bigint unsigned DEFAULT NULL,
  `search_tag` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `liquor_recipe` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `detail_abv` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_liquor_FK` (`liquor_abv_id`),
  KEY `tb_liquor_FK_1` (`liquor_detail_id`),
  KEY `tb_liquor_FK_2` (`drinking_capacity_id`),
  KEY `tb_liquor_FK_3` (`liquor_name_id`),
  CONSTRAINT `tb_liquor_FK` FOREIGN KEY (`liquor_abv_id`) REFERENCES `tb_liquor_abv` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `tb_liquor_FK_1` FOREIGN KEY (`liquor_detail_id`) REFERENCES `tb_liquor_detail` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `tb_liquor_FK_2` FOREIGN KEY (`drinking_capacity_id`) REFERENCES `tb_drinking_capacity` (`id`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `tb_liquor_FK_3` FOREIGN KEY (`liquor_name_id`) REFERENCES `tb_liquor_name` (`id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='술 DB';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor`
--

LOCK TABLES `tb_liquor` WRITE;
/*!40000 ALTER TABLE `tb_liquor` DISABLE KEYS */;
INSERT INTO `tb_liquor` VALUES (2,'CASS','한국의 맥주','한국의 대표 맥주입니다.','2023-09-17 07:05:06','2023-10-05 07:13:58',1,1,4,2,'맥주 카스','테스트',4.5),(3,'Kelly','한국의 맥주','손석구가 광고하는 맥주','2023-09-17 09:22:00','2023-09-17 09:22:00',1,1,NULL,NULL,'맥주 켈리',NULL,4.5),(4,'처음처럼','한국의 소주','한국의 소주로 유명한 소주','2023-10-06 07:27:31','2023-10-06 07:30:48',NULL,NULL,2,NULL,'소주, 처음처럼, 처음 처럼',NULL,16.5);
/*!40000 ALTER TABLE `tb_liquor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_liquor_abv`
--

DROP TABLE IF EXISTS `tb_liquor_abv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_liquor_abv` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_liquor_abv_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='도수 ex) ~5%, 6~10%, ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_abv`
--

LOCK TABLES `tb_liquor_abv` WRITE;
/*!40000 ALTER TABLE `tb_liquor_abv` DISABLE KEYS */;
INSERT INTO `tb_liquor_abv` VALUES (1,'~5%'),(2,'6% ~ 10%');
/*!40000 ALTER TABLE `tb_liquor_abv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_liquor_detail`
--

DROP TABLE IF EXISTS `tb_liquor_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_liquor_detail` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_liquor_detail_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주종 ex) 레드와인, 화이트와인, 로제와인, ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_detail`
--

LOCK TABLES `tb_liquor_detail` WRITE;
/*!40000 ALTER TABLE `tb_liquor_detail` DISABLE KEYS */;
INSERT INTO `tb_liquor_detail` VALUES (1,'에일');
/*!40000 ALTER TABLE `tb_liquor_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_liquor_material`
--

DROP TABLE IF EXISTS `tb_liquor_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_liquor_material` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_material_type_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='재료';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_material`
--

LOCK TABLES `tb_liquor_material` WRITE;
/*!40000 ALTER TABLE `tb_liquor_material` DISABLE KEYS */;
INSERT INTO `tb_liquor_material` VALUES (2,'달걀'),(1,'레몬');
/*!40000 ALTER TABLE `tb_liquor_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_liquor_name`
--

DROP TABLE IF EXISTS `tb_liquor_name`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_liquor_name` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_liquor_name_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주종 ex) 소주, 맥주, ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_name`
--

LOCK TABLES `tb_liquor_name` WRITE;
/*!40000 ALTER TABLE `tb_liquor_name` DISABLE KEYS */;
INSERT INTO `tb_liquor_name` VALUES (2,'맥주'),(1,'소주');
/*!40000 ALTER TABLE `tb_liquor_name` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_liquor_search_log`
--

DROP TABLE IF EXISTS `tb_liquor_search_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_liquor_search_log` (
  `id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `liquor_id` bigint unsigned NOT NULL,
  `search_at` timestamp NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_liquor_search_log_FK` (`liquor_id`),
  CONSTRAINT `tb_liquor_search_log_FK` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_search_log`
--

LOCK TABLES `tb_liquor_search_log` WRITE;
/*!40000 ALTER TABLE `tb_liquor_search_log` DISABLE KEYS */;
INSERT INTO `tb_liquor_search_log` VALUES ('1696567458111_2',2,'2023-10-06 04:44:18'),('1696567552768_2',2,'2023-10-06 04:45:53'),('1696567556229_3',3,'2023-10-06 04:45:56');
/*!40000 ALTER TABLE `tb_liquor_search_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_liquor_sell`
--

DROP TABLE IF EXISTS `tb_liquor_sell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_liquor_sell` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_liquor_sell_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='판매처 ex) 편이점, ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_sell`
--

LOCK TABLES `tb_liquor_sell` WRITE;
/*!40000 ALTER TABLE `tb_liquor_sell` DISABLE KEYS */;
INSERT INTO `tb_liquor_sell` VALUES (1,'편의점');
/*!40000 ALTER TABLE `tb_liquor_sell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_liquor_snack`
--

DROP TABLE IF EXISTS `tb_liquor_snack`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_liquor_snack` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_liquor_snack_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='추천 안주';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_snack`
--

LOCK TABLES `tb_liquor_snack` WRITE;
/*!40000 ALTER TABLE `tb_liquor_snack` DISABLE KEYS */;
INSERT INTO `tb_liquor_snack` VALUES (1,'과자'),(3,'마른안주'),(2,'치즈');
/*!40000 ALTER TABLE `tb_liquor_snack` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_mt_li`
--

DROP TABLE IF EXISTS `tb_mt_li`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_mt_li` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `liquor_id` bigint unsigned NOT NULL,
  `liquor_material_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_mt_li_FK` (`liquor_id`),
  KEY `tb_mt_li_FK_to_mt` (`liquor_material_id`),
  CONSTRAINT `tb_mt_li_FK` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_mt_li_FK_to_mt` FOREIGN KEY (`liquor_material_id`) REFERENCES `tb_liquor_material` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='재료 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_mt_li`
--

LOCK TABLES `tb_mt_li` WRITE;
/*!40000 ALTER TABLE `tb_mt_li` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_mt_li` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_reservation_user`
--

DROP TABLE IF EXISTS `tb_reservation_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_reservation_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_email` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp NOT NULL,
  `modified_at` timestamp NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='사전예약 유저 목록';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_reservation_user`
--

LOCK TABLES `tb_reservation_user` WRITE;
/*!40000 ALTER TABLE `tb_reservation_user` DISABLE KEYS */;
INSERT INTO `tb_reservation_user` VALUES (2,'swdfrgt3701@naver.com','2023-08-27 10:59:15','2023-08-27 10:59:15');
/*!40000 ALTER TABLE `tb_reservation_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sl_li`
--

DROP TABLE IF EXISTS `tb_sl_li`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_sl_li` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `liquor_id` bigint unsigned NOT NULL,
  `liquor_sell_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_sl_li_FK_to_li` (`liquor_id`),
  KEY `tb_sl_li_FK` (`liquor_sell_id`),
  CONSTRAINT `tb_sl_li_FK` FOREIGN KEY (`liquor_sell_id`) REFERENCES `tb_liquor_sell` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_sl_li_FK_to_li` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='판매처 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sl_li`
--

LOCK TABLES `tb_sl_li` WRITE;
/*!40000 ALTER TABLE `tb_sl_li` DISABLE KEYS */;
INSERT INTO `tb_sl_li` VALUES (1,2,1),(2,3,1),(3,4,1);
/*!40000 ALTER TABLE `tb_sl_li` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_sn_li`
--

DROP TABLE IF EXISTS `tb_sn_li`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_sn_li` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `liquor_id` bigint unsigned NOT NULL,
  `liquor_snack_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_sn_li_FK` (`liquor_id`),
  KEY `tb_sn_li_FK_1` (`liquor_snack_id`),
  CONSTRAINT `tb_sn_li_FK` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_sn_li_FK_1` FOREIGN KEY (`liquor_snack_id`) REFERENCES `tb_liquor_snack` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='안주 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sn_li`
--

LOCK TABLES `tb_sn_li` WRITE;
/*!40000 ALTER TABLE `tb_sn_li` DISABLE KEYS */;
INSERT INTO `tb_sn_li` VALUES (1,2,1),(2,2,3),(3,3,1),(4,3,3);
/*!40000 ALTER TABLE `tb_sn_li` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_st_li`
--

DROP TABLE IF EXISTS `tb_st_li`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_st_li` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `liquor_id` bigint unsigned NOT NULL,
  `state_type_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_st_li_FK_to_li` (`liquor_id`),
  KEY `tb_st_li_FK_to_st` (`state_type_id`),
  CONSTRAINT `tb_st_li_FK_to_li` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_st_li_FK_to_st` FOREIGN KEY (`state_type_id`) REFERENCES `tb_state_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='기분 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_st_li`
--

LOCK TABLES `tb_st_li` WRITE;
/*!40000 ALTER TABLE `tb_st_li` DISABLE KEYS */;
INSERT INTO `tb_st_li` VALUES (3,2,2);
/*!40000 ALTER TABLE `tb_st_li` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_state_type`
--

DROP TABLE IF EXISTS `tb_state_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_state_type` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_state_type_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='기분 (상태) ex) 기분 전환, 피곤, 기쁠 때, ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_state_type`
--

LOCK TABLES `tb_state_type` WRITE;
/*!40000 ALTER TABLE `tb_state_type` DISABLE KEYS */;
INSERT INTO `tb_state_type` VALUES (2,'피곤할 때');
/*!40000 ALTER TABLE `tb_state_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_taste_type`
--

DROP TABLE IF EXISTS `tb_taste_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_taste_type` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_taste_type_un` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='맛 종류 ex) 술맛, 달달한 ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_taste_type`
--

LOCK TABLES `tb_taste_type` WRITE;
/*!40000 ALTER TABLE `tb_taste_type` DISABLE KEYS */;
INSERT INTO `tb_taste_type` VALUES (1,'씁쓸한 맛');
/*!40000 ALTER TABLE `tb_taste_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_tt_li`
--

DROP TABLE IF EXISTS `tb_tt_li`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_tt_li` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `liquor_id` bigint unsigned NOT NULL,
  `taste_type_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_tt_li_FK` (`liquor_id`),
  KEY `tb_tt_li_FK_to_tt` (`taste_type_id`),
  CONSTRAINT `tb_tt_li_FK` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_tt_li_FK_to_tt` FOREIGN KEY (`taste_type_id`) REFERENCES `tb_taste_type` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='맛 종류 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_tt_li`
--

LOCK TABLES `tb_tt_li` WRITE;
/*!40000 ALTER TABLE `tb_tt_li` DISABLE KEYS */;
INSERT INTO `tb_tt_li` VALUES (1,3,1);
/*!40000 ALTER TABLE `tb_tt_li` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user`
--

DROP TABLE IF EXISTS `tb_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp NOT NULL,
  `modified_at` timestamp NOT NULL,
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `birthday_year` int unsigned NOT NULL,
  `registration` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `user_pw` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `level` int unsigned NOT NULL DEFAULT '0',
  `warning_cnt` int unsigned NOT NULL DEFAULT '0',
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `self_introduction` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `file_base_nm` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `alarm_active` tinyint(1) NOT NULL DEFAULT '1',
  `sound_active` tinyint(1) NOT NULL DEFAULT '1',
  `vibration_active` tinyint(1) NOT NULL DEFAULT '1',
  `push_active` tinyint(1) NOT NULL DEFAULT '1',
  `marketing_active` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_user_un_email` (`user_email`),
  UNIQUE KEY `tb_user_un_nickname` (`nickname`),
  KEY `tb_user_FK` (`file_base_nm`),
  CONSTRAINT `tb_user_FK` FOREIGN KEY (`file_base_nm`) REFERENCES `tb_file_base` (`file_nm`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='유저 목록';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
INSERT INTO `tb_user` VALUES (6,'admin','2023-10-04 04:24:37','2023-10-05 07:26:13','ADMIN','M',2000,'SULDAKSULDAK','2CSU8F1pF7oC96qilonMtES7c/IDgIdssF0fN1N7eJI=',0,0,1,'안녕!','10c2b6de2d9d4ec9be73dd7c35298837_1696402175140',1,0,1,1,1),(7,'test','2023-10-05 02:20:56','2023-10-05 02:20:56','TEST','M',2000,'SULDAKSULDAK','NyaDNd1pMQRb3N+SYj/4GaZCRLU9DnRtQ4eXNJ1NpXg=',0,0,1,NULL,NULL,1,1,1,1,1);
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_user_liquor`
--

DROP TABLE IF EXISTS `tb_user_liquor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_user_liquor` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `liquor_id` bigint unsigned NOT NULL,
  `search_cnt` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_user_tag_FK` (`user_id`),
  KEY `tb_user_liquor_FK` (`liquor_id`),
  CONSTRAINT `tb_user_liquor_FK` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_user_tag_FK` FOREIGN KEY (`user_id`) REFERENCES `tb_user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user_liquor`
--

LOCK TABLES `tb_user_liquor` WRITE;
/*!40000 ALTER TABLE `tb_user_liquor` DISABLE KEYS */;
INSERT INTO `tb_user_liquor` VALUES (1,6,2,0.3),(2,6,3,0.1);
/*!40000 ALTER TABLE `tb_user_liquor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-06 17:38:36
