package org.openmore.cms.dao;

import org.apache.ibatis.annotations.Param;

import java.util.LinkedHashMap;
import java.util.List;

public interface SupperMapper {
    /**
     * 执行任意的sql语句
     * @param value
     * @return
     */
    List<LinkedHashMap<String, Object>> superSelect(@Param("value") String value);
}