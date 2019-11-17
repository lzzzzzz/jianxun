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
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `user_type` varchar(64) DEFAULT NULL COMMENT '用户类型：USER_TYPE_USER, USER_TYPE_STAFF, USER_TYPE_STAFF_MANAGER, USER_TYPE_STAFF_ROOT',
  `phone` varchar(16) DEFAULT NULL COMMENT '手机号',
  `qq_openid` varchar(32) DEFAULT NULL COMMENT 'qq的openid',
  `wechat_openid` varchar(32) DEFAULT NULL COMMENT 'wechat的openid',
  `miniapp_openid` varchar(32) DEFAULT NULL COMMENT 'wechat小程序的openid',
  `weibo_openid` varchar(32) DEFAULT NULL COMMENT 'weibo的openid',
  `union_id` varchar(32) DEFAULT null comment '微信的统一id',
  `password` varchar(32) DEFAULT NULL COMMENT '密码',
  `salt` varchar(32) NOT NULL COMMENT '盐',
  `code`  varchar(16) DEFAULT NULL comment '手机冠号',
  `email` varchar(32)  COMMENT '邮箱',
  `avatar_url` text DEFAULT NULL COMMENT '头像',
  `role_id`     varchar(32) DEFAULT NULL comment '角色id',
  `is_vip` tinyint(1) NOT NULL DEFAULT '0' comment '是否是vip',
  `last_login_ip` varchar(64) DEFAULT NULL COMMENT '上次登录ip',
  `nickname` varchar(32) DEFAULT NULL COMMENT '用户名',
  `wechat_name` varchar(32) DEFAULT NULL COMMENT '微信用户名',
  `birthday` varchar(32) DEFAULT NULL COMMENT '生日',
  `gender` varchar(16) DEFAULT NULL COMMENT '性别',
  `location` varchar(255) DEFAULT NULL COMMENT '位置',
  `grade_dic_key` varchar(32) DEFAULT NULL COMMENT '年级字典Key',
  `student_province` varchar(32) DEFAULT NULL COMMENT '省',
  `student_city` varchar(32) DEFAULT NULL COMMENT '市',
  `student_town` varchar(32) DEFAULT NULL COMMENT '县城',
  `area` varchar(255) DEFAULT NULL COMMENT '所属区域',
  `instruct` varchar(200) DEFAULT NULL COMMENT '个人简介',
  `forbidden_words` tinyint(1) NOT NULL DEFAULT 0 COMMENT '禁言状态',
  `locked` tinyint(1) NOT NULL DEFAULT 0 COMMENT '锁定状态',
  `locked_time` datetime DEFAULT NULL COMMENT '锁定时间',
  `ua` text DEFAULT NULL COMMENT 'userAgent',
  `regist_from` varchar(16) DEFAULT NULL COMMENT ' 注册来源',
  `device_token` text DEFAULT NULL COMMENT '设备token',
  `vip_expired_time` datetime DEFAULT NULL COMMENT 'vip过期时间',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '用户表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `hot_search`;
CREATE TABLE `hot_search`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '用户类型：TYPE_USER, TYPE_STAFF, TYPE_ADMIN',
  `sort_order` int(11) DEFAULT 0 COMMENT '排序',
  `state` tinyint(1) DEFAULT 1 COMMENT '上线/下线',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '热搜表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `promotion`;
CREATE TABLE `promotion`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `title` varchar(50) DEFAULT NULL COMMENT '标题描述',
  `content` varchar(150) DEFAULT NULL COMMENT '内容描述',
  `img_url` varchar(512) DEFAULT NULL COMMENT '图片地址',
  `type` varchar(64) DEFAULT NULL COMMENT 'TYPE_HOME_PAGE',
  `action` varchar(64) DEFAULT NULL COMMENT '用户类型：TYPE_USER, TYPE_STAFF, TYPE_ADMIN',
  `state` tinyint(1) DEFAULT '1' COMMENT '上线/下线，默认上线',
  `order_by` int(5) DEFAULT '0' COMMENT '排序',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '推广表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for collection
-- ----------------------------
DROP TABLE IF EXISTS `topic`;
CREATE TABLE `topic`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `name` varchar(64) DEFAULT NULL COMMENT '专题名',
  `count` int(32) DEFAULT NULL COMMENT '视频数量',
  `price` int(11) DEFAULT 0 COMMENT '打包定价',
  `brief` varchar(255) NOT NULL COMMENT '打包定价',
  `sale_count` int(11) DEFAULT 0 COMMENT '售卖数量',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '视频专题' ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `video_topic`;
CREATE TABLE `video_topic`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `collection_id` varchar(32) DEFAULT NULL COMMENT '所属专题ID',
  `video_id` varchar(32) DEFAULT NULL COMMENT '视频ID',
  `sort_order` int(11) DEFAULT 0 COMMENT '定价',
  `created_time` datetime DEFAULT NULL ,
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '视频' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `video`;
CREATE TABLE `video`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `topic_id` varchar(32) DEFAULT NULL COMMENT '所属专题ID',
  `type` varchar(64) DEFAULT NULL COMMENT 'TYPE_PUTONGHUA普通话版本, TYPE_PINYIN, TYPE_ENGLISH',
  `vip_level` varchar(16) DEFAULT NULL COMMENT 'LEVEL_FREE, LEVEL_VIP',
  `name` varchar(64) DEFAULT NULL COMMENT '视频名',
  `brief` varchar(128) DEFAULT NULL COMMENT '视频简介',
  `content` longtext(500) DEFAULT NULL COMMENT '视频文字简介',
  `url` varchar(512) DEFAULT NULL COMMENT '视频url',
  `vid` varchar(64) DEFAULT NULL COMMENT '视频ID',
  `price` int(11) DEFAULT 0 COMMENT '定价',
  `teach_material` varchar(32) NOT NULL COMMENT '教材版本',
  `teach_subject` varchar(32) NOT NULL COMMENT '所属科目',
  `art_types` varchar(150) DEFAULT NULL COMMENT '艺术表现形式',
  `grade` varchar(50) DEFAULT NULL COMMENT '年级',
  `sale_count` int(11) DEFAULT 0 COMMENT '售卖数量',
  `play_count` int(11) DEFAULT 0 COMMENT '播放数量',
  `favorite_count` int(11) DEFAULT 0 COMMENT '收藏数量',
  `thumbs_up_count` int(11) DEFAULT 0 COMMENT '收藏数量',
  `is_hot` tinyint(1) DEFAULT 0 COMMENT '是否热门播放',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '视频' ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `video_extral_content`;
CREATE TABLE `video_extral_content`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `extral_content_id` varchar(32) DEFAULT NULL COMMENT '所属专题ID',
  `video_id` varchar(32) DEFAULT NULL COMMENT '视频ID',
  `sort_order` int(11) DEFAULT 0 COMMENT '定价',
  `created_time` datetime DEFAULT NULL ,
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '视频和视频额外内容关联' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `extral_content`;
CREATE TABLE `extral_content`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `video_id` varchar(32) DEFAULT NULL COMMENT '所属视频ID',
  `type` varchar(64) DEFAULT NULL COMMENT 'TYPE_PUTONGHUA普通话版本, TYPE_PINYIN, TYPE_ENGLISH',
  `vip_level` varchar(16) DEFAULT NULL COMMENT 'LEVEL_FREE, LEVEL_VIP',
  `name` varchar(64) DEFAULT NULL COMMENT '视频名',
  `brief` varchar(128) DEFAULT NULL COMMENT '视频简介',
  `url` int(512) DEFAULT 0 COMMENT '视频url',
  `vid` varchar(64) DEFAULT NULL COMMENT '视频ID',
  `price` int(11) DEFAULT 0 COMMENT '定价',
  `sale_count` int(11) DEFAULT 0 COMMENT '售卖数量',
  `play_count` int(11) DEFAULT 0 COMMENT '播放数量',
  `favorite_count` int(11) DEFAULT 0 COMMENT '收藏数量',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '额外视频相关内容' ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `video_category`;
CREATE TABLE `video_category`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `video_id` varchar(32) DEFAULT NULL COMMENT '视频ID',
  `grade_dic_key` varchar(32) DEFAULT NULL COMMENT '所属年级词典key',
  `publication_dic_key` varchar(32) DEFAULT NULL COMMENT '所属出版社词典key',
  `subject_dic_key` varchar(32) DEFAULT NULL COMMENT '所属科目词典key',
  `created_time` datetime DEFAULT NULL ,
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '视频分类标题' ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user_favorite`;
CREATE TABLE `user_favorite`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `video_id` varchar(32) DEFAULT NULL COMMENT '视频ID',
  `created_time` datetime DEFAULT NULL ,
  PRIMARY KEY (`id`)
) COMMENT = '用户收藏' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user_thumbs_up`;
CREATE TABLE `user_thumbs_up`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `video_id` varchar(32) DEFAULT NULL COMMENT '视频ID',
  `created_time` datetime DEFAULT NULL ,
  PRIMARY KEY (`id`)
) COMMENT = '用户点赞' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user_favorite`;
CREATE TABLE `user_favorite`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `video_id` varchar(32) DEFAULT NULL COMMENT '视频ID',
  `created_time` datetime DEFAULT NULL ,
  PRIMARY KEY (`id`)
) COMMENT = '用户收藏' ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for user
-- ----------------------------
/*DROP TABLE IF EXISTS `user_order`;
CREATE TABLE `user_order`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `purchaseable_id` varchar(32) DEFAULT NULL COMMENT '视频id或视频包id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `order_number` varchar(32) DEFAULT NULL COMMENT '视频ID',
  `pay_type` varchar(32) DEFAULT NULL COMMENT '支付类型：IAP，WEPAY，ALIPAY',
  `pay_time` datetime DEFAULT NULL COMMENT '支付时间',
  `price` int(11) DEFAULT NULL COMMENT '原价',
  `realpay` int(11) DEFAULT NULL COMMENT '实际支付金额',
  `redpackage_item_id` varchar(32) DEFAULT NULL COMMENT '使用优惠券ID',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '用户收藏' ENGINE=InnoDB DEFAULT CHARSET=utf8;*/


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `watch_record`;
CREATE TABLE `watch_record`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `video_id` varchar(32) DEFAULT NULL COMMENT '视频ID',
  `created_time` datetime DEFAULT NULL ,
  PRIMARY KEY (`id`)
) COMMENT = '用户观看记录' ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `type` varchar(32) DEFAULT NULL COMMENT '类型：TYPE_SYSTEM系统消息，TYPE_TO_USER定向用户消息',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `content` varchar(150) DEFAULT NULL COMMENT '通知内容',
  `action` varchar(155) DEFAULT NULL COMMENT '动作/链接',
  `is_read` tinyint(1) DEFAULT '0' COMMENT '已读未读',
  `data1` varchar(150) DEFAULT NULL COMMENT '附加信息1',
  `data2` varchar(150) DEFAULT NULL COMMENT '附加信息2',
  `created_time` datetime DEFAULT NULL ,
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '通知' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `video_comments`;
CREATE TABLE `video_comments`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `video_id` varchar(32) DEFAULT NULL COMMENT '所属视频ID',
  `parent_id` varchar(32) DEFAULT NULL COMMENT '父回复id，如果为空，表示没有父回复',
  `content` varchar(200) DEFAULT NULL COMMENT 'LEVEL_FREE, LEVEL_VIP',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '视频评论' ENGINE=InnoDB DEFAULT CHARSET=utf8;

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


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `red_packet_item`;
CREATE TABLE `red_package_item`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `redpackage_id` varchar(32) DEFAULT NULL COMMENT '红包ID',
  `mobile` varchar(32) DEFAULT NULL COMMENT '领取人手机号',
  `user_id` varchar(32) DEFAULT NULL COMMENT '领取人用户ID',
  `code` varchar(32) DEFAULT NULL COMMENT '红包兑换码',
  `type` varchar(64) DEFAULT NULL COMMENT '类型：TYPE_SHIKAN, TYPE_LIJIAN, TYPE_MANJIAN, TYPE_VIP',
  `subtype` varchar(64) DEFAULT NULL COMMENT '子类型：SUB_TYPE_CONSUME, SUB_TYPE_EXCHANGE, SUB_TYPE_CREATE',
  `name` varchar(32) DEFAULT NULL COMMENT '优惠券名',
  `create_amount` int(11) DEFAULT 0 COMMENT '创建数量',
  `receive_start` datetime DEFAULT NULL COMMENT '可领取开始时间',
  `receive_end` datetime DEFAULT NULL COMMENT '可领取结束时间',
  `use_start` datetime DEFAULT NULL COMMENT '使用开始时间',
  `use_end` datetime DEFAULT NULL COMMENT '使用结束时间',
  `max`  int(11) DEFAULT 0 COMMENT '每用户可拥有上限',
  `price`  int(11) DEFAULT 0 COMMENT '优惠券面值',
  `manjian_price`  int(11) DEFAULT 0 COMMENT '满减额度',
  `channel` varchar(32) NOT NULL COMMENT '渠道码',
  `duration` int(11) DEFAULT 0 COMMENT '有效时长，单位：秒',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '红包项表' ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback`  (
  `id` varchar(32) NOT NULL COMMENT 'id',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `content` varchar(512) DEFAULT NULL COMMENT '反馈内容',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系方式',
  `created_time` datetime DEFAULT NULL ,
  `updated_time` datetime DEFAULT NULL ,
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  `version` int(11) DEFAULT 1 COMMENT '乐观锁',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员',
  PRIMARY KEY (`id`)
) COMMENT = '红包项表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for permission
-- ----------------------------
DROP TABLE IF EXISTS `permission`;
CREATE TABLE `permission`  (
  `id` varchar(32) NOT NULL COMMENT '权限id',
  `parent_id` varchar(32) NOT NULL  comment '父级权限id',
  `code` varchar(64) NOT NULL COMMENT '权限识别码',
  `name` varchar(32) NOT NULL COMMENT '权限名',
  `menu` varchar(32) NOT NULL COMMENT '菜单名',
  `sort_order`   int(11) NOT NULL DEFAULT 0 comment '排序',
  `created_time` datetime DEFAULT  NULL COMMENT '创建时间',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  PRIMARY KEY (`id`)
)COMMENT='用户权限表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role
-- ----------------------------
DROP TABLE IF EXISTS `role`;
CREATE TABLE `role`  (
  `id` varchar(32) NOT NULL COMMENT '角色id',
  `code` varchar(64) NOT NULL COMMENT '角色识别码',
  `name` varchar(32) NOT NULL COMMENT '角色名',
  `created_time` datetime  DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime  DEFAULT NULL COMMENT '更新时间',
  `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
  PRIMARY KEY (`id`)
)COMMENT='用户角色表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_permission
-- ----------------------------
DROP TABLE IF EXISTS `role_permission`;
CREATE TABLE `role_permission`  (
  `role_id` varchar(32) NOT NULL COMMENT '角色id',
  `permission_id` varchar(32) NOT NULL COMMENT '权限id',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`role_id`, `permission_id`)
)COMMENT='用户角色权限关系表' ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- DROP TABLE IF EXISTS `wechat_config`;
-- CREATE TABLE `wechat_config` (
--   `id` varchar(32) NOT NULL ,
--   `appid`       varchar(64) DEFAULT NULL comment '微信appid',
--   `secret`       varchar(64) DEFAULT NULL comment '微信App密钥',
--   `token`       varchar(64) DEFAULT NULL comment '微信token',
--   `aes_key`       varchar(64) DEFAULT NULL comment '微信AES密钥',
--   `menu`       TEXT DEFAULT NULL comment '微信菜单',
--   `remark`     varchar(64) NOT NULL DEFAULT '' comment '备注',
--   `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
--   `deleted` tinyint(1) NOT NULL DEFAULT '0' comment '删除状态',
--   `version`     int(11) DEFAULT 1 comment '乐观锁',
--   `operator`     varchar(32)  comment '操作人员',
--   PRIMARY KEY (`id`)
-- )COMMENT='租户微信配置表' ENGINE=InnoDB DEFAULT CHARSET=utf8 ;


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

DROP TABLE IF EXISTS `common_order`;
CREATE TABLE `common_order`  (
  `id` varchar(32)NOT NULL,
  `order_number` varchar(32) DEFAULT NULL COMMENT '订单自定义号',
  `channel_code` varchar(32) DEFAULT NULL COMMENT '渠道码',
  `title` varchar(32) DEFAULT NULL COMMENT '订单标题',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `foreign_id` varchar(32) DEFAULT NULL COMMENT '外部类型id',
  `foreign_type` varchar(32) DEFAULT NULL COMMENT '外部类型名：VIP，VIDEO，TOPIC',
  `teach_material` varchar(30) DEFAULT NULL COMMENT '视频教材版本',
  `brief` varchar(150) DEFAULT NULL COMMENT '封面',
  `price` int(11) NOT NULL COMMENT '价格，单位分',
  `real_pay` int(11) NOT NULL COMMENT '实际支付价格，单位分',
  `redpackage_id` varchar(32) DEFAULT NULL COMMENT '是否使用优惠券，如果为空，表示未使用',
  `redpackage_value` varchar(32) DEFAULT NULL COMMENT '优惠券面值',
  `redpackage_name` varchar(50) DEFAULT NULL COMMENT '优惠券名称',
  `pay_type` varchar(32) DEFAULT NULL COMMENT '支付类型：支付宝/微信支付',
  `transaction_id` varchar(32) DEFAULT NULL COMMENT '三方支付平台交易流水号',
  `payment_successed_at` datetime DEFAULT NULL COMMENT '支付成功时间',
  `data` varchar(50) DEFAULT NULL COMMENT '附加数据',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员id',
  PRIMARY KEY (`id`)
) COMMENT = '通用订单' ENGINE = InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `red_packet`;
CREATE TABLE `red_packet`  (
  `id` varchar(32)NOT NULL,
  `name` varchar(50) NOT NULL COMMENT '优惠券名称',
  `description` varchar(120) DEFAULT NULL COMMENT '优惠券描述',
  `type` varchar(32) DEFAULT NULL COMMENT '优惠券类型',
  `sub_type` varchar(32) DEFAULT NULL COMMENT '应用子类型',
  `scenarios_type` varchar(32) DEFAULT NULL COMMENT '应用场景类型',
  `scenarios_ids` varchar(255) DEFAULT NULL COMMENT '应用场景下的id列表，以逗号为间隔',
  `create_count` int(11) DEFAULT NULL COMMENT '红包的创建数量，仅在subType为预生成类型和兑换类型时有效',
  `day_interval` int(11) DEFAULT NULL COMMENT '两次领取间隔的天数，仅在subType为无限类型红包时有效',
  `count` int(2) DEFAULT '1' COMMENT '每次领取红包数量',
  `amount` int(11) DEFAULT NULL COMMENT '红包抵扣金额',
  `page_url` varchar(150) DEFAULT NULL COMMENT '领取页面地址',
  `max_count` varchar(150) DEFAULT NULL COMMENT '最大领取数量',
  `usable_start_ime` datetime DEFAULT NULL COMMENT '可用开始时间',
  `usable_end_time` datetime DEFAULT NULL COMMENT '可用结束时间',
  `receive_start_time` datetime DEFAULT NULL COMMENT '领取时间',
  `receive_end_time` datetime DEFAULT NULL COMMENT '领取结束时间',
  `validate_period` int(11) DEFAULT NULL COMMENT '有效时长',
  `state` tinyint(1) DEFAULT '1' COMMENT '是否上线',
  `channel_name` varchar(50) DEFAULT NULL COMMENT '渠道名',
  `how_to_use` varchar(150) DEFAULT NULL COMMENT '使用说明',
  `note` varchar(150) DEFAULT NULL COMMENT '注意事项',
  `sub_list` varchar(255) DEFAULT NULL COMMENT '子券列表',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否已经删除',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员id',
  PRIMARY KEY (`id`)
) COMMENT = '优惠券' ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `red_packet_item`;
CREATE TABLE `red_packet_item`  (
  `id` varchar(32)NOT NULL,
  `template_id` varchar(50) NOT NULL COMMENT '红包模版ID',
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `mobile` varchar(11) DEFAULT NULL COMMENT ' 手机号码',
  `received_time` datetime DEFAULT NULL COMMENT '领取时间',
  `exchange_code` varchar(30) DEFAULT NULL COMMENT '兑换码',
  `state` varchar(20) DEFAULT 'NOT_RECEIVED' COMMENT '状态：已领取，未领取 ',
  `channel` varchar(32) DEFAULT NULL COMMENT '来自渠道',
  `usable_end_time` datetime DEFAULT NULL COMMENT '可用结束时间',
  `usable_start_time` datetime DEFAULT NULL COMMENT '可用开始时间',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` tinyint(1) DEFAULT '0' COMMENT '是否已经删除',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员id',
  PRIMARY KEY (`id`)
) COMMENT = '优惠券' ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_coin`;
CREATE TABLE `user_coin`  (
  `id` varchar(32)NOT NULL,
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `mobile` varchar(11) DEFAULT NULL COMMENT ' 手机号码',
  `coin_num` integer(11) DEFAULT 0 COMMENT ' 趣币数量',
  `updated_time` datetime DEFAULT NULL COMMENT '更新时间',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员id',
  PRIMARY KEY (`id`)
) COMMENT = '趣币' ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user_coin_record`;
CREATE TABLE `user_coin_record`  (
  `id` varchar(32)NOT NULL,
  `user_id` varchar(32) DEFAULT NULL COMMENT '用户ID',
  `mobile` varchar(11) DEFAULT NULL COMMENT ' 手机号码',
  `coin_num` integer(11) DEFAULT NULL COMMENT ' 当前趣币数量',
  `coin_num_record` integer(11) DEFAULT 0 COMMENT '趣币数量变动记录',
   `data1` varchar(32) DEFAULT NULL COMMENT '扩展字段',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员id',
  PRIMARY KEY (`id`)
) COMMENT = '趣币记录' ENGINE = InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `app_store`;
CREATE TABLE `app_store`  (
  `id` varchar(32)NOT NULL,
  `title` varchar(32) DEFAULT NULL COMMENT '商品名称',
  `price` int(11) DEFAULT NULL COMMENT '商品价格单位：分',
  `product_id` varchar(11) DEFAULT NULL COMMENT ' 商品ID',
  `productType` varchar(30) DEFAULT 0 COMMENT '商品类型',
  `order_by` integer(11) DEFAULT 0 COMMENT '排序',
  `created_time` datetime DEFAULT NULL COMMENT '创建时间',
  `operator` varchar(32) DEFAULT NULL COMMENT '操作人员id',
  PRIMARY KEY (`id`)
) COMMENT = 'appStore上架商品' ENGINE = InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `device_user`;
CREATE TABLE `device_user`  (
  `id` varchar(32)NOT NULL,
  `device_token` varchar(64) DEFAULT NULL COMMENT '设备token',
  `is_vip` tinyint(1) NOT NULL DEFAULT '0' comment '是否是vip',
  `vip_expired_time` datetime DEFAULT NULL COMMENT 'vip过期时间',
  PRIMARY KEY (`id`)
) COMMENT = '设备用户表' ENGINE = InnoDB DEFAULT CHARSET=utf8;