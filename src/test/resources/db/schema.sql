SET NAMES utf8;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `dictionary`;
CREATE TABLE `dictionary` (
  `dic_key`        varchar(64) NOT NULL comment '词典名，词典值的拼音',
  `parent_key`   varchar(64) DEFAULT NULL comment '词典父id',
  `name`        varchar(32) NOT NULL comment '词典值最多8个汉字',
  `en_name`        varchar(32) comment '英文词典值最多8个汉字',
  `data`        text DEFAULT NULL comment '扩展数据字段',
  `sort_order`   int(11) NOT NULL DEFAULT 0 comment '排序',
  `created_time` datetime  DEFAULT NULL,
  `updated_time` datetime NULL DEFAULT NULL,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version`     int(11) DEFAULT 1 comment '乐观锁',
  `operator`     varchar(32) DEFAULT NULL comment '操作人员',
  PRIMARY KEY (`dic_key`)
) COMMENT='词典表' ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

-- ----------------------------
--  Table structure for `user`
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `nick_name` varchar(15) DEFAULT NULL COMMENT '昵称',
  `number` varchar(32) DEFAULT NULL COMMENT '用户名或房间编号',
  `device_token` varchar(64) NOT NULL COMMENT '设备唯一识别码',
  `ip_address` varchar(15) DEFAULT NULL COMMENT 'ip地址',
  `mac_address` varchar(32) DEFAULT NULL COMMENT 'mac地址',
  `max_number` int(3) DEFAULT '10' COMMENT '最大连接数',
  `join_number` varchar(32) DEFAULT NULL COMMENT '当前加入的房间编号',
  `platform` varchar(15) DEFAULT NULL COMMENT '平台',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '当前状态(在线、下线（房间解散）)',
  `locked` tinyint(1) DEFAULT '0' COMMENT '账户锁定状态',
  `created_time` datetime NOT NULL,
  `updated_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) COMMENT = '用户表' ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `app_version`;
CREATE TABLE `app_version`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `type` varchar(64) DEFAULT NULL COMMENT 'app类型：ANDROID_PHONE，IPHONE，ANDROIDPAD, IPAD',
  `version_code` varchar(64) DEFAULT NULL COMMENT 'TYPE_HOME_PAGE',
  `version_name` varchar(64) DEFAULT NULL COMMENT '用户类型：TYPE_USER, TYPE_STAFF, TYPE_ADMIN',
  `url` varchar(512) DEFAULT NULL COMMENT '下载地址',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `need_update` tinyint(1) NOT NULL DEFAULT '0' comment '是否需要强制更新',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = 'APP版本表' ENGINE=InnoDB DEFAULT CHARSET=utf8;



DROP TABLE IF EXISTS `operate_log`;
CREATE TABLE `operate_log` (
  `id` varchar(32) NOT NULL ,
  `staff_id` varchar(32) NOT NULL DEFAULT '' comment '工作人员id',
  `ip_address` varchar(64) NOT NULL DEFAULT '' comment '操作人ip',
  `content` TEXT NOT NULL COMMENT '日志内容',
  `created_time` datetime DEFAULT NULL,
  `operator`     varchar(32)  comment '操作人员',
  PRIMARY KEY (`id`)
)COMMENT='操作日志' ENGINE=InnoDB DEFAULT CHARSET=utf8 ;

DROP TABLE IF EXISTS `resources`;
CREATE TABLE `resources`  (
  `id` varchar(32)NOT NULL,
  `oss_key` varchar(255) NOT NULL COMMENT '阿里云OSS保存key',
  `resource_type` varchar(64) DEFAULT NULL COMMENT '文件类型',
  `name` varchar(150) NOT NULL COMMENT '文件名称',
  `size` varchar(64) NOT NULL COMMENT '文件大小',
  `duration` varchar(15) DEFAULT NULL COMMENT '时长',
  `url` varchar(255) NOT NULL COMMENT '文件访问url',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) DEFAULT 0 COMMENT '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员id',
  PRIMARY KEY (`id`)
) COMMENT = '资源文件' ENGINE = InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `foreign_resource`;
CREATE TABLE `foreign_resource`  (
  `id` varchar(32)NOT NULL,
  `resource_id` varchar(32) NOT NULL COMMENT '阿里云OSS保存key',
  `foreign_id` varchar(32) DEFAULT NULL COMMENT '外部类型id',
  `foreign_type` varchar(50) DEFAULT NULL COMMENT '外部类型名',
  `params` varchar(150) NOT NULL COMMENT '文件名称',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员id',
  PRIMARY KEY (`id`)
) COMMENT = '资源文件关联表' ENGINE = InnoDB DEFAULT CHARSET=utf8;