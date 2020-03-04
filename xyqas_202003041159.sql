-- --------------------------------------------------------
-- 主机:                           127.0.0.1
-- 服务器版本:                        5.7.28-log - MySQL Community Server (GPL)
-- 服务器OS:                        Win64
-- HeidiSQL 版本:                  10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for xyqas
CREATE DATABASE IF NOT EXISTS `xyqas` /*!40100 DEFAULT CHARACTER SET utf8 COLLATE utf8_bin */;
USE `xyqas`;

-- Dumping structure for table xyqas.forum
CREATE TABLE IF NOT EXISTS `forum` (
  `forum_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '版块id',
  `forum_name` varchar(50) NOT NULL DEFAULT 'default' COMMENT '版块名',
  `forum_isDeleted` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除，0-否，1-逻辑删除',
  `forum_createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `forum_modifyTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`forum_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='版块';

-- Dumping data for table xyqas.forum: ~2 rows (大约)
DELETE FROM `forum`;
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
INSERT INTO `forum` (`forum_id`, `forum_name`, `forum_isDeleted`, `forum_createTime`, `forum_modifyTime`) VALUES
	(1, '默认', 0, '2020-03-04 11:48:58', '2020-03-04 11:53:13'),
	(2, '生活', 0, '2020-03-04 11:48:58', '2020-03-04 11:53:13');
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;

-- Dumping structure for table xyqas.reply
CREATE TABLE IF NOT EXISTS `reply` (
  `reply_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '回复id',
  `user_id` int(11) NOT NULL DEFAULT '0' COMMENT '回复人id',
  `tip_id` int(11) NOT NULL DEFAULT '0' COMMENT '被回复贴id',
  `reply_content` text COLLATE utf8_bin NOT NULL COMMENT '回复内容',
  `reply_publishTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '回复发表时间',
  `reply_modifyTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '回复修改时间',
  `reply_isDeleted` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除，0-否，1-逻辑删除',
  PRIMARY KEY (`reply_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='回复表';

-- Dumping data for table xyqas.reply: ~17 rows (大约)
DELETE FROM `reply`;
/*!40000 ALTER TABLE `reply` DISABLE KEYS */;
INSERT INTO `reply` (`reply_id`, `user_id`, `tip_id`, `reply_content`, `reply_publishTime`, `reply_modifyTime`, `reply_isDeleted`) VALUES
	(1, 6, 1, '测试回复', '2019-10-23 16:09:37', NULL, 0),
	(2, 1, 1, '逍遥测试回复', '2019-10-23 17:06:42', NULL, 0),
	(3, 1, 2, '逍遥在此表示感谢。', '2019-10-26 23:56:55', NULL, 0),
	(4, 1, 2, '本论坛仅用于进行学习交流。', '2019-10-27 00:08:20', NULL, 0),
	(5, 2, 3, '<script>alert(‘Hello World’)</script>', '2019-10-28 09:34:54', NULL, 0),
	(6, 2, 3, '<script>alert("Hello World");</script>', '2019-10-28 09:37:05', NULL, 0),
	(7, 2, 3, '很好', '2019-10-28 09:38:36', NULL, 0),
	(8, 3, 2, 'user回复', '2019-10-29 14:50:29', NULL, 0),
	(9, 1, 4, '<script>alert("Test script");</script>', '2019-10-29 17:52:32', NULL, 0),
	(10, 1, 4, '为了不修改用户输入的原内容，所以直接在页面中显示内容前进行处理。\r\n使用JSTL中的<c:out>标签可以让回复内容中的脚本不执行。', '2019-10-29 18:03:31', NULL, 0),
	(11, 1, 1, '修改回复逻辑，逍遥测试回复。', '2019-11-01 17:39:31', NULL, 0),
	(12, 1, 1, '逍遥测试回复...', '2019-11-01 17:49:55', NULL, 0),
	(13, 1, 5, 'test reply', '2019-12-04 14:33:44', '2019-12-04 14:33:44', 0),
	(14, 1, 5, '66666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666666', '2019-12-17 11:48:06', '2019-12-17 11:48:06', 0),
	(15, 1, 5, 'overflow 一共有5个属性。\r\n\r\n1、overflow:auto；内容会被修剪，超出设置的宽高后会出现滚动条\r\n\r\n2、overflow:scroll;内容会被修剪，不管内容是否超出，都会出现滚动条的位置\r\n\r\n3、overflow:visible;这个是默认值，内容不会被修剪，会出现在元素框外面。\r\n\r\n4、overflow:hidden;内容被修剪，多余的内容被隐藏\r\n\r\n5、overflow:inherit;从父元素那里继承overflow的值。', '2019-12-18 10:30:15', '2019-12-18 10:30:15', 0),
	(16, 1, 5, 'overflow 一共有5个属性。\r\n\r\n1、overflow:auto；内容会被修剪，超出设置的宽高后会出现滚动条\r\n\r\n2、overflow:scroll;内容会被修剪，不管内容是否超出，都会出现滚动条的位置\r\n\r\n3、overflow:visible;这个是默认值，内容不会被修剪，会出现在元素框外面。\r\n\r\n4、overflow:hidden;内容被修剪，多余的内容被隐藏\r\n\r\n5、overflow:inherit;从父元素那里继承overflow的值。\r\n\r\noverflow 一共有5个属性。\r\n\r\n1、overflow:auto；内容会被修剪，超出设置的宽高后会出现滚动条\r\n\r\n2、overflow:scroll;内容会被修剪，不管内容是否超出，都会出现滚动条的位置\r\n\r\n3、overflow:visible;这个是默认值，内容不会被修剪，会出现在元素框外面。\r\n\r\n4、overflow:hidden;内容被修剪，多余的内容被隐藏\r\n\r\n5、overflow:inherit;从父元素那里继承overflow的值。', '2019-12-18 10:30:29', '2019-12-18 10:30:29', 0),
	(17, 1, 5, '测试textarea的换行', '2019-12-18 10:44:56', '2019-12-18 10:44:56', 0),
	(18, 1, 5, '测试textarea的换行1\r\n\r\n测试textarea的换行2\r\n\r\n测试textarea的换行3', '2019-12-18 10:45:11', '2019-12-18 10:45:11', 0);
/*!40000 ALTER TABLE `reply` ENABLE KEYS */;

-- Dumping structure for table xyqas.tab
CREATE TABLE IF NOT EXISTS `tab` (
  `tab_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '分类id',
  `tab_name` varchar(50) COLLATE utf8_bin NOT NULL DEFAULT 'default' COMMENT '分类名',
  `forum_id` int(11) NOT NULL DEFAULT '1' COMMENT '版块id',
  `tab_isDeleted` int(2) NOT NULL DEFAULT '0' COMMENT '是否删除，0-否，1-逻辑删除',
  `tab_createTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `tab_modifyTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`tab_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='分类';

-- Dumping data for table xyqas.tab: ~3 rows (大约)
DELETE FROM `tab`;
/*!40000 ALTER TABLE `tab` DISABLE KEYS */;
INSERT INTO `tab` (`tab_id`, `tab_name`, `forum_id`, `tab_isDeleted`, `tab_createTime`, `tab_modifyTime`) VALUES
	(1, 'Technology', 1, 0, '2020-03-04 11:49:43', '2020-03-04 11:52:57'),
	(2, '其他', 1, 0, '2020-03-04 11:49:43', '2020-03-04 11:52:57'),
	(3, 'Life', 2, 0, '2020-03-04 11:49:43', '2020-03-04 11:52:57');
/*!40000 ALTER TABLE `tab` ENABLE KEYS */;

-- Dumping structure for table xyqas.tip
CREATE TABLE IF NOT EXISTS `tip` (
  `tip_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '贴子id',
  `user_id` int(11) NOT NULL COMMENT '楼主id',
  `tab_id` int(11) NOT NULL COMMENT '分类id',
  `tip_title` varchar(255) COLLATE utf8_bin NOT NULL DEFAULT '' COMMENT '标题',
  `tip_content` text COLLATE utf8_bin COMMENT '内容',
  `tip_publishTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '发表时间',
  `tip_modifyTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `tip_click` int(11) NOT NULL DEFAULT '0' COMMENT '贴子点击量',
  `tip_isDeleted` int(2) NOT NULL DEFAULT '0' COMMENT '是否逻辑删除，0否，1是',
  `tip_isKnot` int(2) NOT NULL DEFAULT '0' COMMENT '是否结贴，0否，1结贴',
  `tip_replies` int(11) NOT NULL DEFAULT '0' COMMENT '贴子回复数',
  `tip_isTop` int(2) NOT NULL DEFAULT '0' COMMENT '是否置顶，0-否，1-是',
  `tip_topTime` datetime DEFAULT NULL COMMENT '置顶时间',
  PRIMARY KEY (`tip_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8 COLLATE=utf8_bin COMMENT='贴子';

-- Dumping data for table xyqas.tip: ~6 rows (大约)
DELETE FROM `tip`;
/*!40000 ALTER TABLE `tip` DISABLE KEYS */;
INSERT INTO `tip` (`tip_id`, `user_id`, `tab_id`, `tip_title`, `tip_content`, `tip_publishTime`, `tip_modifyTime`, `tip_click`, `tip_isDeleted`, `tip_isKnot`, `tip_replies`, `tip_isTop`, `tip_topTime`) VALUES
	(1, 1, 2, '逍遥论坛的第一个贴子', '这是第一个贴子，测试发贴功能成功！', '2019-10-23 16:35:17', '2019-11-01 17:49:55', 78, 0, 0, 4, 0, NULL),
	(2, 2, 2, '官宣：本论坛正式开通', '欢迎发表高质量贴子\r\n#!/bin/bash\r\necho "Hello World !"', '2019-10-24 13:53:49', '2019-11-22 10:35:27', 94, 0, 1, 3, 1, '2020-02-27 11:15:01'),
	(3, 1, 1, '发贴时的版块与分类选项联动', '用ajax访问有@ResponseBody注解的Controller，然后对返回的tabList进行处理，刷新分类下拉栏的选项。', '2019-10-27 23:29:11', '2019-10-28 09:38:36', 39, 0, 0, 3, 1, '2020-02-27 17:44:20'),
	(4, 1, 1, '防止贴子内容中弹出用户输入的脚本', '需要对用户输入的内容进行处理。', '2019-10-29 17:52:15', '2019-10-29 18:03:31', 19, 0, 0, 2, 0, NULL),
	(5, 3, 2, '贴子测试_191122', '测试\r\n更新于2020-02-18 22:25', '2019-11-22 11:23:17', '2020-02-18 22:25:53', 78, 0, 0, 6, 0, NULL),
	(6, 5, 2, '新人报到', '在下李阳，新年快乐！', '2020-03-04 09:53:46', '2020-03-04 09:53:46', 1, 0, 0, 0, 0, NULL),
	(7, 5, 2, '测试正文为空', NULL, '2020-03-04 10:41:34', '2020-03-04 10:41:34', 1, 0, 0, 0, 0, NULL);
/*!40000 ALTER TABLE `tip` ENABLE KEYS */;

-- Dumping structure for table xyqas.user
CREATE TABLE IF NOT EXISTS `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `user_nick` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `user_password` varchar(20) NOT NULL DEFAULT '123456' COMMENT '密码',
  `user_status` int(2) NOT NULL DEFAULT '0' COMMENT '状态，0正常，1禁用，2锁定',
  `user_type` int(2) NOT NULL DEFAULT '2' COMMENT '用户类型，0超级管理员，1，管理员，2普通用户',
  `user_regTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  `user_lastLoginTime` datetime DEFAULT NULL COMMENT '最近登录时间',
  `user_modifyTime` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- Dumping data for table xyqas.user: ~6 rows (大约)
DELETE FROM `user`;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` (`user_id`, `user_name`, `user_nick`, `user_password`, `user_status`, `user_type`, `user_regTime`, `user_lastLoginTime`, `user_modifyTime`) VALUES
	(1, 'xy', '逍遥', '123456', 0, 0, NULL, '2020-03-04 11:50:49', '2020-03-04 11:52:39'),
	(2, 'admin', 'admin', '123456', 0, 1, NULL, '2020-03-04 11:50:49', '2020-03-04 11:52:39'),
	(3, 'user', 'user', '123456', 0, 2, NULL, '2020-03-04 11:50:49', '2020-03-04 11:52:39'),
	(4, 'flyrise', 'FE', 'fe123456', 0, 1, NULL, '2020-03-04 11:50:49', '2020-03-04 11:52:39'),
	(5, 'liyang', '李阳', '123456', 0, 2, '2020-03-03 12:07:11', '2020-03-04 11:50:49', '2020-03-04 11:52:39'),
	(6, 'zhangquan', '张泉', '123456', 0, 2, '2020-03-03 12:13:38', '2020-03-04 11:50:49', '2020-03-04 11:52:39');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
