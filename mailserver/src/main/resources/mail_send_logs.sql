/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3306
 Source Server Type    : MySQL
 Source Server Version : 100419
 Source Host           : localhost:3306
 Source Schema         : user

 Target Server Type    : MySQL
 Target Server Version : 100419
 File Encoding         : 65001

 Date: 30/05/2021 20:49:45
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mail_send_logs
-- ----------------------------
DROP TABLE IF EXISTS `mail_send_logs`;
CREATE TABLE `mail_send_logs`  (
  `msgID` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `group_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `msg_topic` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `msg_tag` varchar(64) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `userID` bigint NOT NULL,
  `msg_status` int UNSIGNED NOT NULL DEFAULT 0,
  `msg_body` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `count` tinyint UNSIGNED NOT NULL DEFAULT 0,
  `tryTime` datetime NOT NULL,
  `createTime` datetime NULL DEFAULT NULL,
  `updateTime` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`msgID`) USING BTREE,
  INDEX `for_userid`(`userID`) USING BTREE,
  CONSTRAINT `for_userid` FOREIGN KEY (`userID`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

SET FOREIGN_KEY_CHECKS = 1;
