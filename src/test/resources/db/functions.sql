

-- 根据父结点，获得所有层级上的子节点
DELIMITER $
DROP FUNCTION IF EXISTS `getChildrenList`;
CREATE FUNCTION `getChildrenList`(pid varchar(128), tenantId varchar(128))
RETURNS text

BEGIN
DECLARE temp text default '';

WHILE pid is not null DO
	#判断是否是第一个,不是的话第一个会为空
        IF temp != '' THEN
	    SET temp = concat(temp, ',', pid);
	ELSE
	    SET temp = pid;
	END IF;
	SELECT group_concat(id) INTO pid FROM address where FIND_IN_SET(parent_id, pid)>0 and deleted = false and tenant_id = tenantId;
END WHILE;
RETURN temp;
END $

-- 根据叶子节点，获得所有父结点链
DROP FUNCTION IF EXISTS `getParentList`;
CREATE FUNCTION `getParentList`(leafId varchar(128), tenantId varchar(128))
RETURNS text

BEGIN
DECLARE temp text default '';

WHILE leafId is not null && leafId <> '' DO
	#判断是否是第一个,不是的话第一个会为空
        IF temp != '' THEN
	    SET temp = concat(temp, ',', leafId);
	ELSE
	    SET temp = leafId;
	END IF;
	SELECT parent_id INTO leafId FROM address where id = leafId and deleted = false and tenant_id = tenantId;
END WHILE;
RETURN temp;
END $

-- 统计指定活动的签到率
DROP FUNCTION IF EXISTS `getActivityCheckinRate`;
CREATE FUNCTION `getActivityCheckinRate`(articleId VARCHAR(32))
RETURNS DOUBLE
BEGIN
RETURN(select IFNULL(sum(if(ua.check_in=true,1,0)),0)/IFNULL(count(*),1) as point from user_activity as ua where ua.deleted=false and ua.article_id = articleId);
END $

DELIMITER ;