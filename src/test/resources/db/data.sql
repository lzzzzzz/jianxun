BEGIN;
-- 工作人员类型
INSERT INTO `dictionary` VALUES ('USER_TYPE', '' , '人员类型',null, '',  0,'2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'USER_TYPE_USER', 'USER_TYPE', '普通用户', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'USER_TYPE_STAFF', 'USER_TYPE', '员工用户', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'USER_TYPE_STAFF_MANAGER', 'USER_TYPE_STAFF', '普通管理员',null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`,`en_name`, `data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'USER_TYPE_STAFF_ROOT', 'USER_TYPE_STAFF', '超级管理员', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'USER_TYPE_STAFF_SALES', 'USER_TYPE_STAFF', '销售员工', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');

--教材版本父类型
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'COURSE_TYPE', null, '课程父类型', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
--人工智能
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'COURSE_TYPE_AI', null, '人工智能', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
--艺术
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'COURSE_TYPE_ARTICLE', null, '艺术', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
--农耕
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'COURSE_TYPE_AGRICULTURE', null, '农耕', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
--实践
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'COURSE_TYPE_PRACTICE', null, '实践', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');




-- 语言支持
INSERT INTO `dictionary` VALUES ('LANGUAGE_REGION', '' , '语言', null,null,  0,'2018-12-08 22:54:07', '2018-12-08 22:54:07', 0, 1, 'administrator');
INSERT INTO `dictionary` VALUES ('EN', 'LANGUAGE_REGION' , '英文',null, null,  0,'2018-12-08 22:54:07', '2018-12-08 22:54:07', 0, 1, 'administrator');
INSERT INTO `dictionary` VALUES ('ZH', 'LANGUAGE_REGION' , '中文',null, null,  0,'2018-12-08 22:54:07', '2018-12-08 22:54:07', 0, 1, 'administrator');

-- INSERT INTO `staff` VALUES ('test_id', 'USER_TYPE_STAFF_ROOT', 'root', '1f0c6a3ff04c9f9e806f183e8230897e', 'dc78e5ac14104828a5acf7074b7e905b', '13520664663', 'root@126.com', 'role_root_id', null, null, null, null, '', '', '', 0, 0, null, null, '', 0, '2018-09-08 15:19:13', '2018-09-08 15:19:13', 0, null);
-- INSERT INTO `staff` VALUES ('staff_root_id', 'USER_TYPE_STAFF_ROOT', 'root', '1f0c6a3ff04c9f9e806f183e8230897e', 'dc78e5ac14104828a5acf7074b7e905b', '13520664663', 'root@126.com', 'role_root_id', null, null, null, null, '', '', '', 0, 0, null, null, '', 0, '2018-09-08 15:19:13', '2018-09-08 15:19:13', 0, null);
-- INSERT INTO `staff` VALUES ('staff_root_id2', 'USER_TYPE_STAFF_ROOT', 'root', '1f0c6a3ff04c9f9e806f183e8230897e', 'dc78e5ac14104828a5acf7074b7e905b', '13520664663', 'root@126.com', 'test_id', null, null, null, null, '', '', '', 0, 0, null, null, '', 0, '2018-09-08 15:19:13', '2018-09-08 15:19:13', 0, null);

INSERT INTO `role` VALUES ('role_root_id', 'ROOT', '超级管理员', '2018-08-30 11:55:19', '2018-08-30 11:55:19', 0);
insert into `role` ( `id`, `code`, `name`, `created_time`, `updated_time`, `deleted`) values ( 'test_id2', 'NORMAL_MANAGER', '普通管理员', '2017-12-27 22:54:07', '2017-12-27 22:54:07', '0');
INSERT INTO `role` VALUES ('test_id', 'test', 'test_name', '2017-08-05 22:54:07', '2017-08-05 22:54:07',  0);
INSERT INTO `role_permission` VALUES ('role_root_id', 'test_id', '2018-09-07 14:29:22');
-- INSERT INTO `permission` VALUES ('test_id', '', 'SHU_JU_KAN_BAN_GYCR', '数据看板', '', '2018-08-20 14:29:54', '2018-08-10 14:29:54', '0');
-- insert into `permission` ( `id`, `parent_id`, `code`, `name`, `menu_key`, `created_time`, `updated_time`, `deleted`) values ( 'test_id2', 'test_id', 'dashborad_show', '数据看板', 'SHU_JU_KAN_BAN_GYCR', '2019-01-06 02:20:16', '2019-01-06 02:20:16', '0');

-- 添加权限菜单
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`) values ( 'menu_cms_dashboard', 'MENU_TYPE', '数据看板', null,'http://img.yidazx.com/image/2019_01_06_01_02_40/cf0a8273-7bd3-47a9-be58-27887d662875', '9999', '2019-01-06 01:43:52', '2019-01-06 01:43:52', '0', '1', null);
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`) values ( 'menu_root', 'MENU_TYPE', '超级管理员', null,null, '0', '2019-01-06 01:43:52', '2019-01-06 01:43:52', '0', '1', null);
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`) values ( 'menu_system_manage', 'MENU_TYPE', '系统管理', null,'http://source.wenxiaoyou.com/test/nav_systerm.png', '0', '2019-01-06 01:43:52', '2019-01-06 01:43:52', '0', '1', null);
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`) values ( 'menu_user', 'MENU_TYPE', '用户管理', null,null, '0', '2019-01-06 01:43:52', '2019-01-06 01:43:52', '0', '1', null);
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`) values ( 'NEI_RONG_GUAN_LI_JTDR', 'MENU_TYPE', '内容管理', null,'http://img.yidazx.com/image/2019_01_06_01_11_42/2c1798f2-9e3e-4f07-821b-8f3dd12792e8', '9899', '2019-01-06 01:08:51', '2019-01-06 01:11:48', '0', '1', 'root');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`) values ( 'SHU_JU_KAN_BAN_GYCR', 'menu_cms_dashboard', '数据看板',null, '', '0', '2019-01-06 01:03:05', '2019-01-06 01:03:05', '0', '1', 'root');

INSERT INTO `user` ( `id`, `user_type`, `phone`, `qq_openid`, `wechat_openid`, `miniapp_openid`, `weibo_openid`, `union_id`, `password`, `salt`, `code`,
 `email`, `avatar_url`, `role_id`, `is_vip`, `last_login_ip`, `nickname`, `birthday`, `gender`, `location`, `grade_dic_key`, `student_province`,
 `student_city`, `student_town`, `area`, `instruct`, `forbidden_words`,`locked`,`locked_time`,`ua`,`regist_from`,`device_token`, `vip_expired_time`, `created_time`,`updated_time`,`deleted`,
 `version`,`operator`)
  values ( 'test_id', 'USER_TYPE_STAFF', '15831229772', '1f0c6a3ff04c9f9e806f183e8230897e', 'dc78e5ac14104828a5acf7074b7e905b', 'dc78e5ac14104828a5acf7074b7e905b',
 'dc78e5ac14104828a5acf7074b7e905b', 'test_id', '123456', '123', '123', 'test2@126.com','http://www.baidu.com','role_root_id', 0, '192.168.0.1', 'test','1926/12/12', '男',
 'test_id',  'test_id', 'beijing','beijing', 'beijing', 'beijing',null, 0,  0, null, null, null, null, '2019-01-16 16:29:12', '2019-01-16 16:29:12', '2019-01-16 16:29:12', 0, 1, 'test_id');

INSERT INTO `user` ( `id`, `user_type`, `phone`, `qq_openid`, `wechat_openid`, `miniapp_openid`,`weibo_openid`, `union_id`, `password`, `salt`, `code`,
 `email`, `avatar_url`, `role_id`, `is_vip`, `last_login_ip`, `nickname`, `wechat_name`,`birthday`, `gender`, `location`, `grade_dic_key`, `student_province`,
 `student_city`, `student_town`, `area`, `instruct`, `locked`,`locked_time`,`ua`,`regist_from`,`device_token`, `vip_expired_time`, `created_time`,`updated_time`,`deleted`,
 `version`,`operator`)
  values ( 'test_id2', 'USER_TYPE_USER', '12345678902', '1f0c6a3ff04c9f9e806f183e8230897e', 'dc78e5ac14104828a5acf7074b7e905b', 'test_id','dc78e5ac14104828a5acf7074b7e905b',
 'dc78e5ac14104828a5acf7074b7e905b', '123456', '123', '123', 'test2@126.com','http://www.baidu.com','test_id', 0, '192.168.0.1', 'test', '微信昵称','1926/12/12', '男',
 'test_id',  'test_id', 'beijing','beijing', 'beijing', 'beijing', null, 0,  null, null, null, null, '2019-01-16 16:29:12', '2019-01-16 16:29:12', '2019-01-16 16:29:12', 0, 1, 'test_id');

-- INSERT INTO `wechat_user` VALUES('test_id','test_id','test_id','test_id','http://abc.png', '张三','13520664663','100179237827389283', '2017-08-05', '男', '北京', '2017-08-05 22:54:07', '2017-08-05 22:54:07',0, 1,'tests');
-- INSERT INTO `wechat_user` VALUES('test_id2','','','test_id2','http://abc.png', '王五','13520664663','100179237827389283', '2017-08-05', '男', '北京', '2017-08-05 22:54:07', '2017-08-05 22:54:07',0, 1,'tests');
-- INSERT INTO `wechat_user` VALUES('test_id3','','','test_id3','http://abc.png', '李四','13520664663','100179237827389283', '2017-08-05', '男', '北京', '2017-08-05 22:54:07', '2017-08-05 22:54:07',0, 1,'tests');

INSERT INTO `app_version` VALUES('test_id','ANDROID_PHONE', '2.0','2.0版本','www.baidu.com','2017-08-05 22:54:07','2017-08-05 22:54:07',0,0,1,'tests');

INSERT INTO `operate_log` VALUES('test_id','test_id','111', '111', '2017-08-05 22:54:07', 'tests');
INSERT INTO `operate_log` VALUES('test_id2','test_id','111', '222', '2017-08-05 22:54:07', 'tests');

insert into `promotion` ( `id`, `title`, `img_url`, `type`, `action`, `state`, `order_by`, `created_time`, `updated_time`, `deleted`, `version`, `operator`) values ( 'test_id', 'test_title',
'https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1547736504954&di=dea584e3b6072a41b9449bdd662f525a&imgtype=0&src=http%3A%2F%2Fimgsrc.baidu.com%2Fimgad%2Fpic%2Fitem%2F9358d109b3de9c82036507ac6681800a19d84395.jpg',
 '测试内容', 'http://www.baidu.com',  1, 0, '2019-01-16 10:37:00', '2019-01-16 10:37:02', 0, 1, 'test_id');


insert into `resources` ( `id`, `oss_key`, `resource_type`, `name`, `size`,`url`, `duration`,`created_time`, `updated_time`, `deleted`, `version`, `operator`)
    values ( 'test_id', 'bfs/2019-01-14/1e587615f7634f02b9050c32551c6824.png', 'IMAGE', 'search_icon.png', '123', 'http://img.bnibt.com/static/1e587615f7634f02b9050c32551c6824.png', '12:00', '2019-01-14 11:18:14', '2019-01-14 11:18:14', '0', '1', 'userMichael');
insert into `resources` ( `id`, `oss_key`, `resource_type`, `name`, `size`,`url`, `duration`,`created_time`, `updated_time`, `deleted`, `version`, `operator`)
    values ( 'test_id2', 'bfs/2019-01-14/1e587615f7634f02b9050c32551c6824.png', 'IMAGE', 'search_icon.png', '123', 'http://img.bnibt.com/static/1e587615f7634f02b9050c32551c6824.png', '3:12', '2019-01-14 11:18:14', '2019-01-14 11:18:14', '0', '1', 'userMichael');

-- insert into `wechat_config` ( `id`, `appid`, `secret`, `token`, `aes_key`, `menu`, `remark`, `created_time`, `updated_time`, `deleted`, `version`, `operator`) values ( 'test_id', 'test_id', '测试', 'test_toke', 'test_id', '无', '无', '2019-01-17 00:27:22', '2019-01-17 00:27:24', '0', '1', 'test_id');

insert into `feedback` (`id`, `user_id`, `content`, `contact`,`created_time`, `updated_time`, `deleted`, `version`, `operator`)
 values('test_id', 'test_id', '界面非常好看', null, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'tests');

insert into `notice` (`id`, `type`, `user_id`, `content`, `action`, `is_read`, `data1`, `data2`, `created_time`, `operator`)
 values ('test_id', 'TYPE_SYSTEM', null, 'test content', 'http://www.baidu.com', 0, null, null, '2017-08-05 22:54:07', 'test');

insert into `hot_search` (`id`, `name`, `sort_order`, `state`,`created_time`, `updated_time`, `deleted`, `version`, `operator`)
 values ('test_id', 'test_name', null, 1,'2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'tests');

insert into `topic` (`id`, `name`, `count`, `price`, `brief`, `sale_count`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
 values ('test_id', 'test topic', 1, 1, 'http:www.baidu.com', 1, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'tests');
insert into `topic` (`id`, `name`, `count`, `price`, `brief`, `sale_count`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
 values ('test_id2', 'test topic', 1, 1, 'http:www.baidu.com', 1, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'tests');

insert into `foreign_resource` (`id`, `resource_id`, `foreign_id`, `foreign_type`, `params`, `created_time`, `operator`)
 values ('test_id', 'test_id', 'test_id', 'BANNER', 'aa',  '2017-08-05 22:54:07','tests');

insert into `video`(`id`, `topic_id`, `type`, `vip_level`, `name`, `brief`, `content`,`url`, `vid`, `price`,
    `teach_material`, `teach_subject`, `art_types`, `grade`,`sale_count`, `play_count`,
    `favorite_count`, `thumbs_up_count`, `is_hot`,`created_time`, `updated_time`, `deleted`, `version`, `operator`)
    values ('test_id', 'test_id', 'YPE_PUTONGHUA', 'LEVEL_FREE', 'test name', '256', 'https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=9e5403dd75f08202399f996d2a929088/3812b31bb051f819b507e4b4dcb44aed2e73e72a.jpg',
    'content','123456', 125, 'test_id',  'test_id', 'test_id', 'test_grade',5, 9, 19, 2, 0,'2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'tests');
insert into `video`(`id`, `topic_id`, `type`, `vip_level`, `name`, `brief`, `content`,`url`, `vid`, `price`,
    `teach_material`, `teach_subject`, `art_types`, `grade`,`sale_count`, `play_count`,
    `favorite_count`, `thumbs_up_count`, `is_hot`,`created_time`, `updated_time`, `deleted`, `version`, `operator`)
    values ('test_id2', 'test_id', 'YPE_PUTONGHUA', 'LEVEL_FREE', 'test name', '256', 'https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/c0%3Dbaike116%2C5%2C5%2C116%2C38/sign=9e5403dd75f08202399f996d2a929088/3812b31bb051f819b507e4b4dcb44aed2e73e72a.jpg',
    'content','123456', 125, 'test_id',  'test_id', 'test_id', 'test_grade',5, 9, 19, 2, 0,'2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'tests');

insert into `video_comments` (`id`, `user_id`, `video_id`, `parent_id`, `content`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
    values ('test_id', 'test_id', 'test_id', 'test_string', 'test content', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'tests');

insert into `user_favorite`(`id`, `user_id`, `video_id`, `created_time`) values('test_id', 'test_id', 'test_id', '2017-08-05 22:54:07');
insert into `user_favorite`(`id`, `user_id`, `video_id`, `created_time`) values('test_id2', 'test_id2', 'test_id', '2017-08-05 22:54:07');

insert into `user_thumbs_up`(`id`, `user_id`, `video_id`, `created_time`) values('test_id', 'test_id', 'test_id', '2017-08-05 22:54:07');


insert into `watch_record`(`id`, `user_id`, `video_id`, `created_time`) values('test_id', 'test_id', 'test_id', '2017-08-05 22:54:07');
insert into `watch_record`(`id`, `user_id`, `video_id`, `created_time`) values('test_id2', 'test_id2', 'test_id', '2017-08-05 22:54:07');

insert into `common_order`(`id`, `order_number`, `channel_code`, `title`, `user_id`, `foreign_id`, `foreign_type`, `teach_material`,`brief`,`price`, `real_pay`, `redpackage_id`,
 `redpackage_value`, `redpackage_name`,`pay_type`, `transaction_id`, `payment_successed_at`, `data`,`created_time`, `operator`)
  values('test_id', 'terst_number', 'test_code', 'test_title', 'test_id', 'test_id', 'VIDEO', 'TEACH_MATERIAL_BUBIAN','http://www.baidu.com',10, 5, null, null, null,
     'ALIPAY', 'test_id', '2017-08-05 22:54:07', 'test1', '2017-08-05 22:54:07', 'tests');
insert into `common_order`(`id`, `order_number`, `channel_code`, `title`, `user_id`, `foreign_id`, `foreign_type`, `price`, `real_pay`, `redpackage_id`,
 `redpackage_value`, `pay_type`, `transaction_id`, `payment_successed_at`, `created_time`, `operator`)
  values('test_id2', 'terst_number', 'test_code', 'test_title', 'test_id', 'test_id2', 'VIDEO', 10, 5, null, null,
     'ALIPAY', 'test_id', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 'tests');

insert into `red_packet`(`id`, `name`, `type`, `count`, `amount`, `created_time`, `deleted`, `operator`)
    values('test_id', 'test redpacket', 'DEFAULT', 1, 100, '2017-08-05 22:54:07', 0, 'tests');

insert into `red_packet_item`(`id`, `template_id`, `user_id`, `mobile`, `usable_end_time`, `usable_start_time`, `created_time`, `deleted`, `operator`)
    values('test_id', 'test_id', 'test_id', '1234567890', '2017-08-05 22:54:07', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 'tests');

insert into `user_coin`(`id`, `user_id`, `mobile`, `coin_num`, `updated_time`, `operator`)
    values('test_id', 'test_id1', '123456', 10, '2017-08-05 22:54:07', 'tests');

insert into `user_coin_record`(`id`, `user_id`, `mobile`, `coin_num`, `coin_num_record`, `data1`, `created_time`, `operator`)
    values('test_id', 'test_id1', '123456', 9, -1, null, '2017-08-05 22:54:07', 'tests');

insert into `app_store`(`id`, `title`, `price`, `product_id`, `productType`, `order_by`, `created_time`, `operator`)
    values('test_id', '测试商品1', 10, 'test_id', 'COIN', 0, '2017-08-05 22:54:07', 'tests');

insert into `app_store`(`id`, `title`, `price`, `product_id`, `productType`, `order_by`, `created_time`, `operator`)
    values('test_id2', '测试商品1', 10, 'test_id', 'COIN', 0, '2017-08-05 22:54:07', 'tests');

insert into `device_user`(`id`, `device_token`, `is_vip`, `vip_expired_time`)
    values('test_id', '123', 0, null);
COMMIT;