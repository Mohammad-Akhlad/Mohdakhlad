

-- MySQL dump 10.13  Distrib 8.0.33, for Linux (x86_64)
--
-- Host: localhost    Database: jdbc_video
-- ------------------------------------------------------
-- Server version	8.0.33-0ubuntu0.20.04.2



--
-- Current Database: `jdbc_video`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `jdbc_video` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `jdbc_video`;

--
-- Table structure for table `customer`
--

CREATE TABLE `customer` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `people`
--


CREATE TABLE `people` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `person`
--


CREATE TABLE `person` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `age` int DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Table structure for table `your_table_name`
--


CREATE TABLE `your_table_name` (
  `id` int NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `city` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping events for database 'jdbc_video'
--

ALTER TABLE customer
ADD COLUMN created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP;


ALTER TABLE customer
ADD COLUMN last_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;



ALTER TABLE customer ADD COLUMN Date_of_Birth DATE;

ALTER TABLE customer ADD COLUMN Phone_no VARCHAR(20);


ALTER TABLE customer
ADD CONSTRAINT uk_customer_phone_no UNIQUE (Phone_no);

--
-- Dumping routines for database 'jdbc_video'
--

-- Dump completed on 2023-06-20 17:41:10

