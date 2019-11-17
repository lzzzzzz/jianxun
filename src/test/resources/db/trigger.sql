DELIMITER $

-- ----------------------------
--  TRIGGER `insertUpdateStaffTalkNum`
--  增加销售人员沟通客户数量用于公平分配客户咨询
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

DROP TRIGGER IF EXISTS `add_device_user_coin`;
CREATE TRIGGER add_device_user_coin
AFTER INSERT ON user FOR EACH ROW
BEGIN
    -- 添加新普通用户趣币数量
    INSERT INTO user_coin(`id`, `user_id`, `coin_num`, `updated_time`, `operator`) values(REPLACE(uuid(), '-', ''), new.id,  0, CURRENT_TIME(), 'admin');
END $

DROP TRIGGER IF EXISTS `addVideoFavourite`;
CREATE TRIGGER addVideoFavourite
AFTER INSERT ON user_favourite FOR EACH ROW
BEGIN
    -- 增加收藏数量
   update video set favorite_count= favorite_count+1 where id = new.video_id;
END $

DROP TRIGGER IF EXISTS `addVideoThumbsUp`;
CREATE TRIGGER addVideoThumbsUp
AFTER INSERT ON user_thumbs_up FOR EACH ROW
BEGIN
    -- 增加点赞数量
   update video set thumbs_up_count= thumbs_up_count+1 where id = new.video_id;
END $

DROP TRIGGER IF EXISTS `delVideoFavourite`;
CREATE TRIGGER delVideoFavourite
AFTER DELETE ON user_favourite FOR EACH ROW
BEGIN
    -- 减少收藏数量
   update video set favorite_count= favorite_count-1 where id = old.video_id;
END $

DROP TRIGGER IF EXISTS `delVideoThumbsUp`;
CREATE TRIGGER delVideoThumbsUp
AFTER DELETE ON user_thumbs_up FOR EACH ROW
BEGIN
    -- 减少点赞数量
   update video set thumbs_up_count= thumbs_up_count-1 where id = old.video_id;
END $

DROP TRIGGER IF EXISTS `addVideoWatchNum`;
CREATE TRIGGER addVideoWatchNum
AFTER INSERT ON watch_record FOR EACH ROW
BEGIN
    -- 增加观看数量
   update video set play_count= play_count+1 where id = new.video_id;
END $

DROP TRIGGER IF EXISTS `addPageUrl`;
CREATE TRIGGER addPageUrl
AFTER INSERT ON red_packet FOR EACH ROW
BEGIN
    -- 增加领取地址
   update red_packet set page_url= CONCAT('https://qpf.lovek12.com/redpacket.html?id=',new.id);
END

DELIMITER ;