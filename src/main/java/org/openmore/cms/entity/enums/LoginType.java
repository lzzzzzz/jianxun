package org.openmore.cms.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum LoginType implements BaseEnum<LoginType, String> {
    PASSWORD("PASSWORD","密码登录"),
    WECHAT("WECHAT","微信登录"),
    MINIAPP("MINIAPP","微信小程序登录"),
    QQ("QQ","QQ登录"),
    WEIBO("WEIBO","微博登录"),
    CAPTCHA("CAPTCHA","手机号验证码登录"),
    SOCKET("SOCKET","websocket登录订阅");

    private String value;
    private String displayName;

    static Map<String, LoginType> enumMap = new HashMap<String, LoginType>();

    static {
        for (LoginType type : LoginType.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    private LoginType(String value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    public static LoginType getEnum(String value) {
        return enumMap.get(value);
    }
}
