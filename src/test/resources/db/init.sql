create database bnibt default character set utf8mb4 collate utf8mb4_general_ci;
create user 'bnibt_user'@'%' identified by 'Bnibt@)!*';
grant all privileges on bnibt.* to 'bnibt_user'@'%';
flush  privileges;

-- ----------------------------
--  系统初始化数据
-- ----------------------------
BEGIN;
INSERT INTO `staff` VALUES ('staff_root_id', 'ROOT', 'root', '1f0c6a3ff04c9f9e806f183e8230897e', 'dc78e5ac14104828a5acf7074b7e905b', '13520664663', 'root@126.com', null, null, null, '', '', '', 0, 0, 0, null, null, '', 0,'2018-09-08 15:19:13', '2018-09-08 15:19:13', 0, null);
INSERT INTO `role` VALUES ('role_root_id', 'ROOT', '超级管理员', '2018-08-30 11:55:19', '2018-08-30 11:55:19', 0);
INSERT INTO `role_permission` VALUES ('role_root_id', '1', '2018-09-07 14:29:22');
INSERT INTO `permission` VALUES ('1', '', 'none', '数据看板', '', '2018-08-20 14:29:54', '2018-08-10 14:29:54', '0');

--教材版本父类型
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL', null, '教材', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');

--教材科目父类型
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_SUBJECT', null, '科目', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');

--视频艺术形式
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_ART_TYPES', null, '艺术类型', null, null, 0, '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');

--所有教材版本
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL_BUBIAN', 'TEACH_MATERIAL', '部编版', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL_RENJIAO', 'TEACH_MATERIAL', '人教版', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL_YUWEN', 'TEACH_MATERIAL', '语文版', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL_SUJIAO', 'TEACH_MATERIAL', '苏教版', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL_LUJIAO', 'TEACH_MATERIAL', '鲁教版', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL_BEISHIDA', 'TEACH_MATERIAL', '北师大版', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL_HUJIAO', 'TEACH_MATERIAL', '沪教版', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL_JIJIAO', 'TEACH_MATERIAL', '冀教版', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL_ZHEJIAO', 'TEACH_MATERIAL', '浙教版', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_MATERIAL_HEDA', 'TEACH_MATERIAL', '河大版', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');

--所有科目
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_SUBJECT_YUWEN', 'TEACH_SUBJECT', '语文', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_SUBJECT_SHUXUE', 'TEACH_SUBJECT', '数学', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');

--所有艺术类型
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_ART_TYPES_DONGHUA', 'TEACH_ART_TYPES', '动画', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_ART_TYPES_SHUICAI', 'TEACH_ART_TYPES', '水彩', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_ART_TYPES_GUOHUA', 'TEACH_ART_TYPES', '国画', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_ART_TYPES_PIYING', 'TEACH_ART_TYPES', '皮影', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');
insert into `dictionary` ( `dic_key`, `parent_key`, `name`, `en_name`,`data`, `sort_order`, `created_time`, `updated_time`, `deleted`, `version`, `operator`)
  values ( 'TEACH_ART_TYPES_CAIQIAN', 'TEACH_ART_TYPES', '彩铅', null, null, '0', '2017-08-05 22:54:07', '2017-08-05 22:54:07', 0, 1, 'administrator');

COMMIT;