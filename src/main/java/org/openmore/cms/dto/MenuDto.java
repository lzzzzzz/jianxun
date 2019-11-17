package org.openmore.cms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openmore.cms.dto.api.DictionaryDto;

import java.util.List;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 * Created by org.openmore.cms
 * on 2017-07-30
 */

@ApiModel("菜单")
public class MenuDto extends DictionaryDto{
    @ApiModelProperty(value = "下级子菜单")
    public List<MenuDto> children;

    public List<MenuDto> getChildren() {
        return children;
    }

    public void setChildren(List<MenuDto> children) {
        this.children = children;
    }
}
