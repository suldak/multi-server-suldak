-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: suldaksuldak
-- ------------------------------------------------------
-- Server version	8.0.31

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
-- Table structure for table `tb_abv_li`
--

DROP TABLE IF EXISTS `tb_abv_li`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_abv_li` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `liquor_id` bigint unsigned NOT NULL,
  `liquor_abv_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_abv_li_FK_to_li` (`liquor_id`),
  KEY `tb_abv_li_FK_to_abv` (`liquor_abv_id`),
  CONSTRAINT `tb_abv_li_FK_to_abv` FOREIGN KEY (`liquor_abv_id`) REFERENCES `tb_liquor_abv` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_abv_li_FK_to_li` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='도수 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_abv_li`
--

LOCK TABLES `tb_abv_li` WRITE;
/*!40000 ALTER TABLE `tb_abv_li` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_abv_li` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_dc_li`
--

DROP TABLE IF EXISTS `tb_dc_li`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_dc_li` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `drinking_capacity_id` bigint unsigned NOT NULL,
  `liquor_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_dc_li_FK_to_dk` (`drinking_capacity_id`),
  KEY `tb_dc_li_FK_to_li` (`liquor_id`),
  CONSTRAINT `tb_dc_li_FK_to_dk` FOREIGN KEY (`drinking_capacity_id`) REFERENCES `tb_drinking_capacity` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_dc_li_FK_to_li` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주랑 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_dc_li`
--

LOCK TABLES `tb_dc_li` WRITE;
/*!40000 ALTER TABLE `tb_dc_li` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_dc_li` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_drinking_capacity`
--

DROP TABLE IF EXISTS `tb_drinking_capacity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_drinking_capacity` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `level` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_drinking_capacity_un` (`level`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주량 (좋아하는 정도)';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_drinking_capacity`
--

LOCK TABLES `tb_drinking_capacity` WRITE;
/*!40000 ALTER TABLE `tb_drinking_capacity` DISABLE KEYS */;
INSERT INTO `tb_drinking_capacity` VALUES (1,'입문자'),(3,'전문가'),(2,'평군');
/*!40000 ALTER TABLE `tb_drinking_capacity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_dt_li`
--

DROP TABLE IF EXISTS `tb_dt_li`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_dt_li` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `liquor_id` bigint unsigned NOT NULL,
  `liquor_detail_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_dt_li_FK` (`liquor_id`),
  KEY `tb_dt_li_FK_to_li` (`liquor_detail_id`),
  CONSTRAINT `tb_dt_li_FK` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_dt_li_FK_to_li` FOREIGN KEY (`liquor_detail_id`) REFERENCES `tb_liquor_detail` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주종 (레드와인) to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_dt_li`
--

LOCK TABLES `tb_dt_li` WRITE;
/*!40000 ALTER TABLE `tb_dt_li` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_dt_li` ENABLE KEYS */;
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
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='술 DB';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor`
--

LOCK TABLES `tb_liquor` WRITE;
/*!40000 ALTER TABLE `tb_liquor` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='도수 ex) ~5%, 6~10%, ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_abv`
--

LOCK TABLES `tb_liquor_abv` WRITE;
/*!40000 ALTER TABLE `tb_liquor_abv` DISABLE KEYS */;
INSERT INTO `tb_liquor_abv` VALUES (1,'~5%');
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='재료';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_material`
--

LOCK TABLES `tb_liquor_material` WRITE;
/*!40000 ALTER TABLE `tb_liquor_material` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주종 ex) 소주, 맥주, ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_name`
--

LOCK TABLES `tb_liquor_name` WRITE;
/*!40000 ALTER TABLE `tb_liquor_name` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_liquor_name` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_liquor_recipe`
--

DROP TABLE IF EXISTS `tb_liquor_recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_liquor_recipe` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `liquor_id` bigint unsigned NOT NULL,
  `content_cnt` int unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_liquor_recipe_FK` (`liquor_id`),
  CONSTRAINT `tb_liquor_recipe_FK` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='레시피';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_recipe`
--

LOCK TABLES `tb_liquor_recipe` WRITE;
/*!40000 ALTER TABLE `tb_liquor_recipe` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_liquor_recipe` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='판매처 ex) 편이점, ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_sell`
--

LOCK TABLES `tb_liquor_sell` WRITE;
/*!40000 ALTER TABLE `tb_liquor_sell` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='추천 안주';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_liquor_snack`
--

LOCK TABLES `tb_liquor_snack` WRITE;
/*!40000 ALTER TABLE `tb_liquor_snack` DISABLE KEYS */;
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
-- Table structure for table `tb_nm_li`
--

DROP TABLE IF EXISTS `tb_nm_li`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tb_nm_li` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `liquor_id` bigint unsigned NOT NULL,
  `liquor_name_id` bigint unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `tb_nm_li_FK_to_li` (`liquor_id`),
  KEY `tb_nm_li_FK_to_nm` (`liquor_name_id`),
  CONSTRAINT `tb_nm_li_FK_to_li` FOREIGN KEY (`liquor_id`) REFERENCES `tb_liquor` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `tb_nm_li_FK_to_nm` FOREIGN KEY (`liquor_name_id`) REFERENCES `tb_liquor_name` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='주종 (소주) to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_nm_li`
--

LOCK TABLES `tb_nm_li` WRITE;
/*!40000 ALTER TABLE `tb_nm_li` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_nm_li` ENABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='판매처 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sl_li`
--

LOCK TABLES `tb_sl_li` WRITE;
/*!40000 ALTER TABLE `tb_sl_li` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='안주 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_sn_li`
--

LOCK TABLES `tb_sn_li` WRITE;
/*!40000 ALTER TABLE `tb_sn_li` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='기분 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_st_li`
--

LOCK TABLES `tb_st_li` WRITE;
/*!40000 ALTER TABLE `tb_st_li` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='기분 (상태) ex) 기분 전환, 피곤, 기쁠 때, ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_state_type`
--

LOCK TABLES `tb_state_type` WRITE;
/*!40000 ALTER TABLE `tb_state_type` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='맛 종류 ex) 술맛, 달달한 ...';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_taste_type`
--

LOCK TABLES `tb_taste_type` WRITE;
/*!40000 ALTER TABLE `tb_taste_type` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='맛 종류 to 술';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_tt_li`
--

LOCK TABLES `tb_tt_li` WRITE;
/*!40000 ALTER TABLE `tb_tt_li` DISABLE KEYS */;
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
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `created_at` timestamp NOT NULL,
  `modified_at` timestamp NOT NULL,
  `nickname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `gender` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `birthday_year` int unsigned NOT NULL,
  `registration` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `tb_user_un_email` (`email`),
  UNIQUE KEY `tb_user_un_nickname` (`nickname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='유저 목록';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_user`
--

LOCK TABLES `tb_user` WRITE;
/*!40000 ALTER TABLE `tb_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `tb_user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-09-12 22:18:49