package org.openmore.cms.dao;

import org.apache.ibatis.annotations.Param;
import org.openmore.cms.dto.api.DictionaryDto;
import org.openmore.cms.entity.Dictionary;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface DictionaryMapper extends Mapper<Dictionary> {

    /**
     * 根据逗号间隔的词典key字段串，获得DictionaryDto
     */
    List<DictionaryDto> selectDicsByCommaString(@Param("commaString") String commaString);

    /**
     * 搜索二级菜单
     */
    List<DictionaryDto> selectSecondMenu(@Param("name") String name);
}