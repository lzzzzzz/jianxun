package org.openmore.cms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.openmore.cms.dto.api.GisInfo;
import org.openmore.cms.entity.enums.LoginType;

import java.util.List;

@ApiModel("用户登录模型")
public class LoginParams {

    @ApiModelProperty(value = "登录类型")
    public LoginType type;
    @ApiModelProperty(value = "三方openId")
    public String openid;
    @ApiModelProperty(value = "邮件")
    public String email;
    @ApiModelProperty(value = "电话冠号")
    public String code;
    @ApiModelProperty(value = "手机号")
    public String phone;
    @ApiModelProperty(value = "密码")
    public String password;
    @ApiModelProperty(value = "设备token")
    public String deviceToken;
    @ApiModelProperty(value = "手机校验码")
    public String captcha;
    @ApiModelProperty(value = "图片校验码")
    public String imgCaptcha;

    @ApiModelProperty(value = "位置列表，最多10个")
    public List<GisInfo.Point> locationList;

    public String getImgCaptcha() {
        return imgCaptcha;
    }

    public void setImgCaptcha(String imgCaptcha) {
        this.imgCaptcha = imgCaptcha;
    }

    public List<GisInfo.Point> getLocationList() {
        return locationList;
    }

    public LoginType getType() {
        return type;
    }

    public void setType(LoginType type) {
        this.type = type;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLocationList(List<GisInfo.Point> locationList) {
        this.locationList = locationList;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
