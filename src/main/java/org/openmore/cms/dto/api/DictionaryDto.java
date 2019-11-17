package org.openmore.cms.dto.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.openmore.common.annotation.ReferencedField;
import org.openmore.cms.entity.Dictionary;


@ApiModel("Dictionary显示模型")
public class DictionaryDto {
    @ApiModelProperty(value = "词典名，默认词典值的拼音")
    public String dicKey;
    @ApiModelProperty(value = "新词典名，用于更改词典名时使用，轻易不用改")
    public String newDicKey;
    @ApiModelProperty(value = "词典父id")
    public String parentKey;
    @ReferencedField(refClass = Dictionary.class, localField = "parentKey", conditionalField = "dic_key", selectField = "name")
    @ApiModelProperty(value = "父词典名")
    public String parentKeyName;
    @ApiModelProperty(value = "词典值最多8个汉字")
    public String name;
    /**
     * 英文描述
     */
    @ApiModelProperty(value = "英文描述")
    private String enName;
    @ApiModelProperty(value = "扩展数据字段，意义自定义，可作为备注，可作为特殊字段")
    public String data;

    @ApiModelProperty(value = "排序，默认0")
    public Integer sortOrder = 0;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建时间")
    public Date createdTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "最后更新时间")
    private Date updatedTime;

    @ApiModelProperty(value = "操作人员")
    public String operator;

    public String getNewDicKey() {
        return newDicKey;
    }

    public void setNewDicKey(String newDicKey) {
        this.newDicKey = newDicKey;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getParentKeyName() {
        return parentKeyName;
    }

    public void setParentKeyName(String parentKeyName) {
        this.parentKeyName = parentKeyName;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setDicKey(String dicKey) {
        this.dicKey = dicKey;
    }

    public String getDicKey() {
        return this.dicKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName;
    }
}