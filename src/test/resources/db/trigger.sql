DELIMITER $

-- ----------------------------
--  TRIGGER `insertUpdateStaffTalkNum`
--  增加销售人员沟通客户数量用于公平分配客户咨询
--  示例
-- ----------------------------
DROP TRIGGER IF EXISTS `insertUserCoinItem`;
CREATE TRIGGER insertUserCoinItem
AFTER INSERT ON user FOR EACH ROW
BEGIN
IF new.user_type = 'USER' THEN
-- 添加新普通用户趣币数量
INSERT INTO user_coin(`id`, `user_id`, `mobile`, `coin_num`, `updated_time`, `operator`)
    values(REPLACE(uuid(), '-', ''), new.id, new.phone, 0, CURRENT_TIME(), 'admin');
END IF;
END $

DELIMITER ;