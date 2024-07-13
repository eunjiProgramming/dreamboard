-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: dreamboard
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `board`
--

DROP TABLE IF EXISTS `board`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `board` (
  `boardIdx` int NOT NULL AUTO_INCREMENT,
  `memID` varchar(20) NOT NULL,
  `title` varchar(100) NOT NULL,
  `content` varchar(2000) NOT NULL,
  `writer` varchar(20) NOT NULL,
  `indate` datetime DEFAULT CURRENT_TIMESTAMP,
  `count` int DEFAULT '0',
  `boardGroup` int DEFAULT NULL,
  `boardSequence` int DEFAULT NULL,
  `boardLevel` int DEFAULT NULL,
  `boardAvailable` int DEFAULT NULL,
  PRIMARY KEY (`boardIdx`),
  UNIQUE KEY `boardIdx_UNIQUE` (`boardIdx`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `board`
--

LOCK TABLES `board` WRITE;
/*!40000 ALTER TABLE `board` DISABLE KEYS */;
INSERT INTO `board` VALUES (1,'eunji','권은지의 글','은지의 글!!\r\n성실히 살자!','권은지','2023-10-09 17:12:41',5,0,0,0,1),(2,'eunji','권은지의 글2','권은지의 글2\r\n권은지의 글2\r\n권은지의 글2\r\n권은지의 글2\r\n권은지의 글2','권은지','2023-10-09 17:14:48',8,1,0,0,1),(3,'eunji','권은지의 글3','권은지의 글\r\n권은지의 글\r\n권은지의 글','권은지','2023-10-09 17:15:28',13,2,0,0,1),(4,'eunji','권은지의 글4(수정)','권은지의 글\r\n권은지의 글\r\n권은지의 글','권은지','2023-10-09 17:15:36',8,3,0,0,0),(5,'eunji','권은지의 글5','권은지의 글\r\n권은지의 글\r\n권은지의 글','권은지','2023-10-09 17:15:45',8,4,0,0,1),(6,'eunji','권은지의 글6','권은지의 글\r\n권은지의 글\r\n권은지의 글','권은지','2023-10-09 17:15:54',5,5,0,0,0),(7,'hellohi','김안녕의 글1','김안녕의 글\r\n김안녕의 글\r\n김안녕의 글','김안녕','2023-10-09 17:47:05',2,6,0,0,1),(8,'hellohi','김안녕의 글2','김안녕의 글\r\n김안녕의 글\r\n김안녕의 글','김안녕','2023-10-09 17:47:14',13,7,0,0,1),(9,'hellohi','김안녕의 글3~~~','김안녕의 글~~~\r\n김안녕의 글\r\n김안녕의 글','김안녕','2023-10-09 17:47:21',13,8,0,0,1),(10,'hello333','안녕삼삼의 글1','안녕삼삼의 글\r\n안녕삼삼의 글\r\n안녕삼삼의 글','안녕삼삼','2023-10-09 17:54:08',7,9,0,0,1),(11,'eunji','김안녕의 글2(권은지의 답글)','반갑습니다.','권은지','2023-10-09 17:57:18',8,7,2,1,1),(12,'eunji','김안녕의 글2(권은지의 답글2)','','권은지','2023-10-09 17:57:26',1,7,3,2,1),(13,'bit01','김안녕의 글2(안녕 난 홍길동이야)~~~','반가워 김안녕','홍길동','2023-10-09 17:58:07',9,7,1,1,1),(14,'bit01','권은지의 글','','홍길동','2023-10-09 18:00:02',4,0,2,1,1),(15,'bit01','권은지의 글(홍길동의 답변)','은지야 반가워','홍길동','2023-10-09 18:00:16',2,0,3,2,0),(16,'eunji','안녕삼삼의 글1(권은지의 답글)','안녕 삼삼 안녕^^','권은지','2023-10-09 19:03:53',31,9,1,1,1),(17,'eunji','권은지의 글(권은지 답글)','^^','권은지','2023-10-09 19:05:32',3,0,1,1,0),(18,'happy','김안녕의 글3~~~(해피의 답글)~~~','난 해피야. 새로 가입했어~~','해피','2023-10-09 19:13:07',16,8,1,1,1),(19,'happy','해피입니다.','안녕하세요! 행복하는게 사는게 꿈인 해피입니다. \r\n잘 부탁드립니다. ','해피','2023-10-09 19:15:47',28,10,0,0,1),(20,'ireneo97','해피입니다.','해피라!!!','김창경','2023-10-09 13:03:03',6,10,1,1,1),(21,'eunji','창경님 반갑습니다','반가워용','권은지','2023-10-09 13:04:48',0,10,2,2,1),(22,'eunji','오늘도 홧팅 권은지!!','홧팅 홧팅 ㅎㅎ','권은지','2023-10-10 14:22:17',0,11,0,0,1),(23,'estelle','에스텔의 글','에스텔의 글','권은지','2023-11-04 09:28:04',2,12,0,0,1);
/*!40000 ALTER TABLE `board` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `memIdx` int NOT NULL AUTO_INCREMENT,
  `memID` varchar(20) NOT NULL,
  `memPwd` varchar(20) NOT NULL,
  `memName` varchar(20) NOT NULL,
  `memAge` int NOT NULL,
  `memGender` varchar(5) NOT NULL,
  `memEmail` varchar(50) NOT NULL,
  `memAddr` varchar(50) NOT NULL,
  `memProfile` varchar(50) DEFAULT NULL,
  `enabled` tinyint DEFAULT '1',
  PRIMARY KEY (`memIdx`),
  UNIQUE KEY `memIdx_UNIQUE` (`memIdx`),
  UNIQUE KEY `memID_UNIQUE` (`memID`),
  UNIQUE KEY `memEmail_UNIQUE` (`memEmail`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
INSERT INTO `member` VALUES (6,'hellohi','hellohi','김안녕',23,'여','hellohi@hellohi.com','가락동 97-3','20230823_232138.png',1),(7,'hello01','hello01','안녕일',21,'남','hello01@gmail.com','가락동 97-3','',1),(8,'hello02','hello02','안녕이',22,'남','hello2@hello.com','김해시 삼방동','',1),(9,'hello333','hello333','안녕삼삼',23,'남','hello3@hello.com','김해시 삼방동','',1),(10,'eunji','eunji!!','권은지',33,'여','eunji@eunji.com','경상남도 김해시','hello.jpg',1),(12,'hello77','hello77','안녕럭키',7,'여','hello77@gmail.com','서울시 용산구','',1),(13,'bit01','bit010101','홍길동',20,'남','bit01@gmail.com','서울시 강동구','',1),(14,'bit02','bit02!!','김길동',22,'남','bit02@gmail.com','서울시 강서구','',1),(15,'bit03','bit0303','박길동',33,'남','bit03@gmail.com','서울시 강북구','',0),(16,'bit05','bit0505','권길순',20,'여','bit05@gmail.com','부산 해운대구','',1),(17,'happy','happy33','해피',3,'남','happy@happy.com','서울시 서초구','웰시코기.jpg',1),(18,'ireneo97','arkchangk','김창경',45,'남','cangrande@naver.com','전라남도 광양','IMG_20230910_232753_341.jpg',1),(19,'estelle','estelle','권은지',33,'여','estelle@gmail.com','인제로 115','3X4.jpg',1),(20,'123abc','1234abc','테스터',99,'남','test@test.co.kr','qqqqqqqqqq','',0);
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-11-28 14:48:16
