CREATE SCHEMA `datn` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;
use datn;

CREATE TABLE `admin` (
   `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
   `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
    `sdt` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
     `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `fullname` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
   `sex` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
    `veryfy` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
     `createdate` date DEFAULT NULL,
  PRIMARY KEY (`username`)
 
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `category` (
  `idcate` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `typename` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `img` text COLLATE utf8_unicode_ci,
  `parent` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idcate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `brand` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idcate` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idcate` (`idcate`),
  CONSTRAINT `brand_ibfk_1` FOREIGN KEY (`idcate`) REFERENCES `category` (`idcate`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `ctv` (
  `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
   `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
    `sdt` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
     `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `fullname` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
   `sex` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
    `veryfy` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
     `createdate` date DEFAULT NULL,
     `image` text COLLATE utf8_unicode_ci DEFAULT NULL,
     `money` int null,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `infobanks` (
  `id` int NOT NULL AUTO_INCREMENT,
  `bankname` varchar(250) COLLATE utf8_unicode_ci DEFAULT NULL,
  `banknumber` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
   `isctv` bit(1)  NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `ncc` (
 `username` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
   `password` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
    `sdt` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
     `email` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `active` bit(1) DEFAULT NULL,
  `fullname` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `address` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
   `sex` varchar(4) COLLATE utf8_unicode_ci DEFAULT NULL,
    `veryfy` varchar(64) COLLATE utf8_unicode_ci DEFAULT NULL,
     `createdate` date DEFAULT NULL,
    
     `money` int null,
 
  `nccname` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `ncclogo` text COLLATE utf8_unicode_ci,
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
 
  PRIMARY KEY (`username`)
 
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `followsell` (
  `followid` int NOT NULL AUTO_INCREMENT,
  `ncc` varchar(50) DEFAULT NULL,
  `ctv` varchar(50) DEFAULT NULL,
  `date` date DEFAULT NULL,
  PRIMARY KEY (`followid`),
  KEY `ncc` (`ncc`),
  KEY `ctv` (`ctv`),
  CONSTRAINT `followsell_ibfk_1` FOREIGN KEY (`ncc`) REFERENCES `ncc` (`username`),
  CONSTRAINT `followsell_ibfk_2` FOREIGN KEY (`ctv`) REFERENCES `ctv` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `orders` (
  `idorder` int NOT NULL AUTO_INCREMENT,
  `dateorder` datetime DEFAULT NULL,
  `total` int DEFAULT NULL,
  `status` int DEFAULT NULL,
  `address` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `customer` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `sdtcustomer` varchar(15) COLLATE utf8_unicode_ci DEFAULT NULL,
  `payment` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idctv` varchar(50) DEFAULT NULL,
  `idncc` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idorder`),
  KEY `idctv` (`idctv`),
  KEY `idncc` (`idncc`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`idctv`) REFERENCES `ctv` (`username`),
  CONSTRAINT `orders_ibfk_2` FOREIGN KEY (`idncc`) REFERENCES `ncc` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `post` (
  `idpost` int NOT NULL AUTO_INCREMENT,
  `title` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `image` text COLLATE utf8_unicode_ci,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`idpost`),
  KEY `username` (`username`),
  CONSTRAINT `post_ibfk_1` FOREIGN KEY (`username`) REFERENCES `admin` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `products` (
  `idpro` varchar(50) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `pricectv` int DEFAULT NULL,
  `createdate` date DEFAULT NULL,
   `tags` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci NULL,
  `qty` int NOT NULL,
  `dvt` varchar(50) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `image0` text COLLATE utf8_unicode_ci,
  `image1` text COLLATE utf8_unicode_ci,
  `image2` text COLLATE utf8_unicode_ci,
  `image3` text COLLATE utf8_unicode_ci,
  `origin` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `idcate` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idncc` varchar(50) DEFAULT NULL,
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

CREATE TABLE `details` (
  `iddetails` int NOT NULL AUTO_INCREMENT,
  `qty` int DEFAULT NULL,
  `idpro` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idorder` int DEFAULT NULL,
  PRIMARY KEY (`iddetails`),
  KEY `idpro` (`idpro`),
  KEY `idorder` (`idorder`),
  CONSTRAINT `details_ibfk_1` FOREIGN KEY (`idpro`) REFERENCES `products` (`idpro`),
  CONSTRAINT `details_ibfk_2` FOREIGN KEY (`idorder`) REFERENCES `orders` (`idorder`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `comments` (
  `idcmt` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idpro` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `star` int DEFAULT NULL,
  `content` varchar(250) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  PRIMARY KEY (`idcmt`),
  KEY `username` (`username`),
  KEY `idpro` (`idpro`),
  CONSTRAINT `comments_ibfk_1` FOREIGN KEY (`username`) REFERENCES `ctv` (`username`),
  CONSTRAINT `comments_ibfk_2` FOREIGN KEY (`idpro`) REFERENCES `products` (`idpro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `properties` (
  `id` int NOT NULL AUTO_INCREMENT,
  `key` varchar(100) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `value` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idpro` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idpro` (`idpro`),
  CONSTRAINT `properties_ibfk_1` FOREIGN KEY (`idpro`) REFERENCES `products` (`idpro`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `regi_products` (
  `idregi` int NOT NULL AUTO_INCREMENT,
  `regidate` datetime DEFAULT NULL,
  `idpro` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `idctv` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`idregi`),
  KEY `idpro` (`idpro`),
  KEY `idctv` (`idctv`),
  CONSTRAINT `regi_products_ibfk_1` FOREIGN KEY (`idpro`) REFERENCES `products` (`idpro`),
  CONSTRAINT `regi_products_ibfk_2` FOREIGN KEY (`idctv`) REFERENCES `ctv` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

CREATE TABLE `transaction` (
  `idtran` int NOT NULL AUTO_INCREMENT,
  `type` int DEFAULT NULL,
  `username` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL,
  `value` varchar(100) COLLATE utf8_unicode_ci DEFAULT NULL,
  `createdate` datetime DEFAULT NULL,
  `note` text CHARACTER SET utf8 COLLATE utf8_unicode_ci,
  `idbank` int DEFAULT NULL,
  `done` bit(1)  NULL,
  PRIMARY KEY (`idtran`),
 
  KEY `idbank` (`idbank`),
 
  CONSTRAINT `transaction_ibfk_2` FOREIGN KEY (`idbank`) REFERENCES `infobanks` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8_unicode_ci;

INSERT INTO category VALUES ('LT', 'Laptop', null, 'TBDT'),
							('PC', 'Máy tính để bàn', null, 'TBDT'),
                            ('XM', 'Xe máy', null, null),
                            ('TV', 'Tivi', null, 'TBDT'),
                            ('TBDT', 'Thiết bị điện tử', null, null)
