package org.openmore.cms.entity.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储文件分类
 */
public enum Platform implements BaseEnum<Platform, String> {
    ANDROID("ANDROID", "安卓手机"),
    ANDROIDPAD("ANDROIDPAD", "安卓pad"),
    IPHONE("IPHONE", "苹果手机"),
    IPAD("IPAD", "苹果pad"),
    MINIAPP("MINIAPP", "小程序"),
    OTHERS("OTHERS", "其它");

    private String value;
    private String displayName;

    static Map<String, Platform> enumMap = new HashMap<String, Platform>();

    static {
        for (Platform type : Platform.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    private Platform(String value, String displayName) {
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

    public static Platform getEnum(String value) {
        return enumMap.get(value);
    }
}
