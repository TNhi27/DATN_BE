-- MySQL dump 10.13  Distrib 8.0.27, for Win64 (x86_64)
--
-- Host: localhost    Database: datn
-- ------------------------------------------------------
-- Server version	8.0.27

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
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `admin` (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `sdt` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `fullname` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `veryfy` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdate` date DEFAULT NULL,
  `image` text COLLATE utf8_unicode_ci,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admin`
--

LOCK TABLES `admin` WRITE;
/*!40000 ALTER TABLE `admin` DISABLE KEYS */;
INSERT INTO `admin` VALUES ('admin1','12345','0975837193','admin@gmail.com',_binary '','Lưu Tá Tá','Kien Giang','Nam','09458','2021-10-30',NULL),('admin2','12345','0975837193','admin@gmail.com',_binary '','Tá Lưu Lưu','Kien Giang','Nam','09458','2021-10-30',NULL),('admin3','12345','0975837193','admin@gmail.com',_binary '','Tá Lưu Tá','Kien Giang','Nam','09458','2021-10-30',NULL);
/*!40000 ALTER TABLE `admin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `brand`
--

DROP TABLE IF EXISTS `brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idcate` varchar(45) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idcate` (`idcate`),
  CONSTRAINT `brand_ibfk_1` FOREIGN KEY (`idcate`) REFERENCES `category` (`idcate`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `brand`
--

LOCK TABLES `brand` WRITE;
/*!40000 ALTER TABLE `brand` DISABLE KEYS */;
INSERT INTO `brand` VALUES (6,'Owen','TTN'),(7,'Top4man','TTN'),(8,'4men','TTN'),(9,'May 10','TTN'),(10,'NEM fashion','THN'),(11,'Seven AM','THN'),(12,'K&K Fashion','THN'),(13,'JUNO','GIAY'),(14,'Biti\'s','GIAY'),(15,'MWC Shop','GIAY'),(16,'Ananas','GIAY'),(17,'LG','PK'),(18,'Không có thương hiệu','NONE');
/*!40000 ALTER TABLE `brand` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `idcate` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `typename` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `img` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `parent` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `lv` int DEFAULT NULL,
  PRIMARY KEY (`idcate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES ('GIAY','Giày dép','data:image/jpeg;base64,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',NULL,NULL),('GIAY1','Giày nam',NULL,'GIAY',NULL),('GIAY2','Giày nữ',NULL,'GIAY',NULL),('NONE','Khong xac dinh',NULL,'NONE',NULL),('PK','Phụ kiện điện tử','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTXVtaLJAFqTLBh8OerTXKYJSL36JOQAYUeog&usqp=CAU',NULL,NULL),('PK1','Tai nghe có dây',NULL,'TEC',NULL),('PK2','Tai nghe không dây',NULL,'PK',NULL),('PK3','Củ sạc điện thoại',NULL,'PK',NULL),('PK4','Dây sạc',NULL,'PK',NULL),('PK5','Tay cầm',NULL,'PK',NULL),('THN','Thời Trang Nữ','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSrusq2hl3ZBJwKqbZLj7BQIGf_qBEkGV8ZPA&usqp=CAU',NULL,NULL),('THN1','Áo so mi nữ',NULL,'THN',NULL),('THN2','Đồ ngủ',NULL,'THN',NULL),('THN3','Quần tây',NULL,'THN',NULL),('THN4','Áo khoác nữ',NULL,'THN',NULL),('THN5','Quân thun nữ',NULL,'THN',NULL),('TTN','Thời Trang Nam','data:image/jpeg;base64,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',NULL,NULL),('TTN1','Áo Sơ Mi Nam',NULL,'TTN',NULL),('TTN2','Quần tây',NULL,'TTN',NULL),('TTN3','Áo khoác nam',NULL,'TTN',NULL),('TTN4','Quần thun',NULL,'TTN',NULL),('TTN5','Áo thun',NULL,'TTN',NULL);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `comments`
--

DROP TABLE IF EXISTS `comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `comments` (
  `idcmt` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idpro` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `star` int DEFAULT NULL,
  `content` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `idorder` int DEFAULT NULL,
  PRIMARY KEY (`idcmt`),
  KEY `username` (`username`),
  KEY `idpro` (`idpro`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`username`) REFERENCES `ctv` (`username`),
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`idpro`) REFERENCES `products` (`idpro`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `comments`
--

LOCK TABLES `comments` WRITE;
/*!40000 ALTER TABLE `comments` DISABLE KEYS */;
INSERT INTO `comments` VALUES (1,'cr7dy','s3333',4,'Gia tot, phan hoi nhanh','2021-11-08 00:00:00',NULL),(2,'cr7dy','SP134',5,'Gia tot, phan hoi nhanh','2021-11-18 00:00:00',NULL),(3,'cr7dy','SP01',1,'Gia tot, phan hoi nhanh','2021-10-18 00:00:00',NULL),(4,'cr7dy','SP12',5,'Gia tot, phan hoi nhanh','2021-11-01 00:00:00',NULL),(5,'cr7dy','SP124',3,'Gia tot, phan hoi nhanh','2021-11-02 00:00:00',NULL),(6,'cr7dy','SP13',5,'Gia tot, phan hoi nhanh','2021-11-03 00:00:00',NULL),(7,'cr7dy','SP134',4,'Gia tot, phan hoi nhanh','2021-11-04 00:00:00',NULL),(8,'cr7dy','s3333',4,'Gia tot, phan hoi nhanh','2021-11-05 00:00:00',NULL),(9,'leo10','SP134',4,'Gia tot, phan hoi nhanh','2021-11-06 00:00:00',NULL),(10,'leo10','SP01',1,'Gia tot, phan hoi nhanh','2021-11-07 00:00:00',NULL),(11,'leo10','SP12',5,'Gia tot, phan hoi nhanh','2021-11-08 00:00:00',NULL),(12,'lewan','SP124',4,'Gia tot, phan hoi nhanh','2021-11-09 00:00:00',NULL),(13,'lewan','SP13',3,'Gia tot, phan hoi nhanh','2021-11-10 00:00:00',NULL),(14,'lewan','SP134',5,'Gia tot, phan hoi nhanh','2021-11-08 00:00:00',NULL),(15,'leo10','tuilaao',4,'hello 4 sao',NULL,21),(16,'leo10','S3333',4,'rat la ok','2021-11-26 22:24:31',22),(17,'leo10','S3333',4,'OK','2021-11-27 14:07:02',25);
/*!40000 ALTER TABLE `comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ctv`
--

DROP TABLE IF EXISTS `ctv`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ctv` (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `sdt` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `fullname` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `verify` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdate` date DEFAULT NULL,
  `image` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `money` int DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ctv`
--

LOCK TABLES `ctv` WRITE;
/*!40000 ALTER TABLE `ctv` DISABLE KEYS */;
INSERT INTO `ctv` VALUES ('cr7dy','0707','095768374','cr7dy@gmail.com',_binary '','Bảy Việt Vị','Kiên Giang','Nam',NULL,'2021-10-30','https://images.pexels.com/photos/9813701/pexels-photo-9813701.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500',10939000),('jack97','1010','095768374','jack97@gmail.com',_binary '','Chịnh Chần Hương Tứng','Bến Tre','Nam',NULL,'2021-10-30','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSqrH7n7B9jZpesW3nKZeNtzmkMdAFhG9Igeg&usqp=CAU',5000000),('leo10','1010','095768374','leo10@gmail.com',_binary '','Lưu Mét Sisss','Kiên Giangggg','Nam',NULL,'2021-10-30','http://res.cloudinary.com/duan2021/image/upload/v1637996870/avatar/vwbogctj413kxrdh5xtq.jpg',33290),('lewan','1010','095768374','lewan@gmail.com',_binary '','Lê Quang Đô Ki','Kiên Giang','Nam',NULL,'2021-10-30','https://images.pexels.com/photos/5422753/pexels-photo-5422753.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500',10939000);
/*!40000 ALTER TABLE `ctv` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `details`
--

DROP TABLE IF EXISTS `details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `details` (
  `iddetails` int NOT NULL AUTO_INCREMENT,
  `qty` int DEFAULT NULL,
  `idpro` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idorder` int DEFAULT NULL,
  `revenue` int DEFAULT NULL,
  PRIMARY KEY (`iddetails`),
  KEY `idpro` (`idpro`),
  KEY `idorder` (`idorder`),
  CONSTRAINT `details_ibfk_1` FOREIGN KEY (`idpro`) REFERENCES `products` (`idpro`),
  CONSTRAINT `details_ibfk_2` FOREIGN KEY (`idorder`) REFERENCES `orders` (`idorder`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `details`
--

LOCK TABLES `details` WRITE;
/*!40000 ALTER TABLE `details` DISABLE KEYS */;
INSERT INTO `details` VALUES (1,12,'s3333',1,NULL),(2,6,'s3333',2,NULL),(3,2,'s3333',4,NULL),(4,3,'sp1',5,NULL),(5,2,'SP1',3,NULL),(6,1,'SP01',7,NULL),(7,2,'SP1',8,NULL),(8,1,'SP01',8,NULL),(9,2,'SP1',9,NULL),(10,1,'SP01',9,NULL),(11,2,'SP1',10,NULL),(12,1,'SP01',10,NULL),(13,2,'SP1',11,NULL),(14,1,'SP01',11,NULL),(15,1,'S3333',11,NULL),(16,2,'SP1',12,NULL),(17,1,'SP01',12,NULL),(18,1,'S3333',12,NULL),(19,1,'S3333',13,NULL),(20,2,'S3333',13,NULL),(21,1,'S3333',14,NULL),(22,2,'S3333',14,NULL),(23,1,'S3333',15,NULL),(24,2,'tuilaao',16,NULL),(25,2,'SP1',17,NULL),(26,1,'S3333',17,NULL),(27,2,'tuilaao',18,NULL),(28,1,'S3333',19,NULL),(29,1,'SP1',19,NULL),(30,1,'tuilaao',20,NULL),(31,1,'tuilaao',21,NULL),(32,2,'S3333',22,1234),(33,1,'SP1',23,500),(34,1,'SP1',24,500),(35,1,'S3333',24,1234),(36,2,'S3333',25,1234),(37,1,'SP134',26,500),(38,3,'SP13',27,500),(39,2,'SP134',28,500),(40,1,'SP134',29,500);
/*!40000 ALTER TABLE `details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `followsell`
--

DROP TABLE IF EXISTS `followsell`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `followsell` (
  `followid` int NOT NULL AUTO_INCREMENT,
  `ncc` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `ctv` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`followid`),
  KEY `ncc` (`ncc`),
  KEY `ctv` (`ctv`),
  CONSTRAINT `followsell_ibfk_1` FOREIGN KEY (`ncc`) REFERENCES `ncc` (`username`),
  CONSTRAINT `followsell_ibfk_2` FOREIGN KEY (`ctv`) REFERENCES `ctv` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `followsell`
--

LOCK TABLES `followsell` WRITE;
/*!40000 ALTER TABLE `followsell` DISABLE KEYS */;
INSERT INTO `followsell` VALUES (6,'congvinh','lewan','2021-11-18'),(93,NULL,NULL,NULL),(96,NULL,NULL,NULL),(98,'congvinh','leo10','2021-11-28');
/*!40000 ALTER TABLE `followsell` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `infobanks`
--

DROP TABLE IF EXISTS `infobanks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `infobanks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bankname` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `banknumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `isctv` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `infobanks`
--

LOCK TABLES `infobanks` WRITE;
/*!40000 ALTER TABLE `infobanks` DISABLE KEYS */;
INSERT INTO `infobanks` VALUES (1,'BIDV','320290429042','leo10',_binary '');
/*!40000 ALTER TABLE `infobanks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ncc`
--

DROP TABLE IF EXISTS `ncc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ncc` (
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `sdt` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `email` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `fullname` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `sex` varchar(4) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `verify` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdate` date DEFAULT NULL,
  `money` int DEFAULT NULL,
  `nccname` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ncclogo` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `city` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idghn` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ncc`
--

LOCK TABLES `ncc` WRITE;
/*!40000 ALTER TABLE `ncc` DISABLE KEYS */;
INSERT INTO `ncc` VALUES ('aomacanada','1234','09658478347','info@gmail.com',_binary '','Lưu  Tá','Kiên Giang','Nam','452','2021-10-30',2000,'BANGIARE','https://images.pexels.com/photos/9831840/pexels-photo-9831840.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500','Bán vì đam mê','HCM',NULL),('congvinh','1234','0329711724','info@gmail.com',_binary '','Kông Zing','TP.HCM','Nam','452','2021-10-30',2112,'DINH LŨ','https://images.pexels.com/photos/9831840/pexels-photo-9831840.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500','Bán vì đam mê','HCM','83994'),('ncc1','1234','09658478347','info@gmail.com',_binary '',' Trường Tá','Kiên Giang','Nam','452','2021-10-30',2000,'ODAYRE','https://images.pexels.com/photos/9831840/pexels-photo-9831840.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500','Bán vì đam mê','HCM',NULL),('ncc4','1234','09658478347','info@gmail.com',_binary '','Lưu Trường Tá','Kiên Giang','Nam','452','2021-10-30',2000,'BANNHA','https://images.pexels.com/photos/9831840/pexels-photo-9831840.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500','Bán vì đam mê','CAN THO',NULL),('thuytien','1234','09658478347','info@gmail.com',_binary '','Thỉ Tiêng','TP.HCM','Nam','452','2021-10-30',2000,'TIÊN LŨ','https://images.pexels.com/photos/9831840/pexels-photo-9831840.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500','Bán vì đam mê','CAN THO',NULL);
/*!40000 ALTER TABLE `ncc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `idorder` int NOT NULL AUTO_INCREMENT,
  `dateorder` datetime DEFAULT NULL,
  `total` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `address` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `customer` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sdtcustomer` varchar(15) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment` int DEFAULT NULL,
  `idctv` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idncc` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `order_code` varchar(200) COLLATE utf8_unicode_ci DEFAULT NULL,
  `huyen` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `xa` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `datefinish` date DEFAULT NULL,
  PRIMARY KEY (`idorder`),
  KEY `idctv` (`idctv`),
  KEY `idncc` (`idncc`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`idctv`) REFERENCES `ctv` (`username`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`idncc`) REFERENCES `ncc` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=30 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'2021-11-15 00:00:00',1200,0,'diachi','khach hang','09485093',1300,'leo10','thuytien',NULL,'1901','610503',NULL),(2,'2021-11-15 00:00:00',1100,1,'diachi','khach hang','09485093',1300,'leo10','thuytien',NULL,'1901','610503',NULL),(3,'2021-11-15 00:00:00',1500,2,'diachi','khach hang','09485093',1300,'leo10','thuytien',NULL,'1901','610503',NULL),(4,'2021-11-15 00:00:00',12670,3,'diachi','khach hang','09485093',1300,'leo10','thuytien',NULL,'1901','610503',NULL),(5,'2021-11-15 00:00:00',12300,4,'diachi','khach hang','09485093',1300,'leo10','thuytien',NULL,'1901','610503',NULL),(6,'2021-11-15 00:00:00',12660,1,'diachi','khach hang','09485093',1300,'jack97','thuytien',NULL,'1901','610503',NULL),(7,'2021-11-18 22:25:28',1243,0,'24/1 tran chien Xã La Bằng - Huyện Đại Từ - Thái Nguyên','Luu Truong Ta','0971519342',0,'leo10','congvinh',NULL,'1901','610503',NULL),(8,'2021-11-18 22:27:39',1243,5,'24/1 tran chien Xã La Bằng - Huyện Đại Từ - Thái Nguyên','Luu Truong Ta','0971519342',0,'leo10','congvinh',NULL,'1901','610503',NULL),(9,'2021-11-18 22:35:35',1243,5,'24/1 tran chien Xã La Bằng - Huyện Đại Từ - Thái Nguyên','Luu Truong Ta','0971519342',0,'leo10','congvinh',NULL,'1901','610503',NULL),(10,'2021-11-18 22:35:44',1243,3,'24/1 tran chien Xã La Bằng - Huyện Đại Từ - Thái Nguyên','Luu Truong Ta','0971519342',0,'leo10','congvinh',NULL,'1901','610503',NULL),(11,'2021-11-18 22:36:32',1287,4,'24/1 tran chien Xã La Bằng - Huyện Đại Từ - Thái Nguyên','Luu Truong Ta','0971519342',1234,'leo10','congvinh',NULL,'1901','610503',NULL),(12,'2021-11-18 22:43:33',1287,4,'24/1 tran chien Xã La Bằng - Huyện Đại Từ - Thái Nguyên','Luu Truong Ta','0971519342',1234,'leo10','congvinh',NULL,'1901','610503',NULL),(13,'2021-11-19 15:53:48',132,4,' Xã Long Trị A - Thị xã Long Mỹ - Hậu Giang','Luu Truong Ta','0971519342',2480,'leo10','congvinh',NULL,'1901','610503610503',NULL),(14,'2021-11-19 15:53:50',132,4,' Xã Long Trị A - Thị xã Long Mỹ - Hậu Giang','Luu Truong Ta','0971519342',2480,'leo10','congvinh',NULL,'1901','610503',NULL),(15,'2021-11-19 16:07:52',44,4,' Xã Vĩnh Bình - Huyện Hòa Bình - Bạc Liêu','Luu Truong Ta','0971519342',12,'leo10','congvinh',NULL,'1901','610503',NULL),(16,'2021-11-20 15:36:14',1268,4,'24/1 tran chien le binh Xã Như Khuê - Huyện Lộc Bình - Lạng Sơn','truongta','0971519342',22,'leo10','aomacanada',NULL,'1901','610503',NULL),(17,'2021-11-22 21:58:52',734,4,'24/1 Xã Mẫu Sơn - Huyện Lộc Bình - Lạng Sơn','Luu Truong Ta','0971519342',2234,'leo10','congvinh',NULL,'1901','610503',NULL),(18,'2021-11-23 14:11:29',1268,0,' Xã Vĩnh Hưng A - Huyện Vĩnh Lợi - Bạc Liêu','Luu Truong Ta','0971519342',2000,'leo10','aomacanada',NULL,'1901','610503',NULL),(19,'2021-11-23 16:19:28',389,2,' Phường 3 - Quận 11 - Hồ Chí Minh','Luu Truong Ta','0971519342',1734,'leo10','congvinh','Z89Z7','1901','610503',NULL),(20,'2021-11-23 16:21:16',634,0,' Xã Khánh Yên Hạ - Huyện Văn Bàn - Lào Cai','Luu Truong Ta','0971519342',1000,'leo10','aomacanada',NULL,'1901','610503',NULL),(21,'2021-11-23 16:22:16',634,0,' Xã Cao Thượng - Huyện Tân Yên - Bắc Giang','Luu Truong Ta','0971519342',1000,'leo10','aomacanada',NULL,'1901','610503',NULL),(22,'2021-11-26 22:24:20',88,2,'24/1 Thị trấn Kinh Cùng - Huyện Phụng Hiệp - Hậu Giang','Tran Dan','0971514343',2468,'leo10','congvinh','Z89ZF','1901','610503',NULL),(23,'2021-11-26 23:00:10',345,2,' Xã Đông Thới - Huyện Cái Nước - Cà Mau','Tran Dan','0971514343',500,'leo10','congvinh','Z892Y','1901','610503',NULL),(24,'2021-11-27 13:00:10',389,2,'223 Dinh Tien Hoang Phường 2 - Quận 8 - Hồ Chí Minh','Nguyen Van A','0971519342',1734,'leo10','congvinh','Z89ZL','1450','20802',NULL),(25,'2021-11-27 14:06:44',88,2,'24/1 Xã Thới Đông - Huyện Cờ Đỏ - Cần Thơ','Luu Truong Ta','0971519342',2468,'leo10','congvinh','Z89ZD','3150','550605',NULL),(26,'2021-11-27 21:18:19',112,4,'24/1 Phường Cải Đan - Thành Phố Sông Công - Thái Nguyên','Nguyen Van A','0971519342',500,'leo10','congvinh',NULL,'1684','120202',NULL),(27,'2021-11-27 21:26:11',1059,2,' Phường Chính Gián - Quận Thanh Khê - Đà Nẵng','Nguyen Van A','0971519342',1500,'leo10','congvinh','Z89ZG','1527','40202',NULL),(28,'2021-11-28 16:07:54',224,5,'24/1 tran chien Phường Lê Bình - Quận Cái Răng - Cần Thơ','Test va to','0971519342',1000,'leo10','congvinh',NULL,'1574','550304',NULL),(29,'2021-11-28 17:08:34',112,5,' Xã Viên An Đông - Huyện Ngọc Hiển - Cà Mau','Nguyen Van A','0971519342',500,'leo10','congvinh','Z895V','2186','610707','2021-11-28');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `post`
--

DROP TABLE IF EXISTS `post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `post` (
  `idpost` int NOT NULL AUTO_INCREMENT,
  `title` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `image` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idpost`),
  KEY `username` (`username`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`username`) REFERENCES `admin` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `post`
--

LOCK TABLES `post` WRITE;
/*!40000 ALTER TABLE `post` DISABLE KEYS */;
/*!40000 ALTER TABLE `post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `idpro` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `pricectv` int DEFAULT NULL,
  `createdate` date DEFAULT NULL,
  `tags` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `qty` int NOT NULL,
  `dvt` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `image0` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `image1` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `image2` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `image3` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `origin` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idcate` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idncc` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `brand` int DEFAULT NULL,
  PRIMARY KEY (`idpro`),
  KEY `idcate` (`idcate`),
  KEY `idncc` (`idncc`),
  KEY `brand` (`brand`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`idcate`) REFERENCES `category` (`idcate`),
  CONSTRAINT `products_ibfk_2` FOREIGN KEY (`idncc`) REFERENCES `ncc` (`username`),
  CONSTRAINT `products_ibfk_3` FOREIGN KEY (`brand`) REFERENCES `brand` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('2f724bfe-7cc5-429d-a78b-9e176fc3a2e6','Lưu Trường Tá','',2,'2021-11-25','',2,'Doi','','','','','Quảng Châu-TQ','TTN4','thuytien',_binary '\0',9),('418a4bad-bcc7-4f07-955a-f1af823c56c1','Lưu Trường Tá','<p><i><strong>doi giay ghi chu*</strong></i></p>',2,'2021-11-25','doi giay',2,'Doi','http://res.cloudinary.com/duan2021/image/upload/v1637805127/image_products/fbiu5lr6luy7m8drioct.jpg','http://res.cloudinary.com/duan2021/image/upload/v1637805129/image_products/iyyudjsmprvnfeg8kkhk.jpg','http://res.cloudinary.com/duan2021/image/upload/v1637805132/image_products/pwhqthbqzaftwglytquq.jpg','','Quảng Châu-TQ','GIAY2','thuytien',_binary '\0',13),('72d9f565-6124-4652-816d-4aa050660102','Lưu Trường Tá','<p><i><strong>doi giay ghi chu*</strong></i></p>',2,'2021-11-25','doi giay',3,'Doi','http://res.cloudinary.com/duan2021/image/upload/v1637805127/image_products/fbiu5lr6luy7m8drioct.jpg','http://res.cloudinary.com/duan2021/image/upload/v1637805129/image_products/iyyudjsmprvnfeg8kkhk.jpg','http://res.cloudinary.com/duan2021/image/upload/v1637805132/image_products/pwhqthbqzaftwglytquq.jpg','','Quảng Châu-TQ','GIAY1','thuytien',_binary '\0',13),('9a0845bd-d9cd-4056-8916-35a7122fc8eb','demo','',3,'2021-11-25','adad',0,'','','','','','','THN5','thuytien',_binary '\0',12),('a8d7bc81-b51d-42ae-abb0-2874db3e5398','truongta','',1,'2021-11-25','',3,'Doi','','','','','Hàn Quốc','GIAY1','thuytien',_binary '\0',13),('ae27b5f1-5707-405e-8b68-f69ec581a4d5','Lưu Trường Tá','<p><i><strong>doi giay ghi chu*</strong></i></p>',2,'2021-11-25','doi giay',2,'Doi','http://res.cloudinary.com/duan2021/image/upload/v1637805127/image_products/fbiu5lr6luy7m8drioct.jpg','http://res.cloudinary.com/duan2021/image/upload/v1637805129/image_products/iyyudjsmprvnfeg8kkhk.jpg','http://res.cloudinary.com/duan2021/image/upload/v1637805132/image_products/pwhqthbqzaftwglytquq.jpg','','Quảng Châu-TQ','GIAY2','thuytien',_binary '\0',13),('bf86a9f0-02d3-466d-aed4-7c61c5e45a21','Trun','',2,'2021-11-25','doi giay',2,'Doi','','','','','Hàn Quốc','TTN2','thuytien',_binary '\0',8),('combo1','Combo vintage 01','Combo. Chất: KAKI. Size: M L',350000,'2021-01-01','Combo',69,'bo','https://scontent.fsgn5-11.fna.fbcdn.net/v/t39.30808-6/250895874_3252754674848203_3594170768663439692_n.jpg?_nc_cat=111&ccb=1-5&_nc_sid=0debeb&_nc_ohc=FgNz3tyqspAAX90yLz5&tn=pOeGPCXi5VSJraPu&_nc_ht=scontent.fsgn5-11.fna&oh=61aaf7f5400d37b04dcd77b8e502773c&oe=61A07A46','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/250128065_3252754064848264_4390455657811393427_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=fvASvr-TQdkAX-u1zlc&_nc_ht=scontent.fsgn5-8.fna&oh=c8f2d663e370203497da61e0dbc0b0ba&oe=61A0EF52','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252375442_3252754561514881_1821899442364522729_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=0debeb&_nc_ohc=CIU8K5oi5ckAX9a6106&_nc_ht=scontent.fsgn5-10.fna&oh=3e75f3528ca976038b79cc5c17dab916&oe=61A08EE5','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/251549022_3252754401514897_8891667630020152288_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=6TgIl0RSioEAX-Pj0GZ&_nc_ht=scontent.fsgn5-10.fna&oh=1e79038adaf7d9dad7d2d8cce6f4e59b&oe=61A1A7F2','QuangChau','TTN','congvinh',_binary '\0',8),('combo2','Combo vintage 01','Combo. Chất: KAKI. Size: M L',350000,'2021-01-01','Combo',69,'bo','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/250128065_3252754064848264_4390455657811393427_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=fvASvr-TQdkAX-u1zlc&_nc_ht=scontent.fsgn5-8.fna&oh=c8f2d663e370203497da61e0dbc0b0ba&oe=61A0EF52','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/250128065_3252754064848264_4390455657811393427_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=fvASvr-TQdkAX-u1zlc&_nc_ht=scontent.fsgn5-8.fna&oh=c8f2d663e370203497da61e0dbc0b0ba&oe=61A0EF52','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252375442_3252754561514881_1821899442364522729_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=0debeb&_nc_ohc=CIU8K5oi5ckAX9a6106&_nc_ht=scontent.fsgn5-10.fna&oh=3e75f3528ca976038b79cc5c17dab916&oe=61A08EE5','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/251549022_3252754401514897_8891667630020152288_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=6TgIl0RSioEAX-Pj0GZ&_nc_ht=scontent.fsgn5-10.fna&oh=1e79038adaf7d9dad7d2d8cce6f4e59b&oe=61A1A7F2','QuangChau','TTN','congvinh',_binary '\0',8),('combo3','Combo vintage 01','Combo. Chất: KAKI. Size: M L',350000,'2021-01-01','Combo',69,'bo','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252375442_3252754561514881_1821899442364522729_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=0debeb&_nc_ohc=CIU8K5oi5ckAX9a6106&_nc_ht=scontent.fsgn5-10.fna&oh=3e75f3528ca976038b79cc5c17dab916&oe=61A08EE5','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/250128065_3252754064848264_4390455657811393427_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=fvASvr-TQdkAX-u1zlc&_nc_ht=scontent.fsgn5-8.fna&oh=c8f2d663e370203497da61e0dbc0b0ba&oe=61A0EF52','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252375442_3252754561514881_1821899442364522729_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=0debeb&_nc_ohc=CIU8K5oi5ckAX9a6106&_nc_ht=scontent.fsgn5-10.fna&oh=3e75f3528ca976038b79cc5c17dab916&oe=61A08EE5','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/251549022_3252754401514897_8891667630020152288_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=6TgIl0RSioEAX-Pj0GZ&_nc_ht=scontent.fsgn5-10.fna&oh=1e79038adaf7d9dad7d2d8cce6f4e59b&oe=61A1A7F2','QuangChau','TTN','congvinh',_binary '\0',8),('combo4','Combo vintage 01','Combo. Chất: KAKI. Size: M L',350000,'2021-01-01','Combo',69,'bo','https://scontent.fsgn5-4.fna.fbcdn.net/v/t39.30808-6/250853011_3252754271514910_2185788116855229394_n.jpg?_nc_cat=102&_nc_rgb565=1&ccb=1-5&_nc_sid=0debeb&_nc_ohc=qdnYza8FaHYAX8fx0Bd&tn=pOeGPCXi5VSJraPu&_nc_ht=scontent.fsgn5-4.fna&oh=2df73f4d0bb32c835f032da31c138caa&oe=61A07319','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/250128065_3252754064848264_4390455657811393427_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=fvASvr-TQdkAX-u1zlc&_nc_ht=scontent.fsgn5-8.fna&oh=c8f2d663e370203497da61e0dbc0b0ba&oe=61A0EF52','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252375442_3252754561514881_1821899442364522729_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=0debeb&_nc_ohc=CIU8K5oi5ckAX9a6106&_nc_ht=scontent.fsgn5-10.fna&oh=3e75f3528ca976038b79cc5c17dab916&oe=61A08EE5','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/251549022_3252754401514897_8891667630020152288_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=6TgIl0RSioEAX-Pj0GZ&_nc_ht=scontent.fsgn5-10.fna&oh=1e79038adaf7d9dad7d2d8cce6f4e59b&oe=61A1A7F2','QuangChau','TTN','congvinh',_binary '\0',8),('combo5','Combo vintage 01','Combo. Chất: KAKI. Size: M L',350000,'2021-01-01','Combo',69,'bo','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/251549022_3252754401514897_8891667630020152288_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=6TgIl0RSioEAX-Pj0GZ&_nc_ht=scontent.fsgn5-10.fna&oh=1e79038adaf7d9dad7d2d8cce6f4e59b&oe=61A1A7F2','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/250128065_3252754064848264_4390455657811393427_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=fvASvr-TQdkAX-u1zlc&_nc_ht=scontent.fsgn5-8.fna&oh=c8f2d663e370203497da61e0dbc0b0ba&oe=61A0EF52','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252375442_3252754561514881_1821899442364522729_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=0debeb&_nc_ohc=CIU8K5oi5ckAX9a6106&_nc_ht=scontent.fsgn5-10.fna&oh=3e75f3528ca976038b79cc5c17dab916&oe=61A08EE5','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/251549022_3252754401514897_8891667630020152288_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=6TgIl0RSioEAX-Pj0GZ&_nc_ht=scontent.fsgn5-10.fna&oh=1e79038adaf7d9dad7d2d8cce6f4e59b&oe=61A1A7F2','QuangChau','TTN','congvinh',_binary '',8),('combo6','Combo vintage 01','Combo. Chất: KAKI. Size: M L',350000,'2021-01-01','Combo',69,'bo','https://scontent.fsgn5-9.fna.fbcdn.net/v/t39.30808-6/251601238_3252754284848242_773999783702676655_n.jpg?_nc_cat=105&ccb=1-5&_nc_sid=0debeb&_nc_ohc=npXbr-fuRuYAX8iS3Ez&tn=pOeGPCXi5VSJraPu&_nc_ht=scontent.fsgn5-9.fna&oh=683760eee5fbe901471b2ea45ceb78e4&oe=61A07036','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/250128065_3252754064848264_4390455657811393427_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=fvASvr-TQdkAX-u1zlc&_nc_ht=scontent.fsgn5-8.fna&oh=c8f2d663e370203497da61e0dbc0b0ba&oe=61A0EF52','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252375442_3252754561514881_1821899442364522729_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=0debeb&_nc_ohc=CIU8K5oi5ckAX9a6106&_nc_ht=scontent.fsgn5-10.fna&oh=3e75f3528ca976038b79cc5c17dab916&oe=61A08EE5','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/251549022_3252754401514897_8891667630020152288_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=6TgIl0RSioEAX-Pj0GZ&_nc_ht=scontent.fsgn5-10.fna&oh=1e79038adaf7d9dad7d2d8cce6f4e59b&oe=61A1A7F2','QuangChau','TTN','congvinh',_binary '\0',8),('combo7','Combo vintage 01','Combo. Chất: KAKI. Size: M L',350000,'2021-01-01','Combo',69,'bo','https://scontent.fsgn5-11.fna.fbcdn.net/v/t39.30808-6/250895874_3252754674848203_3594170768663439692_n.jpg?_nc_cat=111&ccb=1-5&_nc_sid=0debeb&_nc_ohc=FgNz3tyqspAAX90yLz5&tn=pOeGPCXi5VSJraPu&_nc_ht=scontent.fsgn5-11.fna&oh=61aaf7f5400d37b04dcd77b8e502773c&oe=61A07A46','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/250128065_3252754064848264_4390455657811393427_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=fvASvr-TQdkAX-u1zlc&_nc_ht=scontent.fsgn5-8.fna&oh=c8f2d663e370203497da61e0dbc0b0ba&oe=61A0EF52','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252375442_3252754561514881_1821899442364522729_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=0debeb&_nc_ohc=CIU8K5oi5ckAX9a6106&_nc_ht=scontent.fsgn5-10.fna&oh=3e75f3528ca976038b79cc5c17dab916&oe=61A08EE5','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/251549022_3252754401514897_8891667630020152288_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=6TgIl0RSioEAX-Pj0GZ&_nc_ht=scontent.fsgn5-10.fna&oh=1e79038adaf7d9dad7d2d8cce6f4e59b&oe=61A1A7F2','QuangChau','TTN','congvinh',_binary '\0',8),('combo8','Combo vintage 01','Combo. Chất: KAKI. Size: M L',350000,'2021-01-01','Combo',69,'bo','https://scontent.fsgn5-11.fna.fbcdn.net/v/t39.30808-6/250895874_3252754674848203_3594170768663439692_n.jpg?_nc_cat=111&ccb=1-5&_nc_sid=0debeb&_nc_ohc=FgNz3tyqspAAX90yLz5&tn=pOeGPCXi5VSJraPu&_nc_ht=scontent.fsgn5-11.fna&oh=61aaf7f5400d37b04dcd77b8e502773c&oe=61A07A46','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/250128065_3252754064848264_4390455657811393427_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=fvASvr-TQdkAX-u1zlc&_nc_ht=scontent.fsgn5-8.fna&oh=c8f2d663e370203497da61e0dbc0b0ba&oe=61A0EF52','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252375442_3252754561514881_1821899442364522729_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=0debeb&_nc_ohc=CIU8K5oi5ckAX9a6106&_nc_ht=scontent.fsgn5-10.fna&oh=3e75f3528ca976038b79cc5c17dab916&oe=61A08EE5','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/251549022_3252754401514897_8891667630020152288_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=6TgIl0RSioEAX-Pj0GZ&_nc_ht=scontent.fsgn5-10.fna&oh=1e79038adaf7d9dad7d2d8cce6f4e59b&oe=61A1A7F2','QuangChau','TTN','congvinh',_binary '\0',8),('d918ada4-fc0a-498f-b2a9-dba280c6dfae','demo','',2,'2021-11-25','ada',2,'Doi','','','','','','GIAY1','thuytien',_binary '\0',13),('e252e2df-d98f-4f4e-8930-5f2440b95b68','san pham moi ne','',1209000,'2021-11-25','ao thun',3,'Doi','http://res.cloudinary.com/duan2021/image/upload/v1637857242/image_products/y5djk9ahg55ymwhrruyx.jpg','','','','Việt Nam','THN3','thuytien',_binary '',11),('f1c7bcd0-dc9f-4879-9f62-b829e2d546cb','Lưu Trường Tá','<h2>chi tiet san pham</h2><p><i>san pham tot</i></p>',2,'2021-11-25','giay',5,'Doi','','','','','Hàn Quốc','GIAY1','thuytien',_binary '\0',18),('qq','Quần camo 2hand','Quần short camo. Chất: KAKI. Size: M L',150000,'2021-01-01','quan ngan',69,'cai','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/258850681_3292011660922504_556436044484927152_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=3jWGX6puc6wAX9RtRqg&_nc_ht=scontent.fsgn5-8.fna&oh=0269d74dea7297ec99664c9a0db2c84d&oe=61A22F6B','https://scontent.fsgn5-8.fna.fbcdn.net/v/t39.30808-6/258850681_3292011660922504_556436044484927152_n.jpg?_nc_cat=103&ccb=1-5&_nc_sid=0debeb&_nc_ohc=3jWGX6puc6wAX9RtRqg&_nc_ht=scontent.fsgn5-8.fna&oh=0269d74dea7297ec99664c9a0db2c84d&oe=61A22F6B','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/257480474_3292011517589185_382363257502532776_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=8F3Wpgfb9RgAX-GT_mz&tn=pOeGPCXi5VSJraPu&_nc_ht=scontent.fsgn5-10.fna&oh=f809aa3ed08938155fbbc3d7e8c8d873&oe=61A15885','https://scontent.fsgn5-9.fna.fbcdn.net/v/t39.30808-6/258778117_3292011394255864_7156669497686111075_n.jpg?_nc_cat=105&ccb=1-5&_nc_sid=0debeb&_nc_ohc=lGHs0HPEml0AX-Tn2Lt&_nc_ht=scontent.fsgn5-9.fna&oh=b98fca240b080772ea5208853895ca60&oe=61A15283','QuangChau','TTN','congvinh',_binary '',8),('qq1','Quần camo 2hand','Quần short camo. Chất: KAKI. Size: M L',150000,'2021-01-01','quan ngan',69,'cai','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/257480474_3292011517589185_382363257502532776_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=8F3Wpgfb9RgAX-GT_mz&tn=pOeGPCXi5VSJraPu&_nc_ht=scontent.fsgn5-10.fna&oh=f809aa3ed08938155fbbc3d7e8c8d873&oe=61A15885','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/257480474_3292011517589185_382363257502532776_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=8F3Wpgfb9RgAX-GT_mz&tn=pOeGPCXi5VSJraPu&_nc_ht=scontent.fsgn5-10.fna&oh=f809aa3ed08938155fbbc3d7e8c8d873&oe=61A15885','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/257480474_3292011517589185_382363257502532776_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=8F3Wpgfb9RgAX-GT_mz&tn=pOeGPCXi5VSJraPu&_nc_ht=scontent.fsgn5-10.fna&oh=f809aa3ed08938155fbbc3d7e8c8d873&oe=61A15885','https://scontent.fsgn5-9.fna.fbcdn.net/v/t39.30808-6/258778117_3292011394255864_7156669497686111075_n.jpg?_nc_cat=105&ccb=1-5&_nc_sid=0debeb&_nc_ohc=lGHs0HPEml0AX-Tn2Lt&_nc_ht=scontent.fsgn5-9.fna&oh=b98fca240b080772ea5208853895ca60&oe=61A15283','QuangChau','TTN','congvinh',_binary '\0',8),('qq2','Quần camo 2hand','Quần short camo. Chất: KAKI. Size: M L',150000,'2021-01-01','quan ngan',69,'cai','https://scontent.fsgn5-9.fna.fbcdn.net/v/t39.30808-6/258778117_3292011394255864_7156669497686111075_n.jpg?_nc_cat=105&ccb=1-5&_nc_sid=0debeb&_nc_ohc=lGHs0HPEml0AX-Tn2Lt&_nc_ht=scontent.fsgn5-9.fna&oh=b98fca240b080772ea5208853895ca60&oe=61A15283','https://scontent.fsgn5-9.fna.fbcdn.net/v/t39.30808-6/258778117_3292011394255864_7156669497686111075_n.jpg?_nc_cat=105&ccb=1-5&_nc_sid=0debeb&_nc_ohc=lGHs0HPEml0AX-Tn2Lt&_nc_ht=scontent.fsgn5-9.fna&oh=b98fca240b080772ea5208853895ca60&oe=61A15283','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/257480474_3292011517589185_382363257502532776_n.jpg?_nc_cat=110&ccb=1-5&_nc_sid=0debeb&_nc_ohc=8F3Wpgfb9RgAX-GT_mz&tn=pOeGPCXi5VSJraPu&_nc_ht=scontent.fsgn5-10.fna&oh=f809aa3ed08938155fbbc3d7e8c8d873&oe=61A15885','https://scontent.fsgn5-9.fna.fbcdn.net/v/t39.30808-6/258778117_3292011394255864_7156669497686111075_n.jpg?_nc_cat=105&ccb=1-5&_nc_sid=0debeb&_nc_ohc=lGHs0HPEml0AX-Tn2Lt&_nc_ht=scontent.fsgn5-9.fna&oh=b98fca240b080772ea5208853895ca60&oe=61A15283','QuangChau','TTN','congvinh',_binary '\0',8),('qq3','Quần camo dài2','Quần camo. Chất: KAKI. Size: M L',150000,'2021-01-01','quan dai',69,'cai','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252489105_3256803147776689_4750266152117384586_n.jpg?_nc_cat=110&_nc_rgb565=1&ccb=1-5&_nc_sid=0debeb&_nc_ohc=j6N1IqWFIIYAX8Bpeyt&_nc_ht=scontent.fsgn5-10.fna&oh=6620f42c3091fac37b45f32a29c08a01&oe=61A22600','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/252489105_3256803147776689_4750266152117384586_n.jpg?_nc_cat=110&_nc_rgb565=1&ccb=1-5&_nc_sid=0debeb&_nc_ohc=j6N1IqWFIIYAX8Bpeyt&_nc_ht=scontent.fsgn5-10.fna&oh=6620f42c3091fac37b45f32a29c08a01&oe=61A22600','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/253617494_3256805017776502_1608800688475934641_n.jpg?_nc_cat=107&ccb=1-5&_nc_sid=0debeb&_nc_ohc=LYe8yOcn_wQAX8NUDeP&_nc_ht=scontent.fsgn5-10.fna&oh=ef8dfa3ac1d0ab82367ed9099a87273b&oe=61A236B5','https://scontent.fsgn5-10.fna.fbcdn.net/v/t39.30808-6/254301815_3256803227776681_2550982831795818342_n.jpg?_nc_cat=107&_nc_rgb565=1&ccb=1-5&_nc_sid=0debeb&_nc_ohc=94sn4ICIfgsAX_-BjFO&_nc_ht=scontent.fsgn5-10.fna&oh=ab6534ff49fb9f37e062d7c9c0ffbf2d&oe=61A0D6FC','QuangChau','TTN','congvinh',_binary '',8),('S3333','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',44,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','Hàn Quốc','PK1','congvinh',_binary '\0',17),('SP01','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',553,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','Hàn Quốc','PK2','congvinh',_binary '\0',17),('SP1','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',345,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','Hàn Quốc','PK1','congvinh',_binary '\0',17),('SP12','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',234,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','Hàn Quốc','PK1','congvinh',_binary '\0',17),('SP124','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',356,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','Hàn Quốc','PK1','congvinh',_binary '',17),('SP13','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',353,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','Hàn Quốc','TTN1','congvinh',_binary '',17),('SP134','áo Nam siêu đẹp được kiểm hàng - 987','<p>Xốp dán tường giả gạch<strong> 3d Cao Cấp</strong>, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,</p>',112,'2021-10-30','ap thun',239,'Hộp','http://res.cloudinary.com/duan2021/image/upload/v1637996977/image_products/jc0irukgcklmq3tgslg0.jpg','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','VN','TTN2','congvinh',_binary '',18),('SP154','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',790,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','VN','PK2','congvinh',_binary '\0',17),('SP1A','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',1001,'2021-11-26','tai nghe khong day,tai nghe',239,'Hộp','http://res.cloudinary.com/duan2021/image/upload/v1637891715/image_products/ayizdfurzhu6rah6kzcu.jpg','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','http://res.cloudinary.com/duan2021/image/upload/v1637891717/image_products/ry9lrqjvafj1povalckv.jpg','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','VN','PK3','thuytien',_binary '',17),('SP1S','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',906,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','VN','PK3','thuytien',_binary '',17),('SP1Ư','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',500,'2021-10-30','tai nghe,tai nghe khong day',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','VN','GIAY2','thuytien',_binary '',13),('SP1WW','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',445,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','VN','GIAY1','thuytien',_binary '',13),('SP4554','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',1133,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','Hàn Quốc','TTN3','thuytien',_binary '',7),('SPSAKDJ','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',375,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','Hàn Quốc','TTN2','aomacanada',_binary '',8),('tuilaao','Áo Nam Trơn Vải cá sấu loại Thun Lạnh cao cấp Co Giãn 4 Chiều - áo cá','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',634,'2021-10-30','aonam,aonamtron',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','Hàn Quốc','TTN1','aomacanada',_binary '',7),('tuilatainghenek','Tai nghe không dây','Xốp dán tường giả gạch 3d Cao Cấp, kích thước 70*77cm, dày 5mm, đầy đủ màu sắcXốp dán tường giả gạch 3d Cao Cấp,',667,'2021-10-30','tainghe,tainghekhongday',239,'Hộp','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','https://media3.scdn.vn/img4/2021/09_17/4fxDrojHyPIMzXSxxAMt.png','Hàn Quốc','PK1','aomacanada',_binary '',7);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `properties`
--

DROP TABLE IF EXISTS `properties`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `properties` (
  `id` int NOT NULL AUTO_INCREMENT,
  `keyp` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `valuep` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idpro` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idpro` (`idpro`),
  CONSTRAINT `properties_ibfk_1` FOREIGN KEY (`idpro`) REFERENCES `products` (`idpro`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `properties`
--

LOCK TABLES `properties` WRITE;
/*!40000 ALTER TABLE `properties` DISABLE KEYS */;
INSERT INTO `properties` VALUES (11,NULL,NULL,'418a4bad-bcc7-4f07-955a-f1af823c56c1'),(12,'ulala','ulatr','72d9f565-6124-4652-816d-4aa050660102'),(13,'1','1','e252e2df-d98f-4f4e-8930-5f2440b95b68'),(42,'ualtr','adad','SP134'),(43,'jaj','ada','SP134');
/*!40000 ALTER TABLE `properties` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `regi_products`
--

DROP TABLE IF EXISTS `regi_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `regi_products` (
  `idregi` int NOT NULL AUTO_INCREMENT,
  `regidate` datetime DEFAULT NULL,
  `idpro` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idctv` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `price` int DEFAULT NULL,
  PRIMARY KEY (`idregi`),
  KEY `idpro` (`idpro`),
  KEY `idctv` (`idctv`),
  CONSTRAINT `regi_products_ibfk_1` FOREIGN KEY (`idpro`) REFERENCES `products` (`idpro`),
  CONSTRAINT `regi_products_ibfk_2` FOREIGN KEY (`idctv`) REFERENCES `ctv` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `regi_products`
--

LOCK TABLES `regi_products` WRITE;
/*!40000 ALTER TABLE `regi_products` DISABLE KEYS */;
INSERT INTO `regi_products` VALUES (19,'2021-11-10 21:24:30','s3333','lewan',2344),(40,'2021-11-28 16:06:57','SP134','leo10',500);
/*!40000 ALTER TABLE `regi_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `idtran` int NOT NULL AUTO_INCREMENT,
  `type` int DEFAULT NULL,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `value` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `note` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `idbank` int DEFAULT NULL,
  `done` bit(1) DEFAULT NULL,
  PRIMARY KEY (`idtran`),
  KEY `idbank` (`idbank`),
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`idbank`) REFERENCES `infobanks` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,0,'leo10','120000','2021-11-19 00:00:00','nap tien',1,_binary ''),(2,1,'leo10','120000','2021-11-20 00:00:00','nap tien',1,_binary ''),(3,1,'leo10','120000','2021-11-18 00:00:00','nap tien',1,_binary '\0'),(4,0,'leo10','120000','2021-11-20 00:00:00','nap tien',1,_binary '\0');
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-11-28 21:02:53
