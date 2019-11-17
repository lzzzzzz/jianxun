package org.openmore.cms.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * THIS IS AUTO GENERATED SOURCE CODE
 * Created by org.openmore.cms
 * on 2017-07-30
 */

@ApiModel("三方登录结构显示模型")
public class ThirdLoginDto {
    @ApiModelProperty(value = "三方平台名，微信：wechat，微博：weibo，QQ：qq")
    public String thirdPartyName;
    @ApiModelProperty(value = "微博不可用")
    public String openid;
    @ApiModelProperty(value = "微信专用统一id")
    public String unionid;
    @ApiModelProperty(value = "用户昵称")
    public String nickname;
    @ApiModelProperty(value = "性别")
    public String gender;
    @ApiModelProperty(value = "头像")
    public String avatarUrl;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getThirdPartyName() {
        return thirdPartyName;
    }

    public void setThirdPartyName(String thirdPartyName) {
        this.thirdPartyName = thirdPartyName;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getOpenid() {
        return this.openid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getUnionid() {
        return this.unionid;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getGender() {
        return this.gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
}
