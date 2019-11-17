package org.openmore.cms.dto.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.openmore.cms.entity.enums.ResourceType;


@ApiModel("Resources显示模型")
public class ResourcesDto {
    @ApiModelProperty(value = "")
    public String id;
    @ApiModelProperty(value = "阿里云OSS保存key")
    public String key;
    @ApiModelProperty(value = "文件类型")
    public ResourceType type;
    @ApiModelProperty(value = "文件类型名")
    public String typeName;
    @ApiModelProperty(value = "文件名称")
    public String name;
    @ApiModelProperty(value = "文件大小")
    public String size;
    /**时长*/
    @ApiModelProperty(value = "时长")
    private String duration;
    @ApiModelProperty(value = "文件访问url")
    public String url;
    @ApiModelProperty(value = "创建时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createdTime;
    @ApiModelProperty(value = "更新时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updatedTime;
    @ApiModelProperty(value = "操作人员id")
    public String operator;

    public String getTypeName() {
        if(null==type){
            return null;
        }
        return type.getDisplayName();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
    public void setKey(String key){
        this.key = key;
    }

    public String getKey(){
        return this.key;
    }
    public void setType(ResourceType type){
        this.type = type;
    }

    public ResourceType getType(){
        return this.type;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getName(){
        return this.name;
    }
    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }
    public void setCreatedTime(Date createdTime){
        this.createdTime = createdTime;
    }

    public Date getCreatedTime(){
        return this.createdTime;
    }
    public void setUpdatedTime(Date updatedTime){
        this.updatedTime = updatedTime;
    }

    public Date getUpdatedTime(){
        return this.updatedTime;
    }
    public void setOperator(String operator){
        this.operator = operator;
    }

    public String getOperator(){
        return this.operator;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}