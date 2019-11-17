package org.openmore.cms.entity.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储文件分类
 */
public enum ResourceForeignType implements BaseEnum<ResourceForeignType, String> {
    BANNER("BANNER", "banner展示/视频/图片"),
    INSTRUCT("INSTRUCT", "课程介绍"),
    TOPIC("TOPIC", "专题使用"),
    OTHERS("OTHERS", "其它");

    private String value;
    private String displayName;

    static Map<String, ResourceForeignType> enumMap = new HashMap<String, ResourceForeignType>();

    static {
        for (ResourceForeignType type : ResourceForeignType.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    private ResourceForeignType(String value, String displayName) {
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

    public static ResourceForeignType getEnum(String value) {
        return enumMap.get(value);
    }
}
