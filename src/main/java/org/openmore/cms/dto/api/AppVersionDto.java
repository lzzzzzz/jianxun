package org.openmore.cms.dto.api;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.openmore.cms.entity.enums.AppType;


@ApiModel("AppVersion显示模型")
public class AppVersionDto {
    @ApiModelProperty(value = "id")
    public String id;
    @ApiModelProperty(value = "app类型：ANDROID_PHONE，IPHONE，ANDROIDPAD, IPAD")
    public AppType type;
    @ApiModelProperty(value = "TYPE_HOME_PAGE")
    public String versionCode;
    @ApiModelProperty(value = "用户类型：TYPE_USER, TYPE_STAFF, TYPE_ADMIN")
    public String versionName;
    @ApiModelProperty(value = "下载地址")
    public String url;
    @ApiModelProperty(value = "")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date createdTime;
    @ApiModelProperty(value = "")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    public Date updatedTime;
    @ApiModelProperty(value = "删除状态")
    public boolean deleted;
    @ApiModelProperty(value = "乐观锁")
    public int version;
    @ApiModelProperty(value = "操作人员")
    public String operator;
    @ApiModelProperty(value = "是否强制更新")
    private Boolean needUpdate;

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return this.id;
    }
    public void setType(AppType type){
        this.type = type;
    }

    public AppType getType(){
        return this.type;
    }
    public void setVersionCode(String versionCode){
        this.versionCode = versionCode;
    }

    public String getVersionCode(){
        return this.versionCode;
    }
    public void setVersionName(String versionName){
        this.versionName = versionName;
    }

    public String getVersionName(){
        return this.versionName;
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
    public void setDeleted(boolean deleted){
        this.deleted = deleted;
    }

    public boolean getDeleted(){
        return this.deleted;
    }
    public void setVersion(int version){
        this.version = version;
    }

    public int getVersion(){
        return this.version;
    }
    public void setOperator(String operator){
        this.operator = operator;
    }

    public String getOperator(){
        return this.operator;
    }

    public Boolean getNeedUpdate() {
        return needUpdate;
    }

    public void setNeedUpdate(Boolean needUpdate) {
        this.needUpdate = needUpdate;
    }
}