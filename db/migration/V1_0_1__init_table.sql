/*
 Navicat Premium Data Transfer

 Source Server         : 本地
 Source Server Type    : MySQL
 Source Server Version : 80011
 Source Host           : localhost:3306
 Source Schema         : db_smart

 Target Server Type    : MySQL
 Target Server Version : 80011
 File Encoding         : 65001

 Date: 27/07/2018 15:26:36
*/

SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for administrator
-- ----------------------------
DROP TABLE IF EXISTS `administrator`;
CREATE TABLE `administrator`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `account` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `notify_order` smallint(6) UNSIGNED NOT NULL DEFAULT 0,
  `status` smallint(6) NOT NULL DEFAULT 0,
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `account`(`account`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 53 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '后台管理员表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of administrator
-- ----------------------------
INSERT INTO `administrator` VALUES (1, 'michaeltang@openmore.org', 'e10adc3949ba59abbe56e057f20f883e', 'MichaelTang2', 10, 10, '2017-08-31 20:39:16', '2017-08-31 21:04:10');

-- ----------------------------
-- Table structure for alipay_transaction
-- ----------------------------
DROP TABLE IF EXISTS `alipay_transaction`;
CREATE TABLE `alipay_transaction`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `trade_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `out_trade_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `trade_index`(`out_trade_no`, `trade_no`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '支付宝支付交易表' ROW_FORMAT = Compact;

-- ----------------------------
-- Table structure for message
-- ----------------------------
DROP TABLE IF EXISTS `message`;
CREATE TABLE `message`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `type` smallint(6) UNSIGNED NOT NULL DEFAULT 0,
  `title` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `action` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `is_read` smallint(6) NOT NULL DEFAULT 0,
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expired_time` datetime(0) NOT NULL DEFAULT '9999-01-01 00:00:00',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 373 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '消息表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of message
-- ----------------------------
INSERT INTO `message` VALUES (1, 1, 20, '作品已卖出', '你的作品已经卖出，收入200金币！', '', 0, '2017-10-18 11:48:44', '9999-01-01 00:00:00');
INSERT INTO `message` VALUES (2, 1, 10, '测试消息标题2', '测试消息内容2', 'http://www.qq.com', 0, '2017-08-05 22:24:07', '2017-08-05 22:24:07');
INSERT INTO `message` VALUES (4, 2, 10, '测试消息标题4', '测试消息内容4', 'http://www.qq.com', 10, '2017-08-05 22:44:07', '2017-08-05 22:44:07');
INSERT INTO `message` VALUES (5, 2, 10, '测试消息标题5', '测试消息内容5', 'http://www.qq.com', 10, '2017-08-05 22:54:07', '2017-08-05 22:54:07');
INSERT INTO `message` VALUES (6, 2, 20, '测试消息标题6', '测试消息内容6', 'http://www.qq.com', 0, '2017-08-05 23:54:07', '2017-08-05 23:54:07');

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限识别码',
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '权限名',
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户权限表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `code` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色识别码',
  `name` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色名',
  `created_ime` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for rolepermission
-- ----------------------------
DROP TABLE IF EXISTS `rolepermission`;
CREATE TABLE `rolepermission`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '对应角色id',
  `permission_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色对应权限id',
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色-权限对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for schema_version
-- ----------------------------
DROP TABLE IF EXISTS `schema_version`;
CREATE TABLE `schema_version`  (
  `installed_rank` int(11) NOT NULL,
  `version` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
  `description` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `type` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `script` varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `checksum` int(11) NULL DEFAULT NULL,
  `installed_by` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `installed_on` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `execution_time` int(11) NOT NULL,
  `success` tinyint(1) NOT NULL,
  PRIMARY KEY (`installed_rank`) USING BTREE,
  INDEX `schema_version_s_idx`(`success`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of schema_version
-- ----------------------------
INSERT INTO `schema_version` VALUES (1, '1.0.1', 'init table', 'SQL', 'V1_0_1__init_table.sql', 1272164278, 'root', '2018-07-27 13:52:37', 4206, 1);

-- ----------------------------
-- Table structure for system_config
-- ----------------------------
DROP TABLE IF EXISTS `system_config`;
CREATE TABLE `system_config`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `value` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '系统配置表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of system_config
-- ----------------------------
INSERT INTO `system_config` VALUES (1, 'app_config', '{\"exhibition_price\":1000,\"auction_rate\":10}', 'remark', '2017-11-16 20:07:14', '2017-11-16 20:07:14');

-- ----------------------------
-- Table structure for third_authorizations
-- ----------------------------
DROP TABLE IF EXISTS `third_authorizations`;
CREATE TABLE `third_authorizations`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `third_uid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `third_party` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `wechat_unionid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0),
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `user_id`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 253 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '订单状态表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of third_authorizations
-- ----------------------------
INSERT INTO `third_authorizations` VALUES (1, 3, '5c1b0323-52d5-45aa-9bf1-c118f53208fe', 'qq', '', '2017-08-07 00:00:46', '2017-08-08 11:28:34');
INSERT INTO `third_authorizations` VALUES (2, 4, '963050eb-b331-46a0-8a9d-78632b946b68', 'qq', '', '2017-08-07 00:22:26', '2017-08-07 00:22:26');
INSERT INTO `third_authorizations` VALUES (3, 5, '4aa71cd6-7505-43dd-b2c9-52596e33e55e', 'qq', '', '2017-08-07 00:24:14', '2017-08-07 00:24:14');
INSERT INTO `third_authorizations` VALUES (4, 6, '555c9562-e4a2-4ee1-a431-91dbed44897d', 'qq', '', '2017-08-07 00:29:51', '2017-08-07 00:29:51');
INSERT INTO `third_authorizations` VALUES (5, 7, '50a36b42-a237-4e8c-85d5-82a3862756f4', 'qq', '', '2017-08-07 00:35:38', '2017-08-07 00:35:38');
INSERT INTO `third_authorizations` VALUES (6, 8, 'b4b87727-e0cb-46a1-9e0d-fb7714570e18', 'qq', '', '2017-08-07 00:36:42', '2017-08-07 00:36:42');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `uuid` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `salt` varchar(15) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐值',
  `mobile` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '手机号',
  `nickname` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '昵称',
  `username` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户名',
  `password` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登陆密码（加密）',
  `age` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '年龄',
  `avatar` varchar(512) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '头像',
  `birthday` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' COMMENT '生日',
  `gender` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '性别',
  `location` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '住址',
  `is_test` smallint(6) NOT NULL DEFAULT 0 COMMENT '是否是测试账号',
  `ethnic` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '民族',
  `status` smallint(6) NOT NULL DEFAULT 0 COMMENT '账号状态',
  `lock` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否被锁定',
  `coin_quantity` int(11) UNSIGNED NOT NULL DEFAULT 0,
  `resident_type_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居民类型id',
  `part_branch_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '党支部id',
  `chronic_disease_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '满性病id',
  `enterparty_time` datetime(0) NULL DEFAULT NULL COMMENT '入党时间',
  ` work_unit` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '工作单位',
  `residential_address` varchar(150) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '居住地址',
  `residence_type` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '户口类型（城市/农村）',
  `family_door` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '门户号',
  `floor_location` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '单元/门',
  `floor` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '楼层',
  `enrolled_situation` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '入户情况',
  `login_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '登录时间',
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updated_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `uuid`(`uuid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 327 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户（居民）表' ROW_FORMAT = Compact;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', 'aisdjfajsfijasfj', NULL, '13520664663', 'MichaelTang', NULL, NULL, 'http://wx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTJdy57U40RXkqPjGvYP7z1Rm5HwiczCQOalX9eTmS04CACmWIu5oXlPzTIicrcmXqfrn6kIrbgP28Jg/0', 'akldsjf', 'alkdjsflkj', 'asdklfjl', 1, NULL, 10, 1764, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-10-28 08:22:54', '2017-08-05 22:54:07', '2017-10-28 08:22:29');
INSERT INTO `user` VALUES ('2', 'adhsfuihwqiluhfi', NULL, '132139819238', 'aksjdf', NULL, NULL, 'http://photo.avatar.com', 'asdlkjf', 'alsdkjflk', 'sdlkfj', 1, NULL, 10, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-08-05 22:54:24', '2017-08-05 22:54:24', '2017-08-17 10:57:53');
INSERT INTO `user` VALUES ('3', 'd0e4f8fb-4f6f-4319-9a3e-5f239cc77dc3', NULL, '', 'testuserZUQGKZ', NULL, NULL, '', '', '', '', 0, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-08-08 11:15:08', '2017-08-07 00:00:46', '2017-08-07 00:00:46');
INSERT INTO `user` VALUES ('4', 'bb86849f-fa72-4ba3-87a1-72ec0a5cbf22', NULL, '', 'testuser22QPG3', NULL, NULL, '', '', '', '', 0, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-08-07 00:22:26', '2017-08-07 00:22:26', '2017-08-07 00:22:26');
INSERT INTO `user` VALUES ('5', '0d2349e0-1d4c-4688-bd6e-307c47bfdddd', NULL, '', 'testuserHWULMB', NULL, NULL, '', '', '', '', 0, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-08-07 00:24:13', '2017-08-07 00:24:13', '2017-08-07 00:24:13');
INSERT INTO `user` VALUES ('6', 'd38ad1bf-e56f-4f91-a50b-272bd63390cf', NULL, '', 'testuserH6NL7V', NULL, NULL, '', '', '', '', 0, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-08-07 00:29:51', '2017-08-07 00:29:51', '2017-08-07 00:29:51');
INSERT INTO `user` VALUES ('7', 'ef4d374f-7503-48a0-a59e-b62b3c1b04da', NULL, '', 'testuserTKUH4N', NULL, NULL, '', '', '', '', 0, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-08-07 00:35:38', '2017-08-07 00:35:38', '2017-08-07 00:35:38');
INSERT INTO `user` VALUES ('8', '306bc2c6-dd17-44a3-90df-ed9e9ae62303', NULL, '', 'testuserZCCTML', NULL, NULL, '', '', '', '', 0, NULL, 0, 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2017-08-07 00:36:42', '2017-08-07 00:36:42', '2017-08-07 00:36:42');

-- ----------------------------
-- Table structure for userrole
-- ----------------------------
DROP TABLE IF EXISTS `userrole`;
CREATE TABLE `userrole`  (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `role_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应角色id',
  `user_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '对应用户id',
  `created_time` datetime(0) NULL DEFAULT NULL,
  `updated_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户-角色对应表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for wxpay_transaction
-- ----------------------------
DROP TABLE IF EXISTS `wxpay_transaction`;
CREATE TABLE `wxpay_transaction`  (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `transaction_id` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `out_trade_no` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '',
  `content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `created_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `trade_index`(`out_trade_no`, `transaction_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '微信支付交易表' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
