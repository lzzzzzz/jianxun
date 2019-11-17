package org.openmore.cms.dto.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;


@ApiModel("DeviceUser显示模型")
public class DeviceUserDto {
    @ApiModelProperty(value = "")
    public String id;
    @ApiModelProperty(value = "")
    public String deviceToken;
    @ApiModelProperty(value = "是否是VIP")
    public Boolean isVip;
    @ApiModelProperty(value = "VIP过期时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date vipExpiredTime;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
    public void setDeviceToken(String deviceToken){
        this.deviceToken = deviceToken;
    }

    public String getDeviceToken(){
        return this.deviceToken;
    }
    public void setIsVip(Boolean isVip){
        this.isVip = isVip;
    }

    public Boolean getIsVip(){
        return this.isVip;
    }
    public void setVipExpiredTime(Date vipExpiredTime){
        this.vipExpiredTime = vipExpiredTime;
    }

    public Date getVipExpiredTime(){
        return this.vipExpiredTime;
    }
}