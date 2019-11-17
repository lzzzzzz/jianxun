create database jianxun default character set utf8mb4 collate utf8mb4_general_ci;
create user 'user_lz'@'%' identified by 'lz666666';
grant all privileges on bnibt.* to 'user_lz'@'%';
flush  privileges;

-- ----------------------------
--  系统初始化数据
-- ----------------------------
BEGIN;

COMMIT;