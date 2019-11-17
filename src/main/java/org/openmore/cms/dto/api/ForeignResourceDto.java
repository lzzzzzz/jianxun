package org.openmore.cms.dto.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.openmore.cms.entity.Dictionary;
import org.openmore.cms.entity.Resources;
import org.openmore.cms.entity.User;
import org.openmore.cms.entity.enums.ResourceForeignType;
import org.openmore.cms.entity.enums.ResourceType;
import org.openmore.common.annotation.ReferencedField;


@ApiModel("ForeignResource显示模型")
public class ForeignResourceDto {
    @ApiModelProperty(value = "关联资源ID")
    public String resourceId;
    @ApiModelProperty(value = "关联外部ID")
    public String foreignId;
    @ApiModelProperty(value = "文件适使用类型")
    public ResourceForeignType foreignType;
    @ApiModelProperty(value = "url需要拼接的参数")
    public String params;
    @ApiModelProperty(value = "")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createdTime;
    @ApiModelProperty(value = "")
    public String operator;
    @ApiModelProperty(value = "")
    public String id;

    @ApiModelProperty(value = "阿里云OSS保存key")
    @ReferencedField(refClass = Resources.class, localField = "resourceId", conditionalField = "id", selectField = "oss_key")
    public String key;
    @ApiModelProperty(value = "文件类型")
    @ReferencedField(refClass = Resources.class, localField = "resourceId", conditionalField = "id", selectField = "resource_type")
    public String type;
    @ApiModelProperty(value = "文件名称")
    @ReferencedField(refClass = Resources.class, localField = "resourceId", conditionalField = "id", selectField = "name")
    public String name;
    @ApiModelProperty(value = "文件大小")
    @ReferencedField(refClass = Resources.class, localField = "resourceId", conditionalField = "id", selectField = "size")
    public String size;
    /**时长*/
    @ApiModelProperty(value = "时长")
    @ReferencedField(refClass = Resources.class, localField = "resourceId", conditionalField = "id", selectField = "duration")
    private String duration;
    @ApiModelProperty(value = "文件访问url")
    @ReferencedField(refClass = Resources.class, localField = "resourceId", conditionalField = "id", selectField = "url")
    public String url;

    public void setResourceId(String resourceId){
        this.resourceId = resourceId;
    }

    public String getResourceId(){
        return this.resourceId;
    }
    public void setForeignId(String foreignId){
        this.foreignId = foreignId;
    }

    public String getForeignId(){
        return this.foreignId;
    }
    public void setForeignType(ResourceForeignType foreignType){
        this.foreignType = foreignType;
    }

    public ResourceForeignType getForeignType(){
        return this.foreignType;
    }
    public void setParams(String params){
        this.params = params;
    }

    public String getParams(){
        return this.params;
    }
    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }

    public Date getCreatedTime(){
        return this.createdTime;
    }
    public void setOperator(String operator){
        this.operator = operator;
    }

    public String getOperator(){
        return this.operator;
    }
    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}