-- MySQL dump 10.13  Distrib 5.6.23, for Win64 (x86_64)
--
-- Host: localhost    Database: test
-- ------------------------------------------------------
-- Server version	5.6.24-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `txm_message`
--

DROP TABLE IF EXISTS `txm_message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `txm_message` (
  `txm_message_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `linked_message_id` bigint(20) DEFAULT NULL,
  `linked_message_relation` varchar(12) DEFAULT NULL,
  `sender_host_name` varchar(128) NOT NULL,
  `sender_process_id` varchar(128) NOT NULL,
  `sender_name` varchar(128) DEFAULT NULL,
  `content_reference` varchar(128) DEFAULT NULL,
  `event_name` varchar(128) NOT NULL,
  `payload_type` varchar(128) NOT NULL,
  `acting_user` varchar(128) DEFAULT NULL,
  `trade_id_group_id` bigint(20) NOT NULL,
  `source` varchar(32) NOT NULL,
  `creation_timestamp` datetime NOT NULL,
  `business_line` varchar(12) DEFAULT NULL,
  `xml_content_id` bigint(20) DEFAULT NULL,
  `transport_message_id` varchar(128) DEFAULT NULL,
  `region` varchar(20) DEFAULT NULL,
  `transport_timestamp` datetime DEFAULT NULL,
  PRIMARY KEY (`txm_message_id`),
  KEY `trade_id_group_id3_idx` (`trade_id_group_id`),
  KEY `xml_content_id_1_idx` (`xml_content_id`),
  CONSTRAINT `trade_id_group_id3` FOREIGN KEY (`trade_id_group_id`) REFERENCES `trade_id_group` (`trade_id_group_id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `xml_content_id_1` FOREIGN KEY (`xml_content_id`) REFERENCES `xml_content` (`xml_content_id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-04-23 16:26:48
