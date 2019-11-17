package org.openmore.cms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 * Created by org.openmore.cms
 * on 2017-07-23
 */

@ApiModel("账号密码登录模型")
public class UserPasswordDto {
    @ApiModelProperty(value = "用户名")
    public String username;
    @ApiModelProperty(value = "密码")
    public String passowrd;
    @ApiModelProperty(value = "验证码")
    public String captcha;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassowrd() {
        return passowrd;
    }

    public void setPassowrd(String passowrd) {
        this.passowrd = passowrd;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
}
