package org.openmore.cms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openmore.cms.entity.enums.UserType;

import java.util.Date;
import java.util.List;


/**
 * THIS IS AUTO GENERATED SOURCE CODE
 * Created by org.openmore.cms
 * on 2017-07-23
 */

@ApiModel("用户登录后返回用户模型")
public class UserProfileDto {
    @ApiModelProperty(value = "用户id")
    public String id;
    @ApiModelProperty(value = "微信openId")
    public String wxOpenId;
    @ApiModelProperty(value = "QQ openId")
    public String qqOpenId;
    @ApiModelProperty(value = "微博openId")
    public String wbOpenId;
    @ApiModelProperty(value = "小程序openId")
    public String miniAppOpenId;
    @ApiModelProperty(value = "登录sessionId")
    public String token;
    @ApiModelProperty(value = "用户名/昵称")
    public String username;
    @ApiModelProperty(value = "微信用户名/昵称")
    public String wechatName;
    @ApiModelProperty(value = "头像")
    public String avatarUrl;
    @ApiModelProperty(value = "性别：男/女")
    public String gender;
    @ApiModelProperty(value = "用户类型：超级管理员，工作人员，普通用户")
    public UserType type;
    @ApiModelProperty(value = "生日")
    public String birthday;
    @ApiModelProperty(value = "手机号（中间四位加密）")
    public String phone;
    @ApiModelProperty(value = "地址")
    public String location;
    @ApiModelProperty(value = "注册时间")
    public Date createdTime;
    @ApiModelProperty(value = "更新时间")
    public Date updatedTime;
    @ApiModelProperty(value = "是否设置密码")
    public Boolean hasPassword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWxOpenId() {
        return wxOpenId;
    }

    public void setWxOpenId(String wxOpenId) {
        this.wxOpenId = wxOpenId;
    }

    public String getQqOpenId() {
        return qqOpenId;
    }

    public void setQqOpenId(String qqOpenId) {
        this.qqOpenId = qqOpenId;
    }

    public String getWbOpenId() {
        return wbOpenId;
    }

    public void setWbOpenId(String wbOpenId) {
        this.wbOpenId = wbOpenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public Boolean getHasPassword() {
        return hasPassword;
    }

    public void setHasPassword(Boolean hasPassword) {
        this.hasPassword = hasPassword;
    }

    public String getMiniAppOpenId() {
        return miniAppOpenId;
    }

    public void setMiniAppOpenId(String miniAppOpenId) {
        this.miniAppOpenId = miniAppOpenId;
    }

    public String getWechatName() {
        return wechatName;
    }

    public void setWechatName(String wechatName) {
        this.wechatName = wechatName;
    }
}
