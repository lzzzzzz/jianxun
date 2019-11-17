package org.openmore.cms.entity.enums;

import java.util.HashMap;
import java.util.Map;

/**APP类型*/
public enum AppType implements BaseEnum<AppType, String> {
    ANDROID_PHONE("ANDROID_PHONE", "安卓版"),
    IPHONE("IPHONE", "IOS版"),
    ANDROID_PAD("ANDROIDPAD", "安卓PAD"),
    IPAD("IPAD", "IOS PAD");

    private String value;
    private String displayName;

    static Map<String, AppType> enumMap = new HashMap<String, AppType>();

    static {
for (AppType type : AppType.values()) {
    enumMap.put(type.getValue(), type);
}
    }

    private AppType(String value, String displayName) {
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

    public static AppType getEnum(String value) {
return enumMap.get(value);
    }

}