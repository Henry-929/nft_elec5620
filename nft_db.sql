/*
 Navicat Premium Data Transfer

 Source Server         : zzy
 Source Server Type    : MySQL
 Source Server Version : 50723
 Source Host           : localhost:3306
 Source Schema         : nft_db

 Target Server Type    : MySQL
 Target Server Version : 50723
 File Encoding         : 65001

 Date: 27/10/2022 21:35:07
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for art
-- ----------------------------
DROP TABLE IF EXISTS `art`;
CREATE TABLE `art` (
  `art_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '艺术品ID',
  `art_name` varchar(255) NOT NULL COMMENT '艺术品名称',
  `art_file` bigint(20) DEFAULT NULL COMMENT '电子艺术品源文件',
  `art_introduction` varchar(255) DEFAULT NULL COMMENT '艺术品简介',
  `art_token` varchar(255) DEFAULT NULL COMMENT '在区块链中的token',
  `art_author` varchar(255) NOT NULL DEFAULT '佚名' COMMENT '艺术品作者',
  `owner_id` bigint(20) NOT NULL COMMENT '艺术品拥有者id',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `create_time` datetime NOT NULL COMMENT '创造时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`art_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='电子艺术品表';

-- ----------------------------
-- Records of art
-- ----------------------------
BEGIN;
INSERT INTO `art` VALUES (1, '七月的云', 1, '坐在车里在高速公路上疾驰前行，时间正是太阳刚出来的时候，远方的云的轮廓慢慢清晰了起来，不管车行速度多快，云朵就像跟在眼前一样，不紧不慢。', '1509521643838185472', 'Gigashi', 1, 1, '2022-03-31 21:23:15', '2022-03-31 21:23:15');
INSERT INTO `art` VALUES (2, 'line艺术', 3, '缭乱却富有情感色彩，line艺术由人工智能参考数百万副优秀画作进行生成创作，line艺术是反映世界情感组成的一种构思，也表达对事物的一种情感寄托。', '1509522217874825216', 'Pixel blocks', 3, 1, '2022-03-31 21:25:32', '2022-03-31 21:25:32');
INSERT INTO `art` VALUES (3, '流转的星月夜', 4, '在这幅画中，生动地描绘了充满运动和变化的星空。 整个画面被一股汹涌、动荡的蓝绿色激流所吞噬，旋转、躁动、卷曲的星云使夜空变得异常活跃。', '1509522737549090816', '王炜', 2, 1, '2022-03-31 21:27:36', '2022-03-31 21:27:36');
INSERT INTO `art` VALUES (4, '奇幻宇宙', 2, '浩瀚的宇宙充满奇幻与神秘 太阳系的外面是银河系，而银河系的外面又有很多的星系和星体，令人向往！', '1509523578729340928', '刘佳', 2, 1, '2022-03-31 21:30:57', '2022-03-31 21:30:57');
INSERT INTO `art` VALUES (5, 'opop', 5, 'asdads', NULL, 'opopo123', 3, 1, '2022-10-24 10:08:32', '2022-10-24 10:08:32');
COMMIT;

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `comment_id` bigint(20) NOT NULL COMMENT '评论ID',
  `comment_content` text NOT NULL COMMENT '评论内容',
  `commnetator_id` bigint(20) NOT NULL COMMENT '评论者',
  `comment_reply` bigint(20) DEFAULT NULL COMMENT '回复(对于另一条评论）',
  `comment_like` int(11) NOT NULL DEFAULT '0' COMMENT '点赞数量',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `deleted` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL COMMENT '创造时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`comment_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论表';

-- ----------------------------
-- Table structure for comment_like
-- ----------------------------
DROP TABLE IF EXISTS `comment_like`;
CREATE TABLE `comment_like` (
  `comment_like_id` bigint(20) NOT NULL COMMENT '评论点赞ID',
  `comment_id` bigint(20) NOT NULL COMMENT '评论ID',
  `like_user_id` bigint(20) NOT NULL COMMENT '点赞者',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `deleted` int(11) NOT NULL DEFAULT '0',
  `create_time` datetime NOT NULL COMMENT '创造时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`comment_like_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='评论点赞记录';

-- ----------------------------
-- Table structure for file
-- ----------------------------
DROP TABLE IF EXISTS `file`;
CREATE TABLE `file` (
  `file_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '文件Id',
  `file_name` varchar(255) NOT NULL COMMENT '文件名称',
  `file_format` varchar(255) NOT NULL COMMENT '文件格式（.txt .png）',
  `file_path` varchar(255) NOT NULL COMMENT '文件保存路径',
  `file_hash` varchar(255) NOT NULL COMMENT '文件',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `create_time` datetime NOT NULL COMMENT '创造时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`file_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8 COMMENT='文件表（保存各类文件，如艺术品的文件，上传的头像的文件，其他文件等）';

-- ----------------------------
-- Records of file
-- ----------------------------
BEGIN;
INSERT INTO `file` VALUES (1, 'file3b338756-6e1b-470b-8d9f-2bfe97f7a688', 'Gigashi《七月的云》.jpg', '/122924/Gigashi《七月的云》.jpg', 'd41d8cd98f00b204e9800998ecf8427e', 1, '2022-10-24 10:04:32', '2022-10-24 10:04:32');
INSERT INTO `file` VALUES (2, 'file8cdad59b-fa57-44f4-80ec-c237534fb99f', '刘佳《奇幻宇宙》.png', '/122924/刘佳《奇幻宇宙》.png', 'd41d8cd98f00b204e9800998ecf8427e', 1, '2022-10-24 10:05:32', '2022-10-24 10:05:32');
INSERT INTO `file` VALUES (3, 'file236cd6cf-ea64-435e-b5f7-be7bcc6177d1', '1.png', '/122924/1.png', 'd41d8cd98f00b204e9800998ecf8427e', 1, '2022-10-24 10:06:32', '2022-10-24 10:06:32');
INSERT INTO `file` VALUES (4, 'filee3833edb-eebb-494e-9fc8-56453a0c9f2a', '2.png', '/122924/2.png', 'd41d8cd98f00b204e9800998ecf8427e', 1, '2022-10-24 10:07:32', '2022-10-24 10:07:32');
INSERT INTO `file` VALUES (5, 'file3a60a4c1-b4e5-4b85-a215-4eb210eac7ac', '3.png', '/122924/3.png', 'd41d8cd98f00b204e9800998ecf8427e', 1, '2022-10-24 10:08:32', '2022-10-24 10:08:32');
COMMIT;

-- ----------------------------
-- Table structure for goods
-- ----------------------------
DROP TABLE IF EXISTS `goods`;
CREATE TABLE `goods` (
  `goods_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '商品ID',
  `art_id` bigint(20) NOT NULL COMMENT '商品艺术品ID',
  `goods_seller_id` bigint(20) NOT NULL COMMENT '商品卖家ID',
  `resale_price` decimal(10,2) DEFAULT NULL COMMENT '零售价',
  `selled` tinyint(1) NOT NULL COMMENT '是否被售出',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `create_time` datetime NOT NULL COMMENT '创造时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`goods_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='商品表';

-- ----------------------------
-- Records of goods
-- ----------------------------
BEGIN;
INSERT INTO `goods` VALUES (1, 1, 1, 22.00, 0, 1, '2022-04-02 20:56:23', '2022-04-02 20:56:23');
INSERT INTO `goods` VALUES (6, 5, 3, 30.00, 0, 1, '2022-10-27 00:36:50', '2022-10-27 00:36:50');
COMMIT;

-- ----------------------------
-- Table structure for goods_trade
-- ----------------------------
DROP TABLE IF EXISTS `goods_trade`;
CREATE TABLE `goods_trade` (
  `trade_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '交易记录ID',
  `goods_id` bigint(20) NOT NULL COMMENT '商品的ID',
  `seller_id` bigint(20) NOT NULL COMMENT '卖方ID',
  `buyer_id` bigint(20) NOT NULL COMMENT '买方ID',
  `coin` decimal(10,2) NOT NULL COMMENT '涉及金额',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `create_time` datetime NOT NULL COMMENT '创造时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`trade_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='商品交易记录表';

-- ----------------------------
-- Records of goods_trade
-- ----------------------------
BEGIN;
INSERT INTO `goods_trade` VALUES (1, 2, 2, 3, 2000.00, 1, '2022-10-24 22:40:20', '2022-10-24 22:40:20');
INSERT INTO `goods_trade` VALUES (2, 4, 2, 3, 10.00, 1, '2022-10-25 20:03:57', '2022-10-25 20:03:57');
INSERT INTO `goods_trade` VALUES (3, 5, 1, 3, 15.00, 1, '2022-10-25 20:03:57', '2022-10-25 20:03:57');
INSERT INTO `goods_trade` VALUES (5, 7, 1, 2, 30.00, 1, '2022-10-27 16:06:12', '2022-10-27 16:06:12');
INSERT INTO `goods_trade` VALUES (6, 8, 3, 2, 10.00, 1, '2022-10-27 16:06:12', '2022-10-27 16:06:12');
COMMIT;

-- ----------------------------
-- Table structure for shopping_car
-- ----------------------------
DROP TABLE IF EXISTS `shopping_car`;
CREATE TABLE `shopping_car` (
  `cargood_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物车商品ID',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `goods_id` bigint(20) NOT NULL COMMENT '商品ID',
  `version` int(11) NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `create_time` datetime NOT NULL COMMENT '创造时间',
  `update_time` datetime NOT NULL COMMENT '修改时间',
  PRIMARY KEY (`cargood_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='购物车';

-- ----------------------------
-- Records of shopping_car
-- ----------------------------
BEGIN;
INSERT INTO `shopping_car` VALUES (2, 3, 1, 1, '2022-10-27 00:40:12', '2022-10-27 00:40:12');
INSERT INTO `shopping_car` VALUES (3, 3, 7, 1, '2022-10-27 00:40:27', '2022-10-27 00:40:27');
COMMIT;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `user_name` varchar(255) NOT NULL COMMENT '用户名',
  `user_password` varchar(255) NOT NULL COMMENT '密码，数据库中通过保存通过md5加密处理过的值',
  `balance` decimal(10,2) DEFAULT NULL COMMENT '账户余额(ETHB)',
  `bank_card` varchar(255) DEFAULT NULL,
  `roles` varchar(255) DEFAULT NULL,
  `perms` varchar(255) DEFAULT NULL,
  `eth_address` varchar(255) DEFAULT NULL COMMENT '以太坊地址',
  `key_src` varchar(255) DEFAULT NULL COMMENT 'key文件名',
  `version` int(11) DEFAULT '1' COMMENT '乐观锁',
  `create_time` datetime DEFAULT NULL COMMENT '创造时间',
  `update_time` datetime DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8 COMMENT='用户表';

-- ----------------------------
-- Records of user
-- ----------------------------
BEGIN;
INSERT INTO `user` VALUES (1, 'vivo', 'bedfe476f8a2800b2711c7fdbc4d1e50', 61.00, NULL, 'user', 'admin:manage', NULL, NULL, 3, '2022-10-16 00:28:25', '2022-10-16 00:28:25');
INSERT INTO `user` VALUES (2, 'papi', 'bedfe476f8a2800b2711c7fdbc4d1e50', 10.00, NULL, 'user', 'user:visit', NULL, NULL, 1, '2022-10-16 00:29:09', '2022-10-16 00:29:09');
INSERT INTO `user` VALUES (3, 'oppo', 'bedfe476f8a2800b2711c7fdbc4d1e50', 4.00, '', 'user', 'user:visit', NULL, NULL, 4, '2022-10-17 22:15:50', '2022-10-17 22:15:50');
COMMIT;

-- ----------------------------
-- Procedure structure for NewProc
-- ----------------------------
DROP PROCEDURE IF EXISTS `NewProc`;
delimiter ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `NewProc`()
BEGIN
  #Routine body goes here...
TRUNCATE TABLE user;
END;
;;
delimiter ;

SET FOREIGN_KEY_CHECKS = 1;
