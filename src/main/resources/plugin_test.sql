/*
 Navicat Premium Data Transfer

 Source Server         : local
 Source Server Type    : MySQL
 Source Server Version : 50726
 Source Host           : localhost:3306
 Source Schema         : plugin_test

 Target Server Type    : MySQL
 Target Server Version : 50726
 File Encoding         : 65001

 Date: 24/12/2019 20:41:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for fromLabs_user
-- ----------------------------
DROP TABLE IF EXISTS `fromLabs_user`;
CREATE TABLE `fromLabs_user` (
  `uid` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of fromLabs_user
-- ----------------------------
BEGIN;
INSERT INTO `fromLabs_user` VALUES (1, 'alex_from', '123');
COMMIT;

-- ----------------------------
-- Table structure for monday_user
-- ----------------------------
DROP TABLE IF EXISTS `monday_user`;
CREATE TABLE `monday_user` (
  `uid` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of monday_user
-- ----------------------------
BEGIN;
INSERT INTO `monday_user` VALUES (1, 'alex_monday', '123');
COMMIT;

-- ----------------------------
-- Table structure for task_info
-- ----------------------------
DROP TABLE IF EXISTS `task_info`;
CREATE TABLE `task_info` (
  `task_id` int(11) NOT NULL,
  `uid` int(11) DEFAULT NULL,
  `task_name` varchar(50) DEFAULT NULL,
  `project_name` varchar(50) DEFAULT NULL,
  `task_priority` int(11) DEFAULT NULL,
  `task_type` varchar(20) DEFAULT NULL,
  `task_detail` varchar(300) DEFAULT NULL,
  `start_time` varchar(60) DEFAULT NULL,
  `end_time` varchar(60) DEFAULT NULL,
  `due_time` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of task_info
-- ----------------------------
BEGIN;
INSERT INTO `task_info` VALUES (1, 3, 't1', 'p1', 1, 'develop', 'asdfasdfasdfasdf', '', '', '2019-01-17 10:14:00');
INSERT INTO `task_info` VALUES (2, 1, 't2', 'p2', 2, 'design', '12312312', '', '', '2019-01-02 10:14:00');
INSERT INTO `task_info` VALUES (3, 2, 't3', 'p3', 2, 'analyze', 'assdfgsdfgdsg', '', '', '2019-01-02 12:14:00');
INSERT INTO `task_info` VALUES (4, 1, 't2', 'p3', 2, 'design', 'asdfsadfas', '', '2019-12-12 14:50:59', '2019-01-02 10:16:11');
INSERT INTO `task_info` VALUES (12312, 1, 't4', 'p4', 3, 'consultant', 'wewoosdfs', '2019-12-12 14:50:59', '2019-12-12 14:50:59', '2019-01-17 01:14:13');
INSERT INTO `task_info` VALUES (123412, 1, 't6', 'p6', 5, 'design', 'asasdfasdf', '2019-12-05 14:51:10', '2019-12-05 14:51:10', '2019-01-17 10:14:12');
INSERT INTO `task_info` VALUES (11234123, 1, 't5', 'p5', 3, 'develop', 'asd打发士大夫卡刷点卡ff', '2019-12-09 14:51:14', '2019-12-09 14:51:14', '2019-01-17 20:14:00');
COMMIT;

-- ----------------------------
-- Table structure for trello_user
-- ----------------------------
DROP TABLE IF EXISTS `trello_user`;
CREATE TABLE `trello_user` (
  `uid` int(11) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of trello_user
-- ----------------------------
BEGIN;
INSERT INTO `trello_user` VALUES (1, 'alex_trello', '123');
INSERT INTO `trello_user` VALUES (2, 'alex2', '321');
INSERT INTO `trello_user` VALUES (3, 'alex4', '4321');
INSERT INTO `trello_user` VALUES (12, 'alex213123', '1234444');
INSERT INTO `trello_user` VALUES (15, 'baby', '12312312');
INSERT INTO `trello_user` VALUES (123, 'alex123', '123123');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
