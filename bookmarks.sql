-- MySQL dump 10.13  Distrib 5.7.21, for macos10.13 (x86_64)
--
-- Host: localhost    Database: bookmarks
-- ------------------------------------------------------
-- Server version	5.7.21

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
-- Current Database: `bookmarks`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `bookmarks` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `bookmarks`;

--
-- Table structure for table `bookmark`
--

DROP TABLE IF EXISTS `bookmark`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bookmark` (
  `username` varchar(16) NOT NULL,
  `bm_URL` varchar(255) NOT NULL,
  PRIMARY KEY (`username`,`bm_URL`),
  KEY `username` (`username`),
  KEY `bm_URL` (`bm_URL`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookmark`
--

LOCK TABLES `bookmark` WRITE;
/*!40000 ALTER TABLE `bookmark` DISABLE KEYS */;
INSERT INTO `bookmark` VALUES ('dongmeiliang','https://developer.apple.com'),('dongmeiliang','https://github.com'),('dongmeiliang','https://stackoverflow.com'),('dongmeiliang','https://www.apple.com'),('dongmeiliang','https://youtube.com'),('galloway','https://android.com'),('galloway','https://ankit.im'),('galloway','https://digits.com'),('galloway','https://www.apple.com'),('galloway','https://www.bing.com'),('krzysztof','https://amap.com'),('krzysztof','https://gimp.com'),('krzysztof','https://gnustep.org'),('krzysztof','https://stackoverflow.com'),('krzysztof','https://wikipedia.com'),('matt','https://developer.apple.com'),('matt','https://fir.im'),('matt','https://mooc.com'),('matt','https://raywenderlich.com'),('matt','https://swift.org'),('pangu','https://keepassxc.com'),('pangu','https://qq.com'),('pangu','https://rime.im'),('pangu','https://www.zhihu.com'),('pangu','https://youtube.com'),('sheldon','https://developer.apple.com'),('Steve Jobs','https://www.apple.com'),('Tim Cook','https://www.apple.com');
/*!40000 ALTER TABLE `bookmark` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `username` varchar(16) NOT NULL,
  `passwd` char(40) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('dongmeiliang','dongmeiliang','meiliang@tenneshop.com'),('galloway','galloway','galloway@galloway.com'),('krzysztof','krzysztof','krzysztof@krzysztof.com'),('matt','matt','matt@matt.com'),('orta','orta','orta@orta.com'),('pangu','pangu','pangu@pangu.com');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-04-19 16:25:41
