-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.28-log - MySQL Community Server (GPL)
-- 服务器操作系统:                      Win64
-- HeidiSQL 版本:                  10.2.0.5702
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- 导出 xyqas 的数据库结构
CREATE DATABASE IF NOT EXISTS `xyqas` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_bin */;
USE `xyqas`;

-- 导出  表 xyqas.forum 结构
CREATE TABLE IF NOT EXISTS `forum` (
  `forum_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '版块id',
  `forum_name` varchar(50) NOT NULL DEFAULT 'default' COMMENT '版块名',
  PRIMARY KEY (`forum_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='版块';

-- 正在导出表  xyqas.forum 的数据：~0 rows (大约)
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
INSERT INTO `forum` (`forum_id`, `forum_name`) VALUES
	(1, '默认');
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;

-- 导出  表 xyqas.reply 结构
CREATE TABLE IF NOT EXISTS `reply` (
  `reply_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '回复id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '回复人id',
  `tip_id` int(11) NOT NULL DEFAULT '0' COMMENT '被回复贴id',
  `reply_content` text COLLATE utf8_bin NOT NULL COMMENT '回复内容',
  `reply_publishTime` datetime DEFAULT NULL COMMENT '回复发表时间',
  PRIMARY KEY (`reply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='回复表';

-- 正在导出表  xyqas.reply 的数据：~7 rows (大约)
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` (`reply_id`, `user_id`, `tip_id`, `reply_content`, `reply_publishTime`) VALUES
	(1, 6, 1, '测试回复', '2019-10-23 16:09:37'),
	(2, 1, 1, '逍遥测试回复', '2019-10-23 17:06:42'),
	(3, 6, 29, '我是马克~', '2019-10-23 17:51:35'),
	(4, 1, 29, '我是逍遥，马克你好！', '2019-10-23 17:52:53'),
	(5, 1, 30, '我是逍遥，给老板点赞！', '2019-10-24 09:39:59'),
	(6, 3, 29, 'Shell 脚本（shell script），是一种为 shell 编写的脚本程序', '2019-10-24 13:48:04'),
	(7, 1, 32, '飞企互联牛逼！', '2019-10-25 11:19:24');
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;

-- 导出  表 xyqas.tab 结构
CREATE TABLE IF NOT EXISTS `tab` (
  `tab_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '小分类id',
  `tab_name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT 'default' COMMENT '小分类名',
  `forum_id` int(11) NOT NULL DEFAULT '1' COMMENT '大板块id',
  PRIMARY KEY (`tab_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='小分类';

-- 正在导出表  xyqas.tab 的数据：~1 rows (大约)
/*!40000 ALTER TABLE `tab` DISABLE KEYS */;
INSERT INTO `tab` (`tab_id`, `tab_name`, `forum_id`) VALUES
	(1, '默认分类', 1);
/*!40000 ALTER TABLE `tab` ENABLE KEYS */;

-- 导出  表 xyqas.tip 结构
CREATE TABLE IF NOT EXISTS `tip` (
  `tip_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '贴子id',
  `user_id` int(11) NOT NULL COMMENT '楼主id',
  `tab_id` int(11) NOT NULL COMMENT '小分类id',
  `tip_title` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标题',
  `tip_content` text COLLATE utf8_bin COMMENT '内容',
  `tip_publishTime` datetime DEFAULT NULL COMMENT '发表时间',
  `tip_modifyTime` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  `tip_click` int(11) NOT NULL DEFAULT '0' COMMENT '贴子点击量',
  `tip_status` int(11) NOT NULL DEFAULT '0' COMMENT '贴子状态，0正常，1逻辑删除',
  `tip_isKnot` int(11) NOT NULL DEFAULT '0' COMMENT '是否结贴，0否，1结贴',
  PRIMARY KEY (`tip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='贴子';

-- 正在导出表  xyqas.tip 的数据：~5 rows (大约)
/*!40000 ALTER TABLE `tip` DISABLE KEYS */;
INSERT INTO `tip` (`tip_id`, `user_id`, `tab_id`, `tip_title`, `tip_content`, `tip_publishTime`, `tip_modifyTime`, `tip_click`, `tip_status`, `tip_isKnot`) VALUES
	(1, 1, 1, '测试标题', '123', '2019-10-23 16:35:17', '2019-10-25 11:09:03', 14, 0, 1),
	(29, 6, 1, '我是马克', '马克测试发贴', '2019-10-23 17:51:14', '2019-10-25 11:18:41', 19, 0, 1),
	(30, 1, 1, '跟我回江南', '老板最牛，无与伦比！', '2019-10-24 09:39:27', '2019-10-25 11:08:40', 5, 1, 0),
	(31, 2, 1, '官宣：本论坛正式开通', '欢迎发表高质量贴子\r\n#!/bin/bash\r\necho "Hello World !"', '2019-10-24 13:53:49', '2019-10-25 15:46:13', 23, 0, 0),
	(32, 8, 1, '飞企互联的使命', '建设数字中国！', '2019-10-25 11:05:10', '2019-10-25 15:44:05', 8, 0, 0);
/*!40000 ALTER TABLE `tip` ENABLE KEYS */;

-- 导出  表 xyqas.user 结构
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `user_nick` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `user_password` varchar(20) NOT NULL DEFAULT '123456' COMMENT '密码',
  `user_status` int(2) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1禁用，2锁定',
  `user_type` int(2) NOT NULL DEFAULT '2' COMMENT '用户类型，0超级管理员，1，管理员，2普通用户',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- 正在导出表  xyqas.user 的数据：~8 rows (大约)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `user_name`, `user_nick`, `user_password`, `user_status`, `user_type`) VALUES
	(1, 'xy', '逍遥', '123456', 0, 0),
	(2, 'admin', 'admin', 'fe123456', 0, 1),
	(3, 'user', 'user', 'fe123456', 0, 2),
	(4, 'jack', '杰克', '123456', 0, 2),
	(6, 'mark', '马克', '123456', 0, 2),
	(7, 'mike', '麦克', '123456', 0, 2),
	(8, 'flyrise', '飞企互联', '123456', 0, 1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
