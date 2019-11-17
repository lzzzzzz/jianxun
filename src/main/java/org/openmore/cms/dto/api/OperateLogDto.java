package org.openmore.cms.dto.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


@ApiModel("OperateLog显示模型")
public class OperateLogDto {
    @ApiModelProperty(value = "")
    public String id;
    @ApiModelProperty(value = "工作人员id")
    public String staffId;
    @ApiModelProperty(value = "操作人ip")
    public String ipAddress;
    @ApiModelProperty(value = "日志内容")
    public String content;
    @ApiModelProperty(value = "")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createdTime;
    @ApiModelProperty(value = "操作人员id")
    public String operator;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
    public void setStaffId(String staffId){
        this.staffId = staffId;
    }

    public String getStaffId(){
        return this.staffId;
    }
    public void setIpAddress(String ipAddress){
        this.ipAddress = ipAddress;
    }

    public String getIpAddress(){
        return this.ipAddress;
    }
    public void setContent(String content){
        this.content = content;
    }

    public String getContent(){
        return this.content;
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
}