package org.openmore.cms.entity.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 存储文件分类
 */
public enum ResourceType implements BaseEnum<ResourceType, String> {
    IMAGE("IMAGE", "图片"),
    AUDIO("AUDIO", "音频"),
    VIDEO("VIDEO", "视频"),
    OTHERS("OTHERS", "其它");

    private String value;
    private String displayName;

    static Map<String, ResourceType> enumMap = new HashMap<String, ResourceType>();

    static {
        for (ResourceType type : ResourceType.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    private ResourceType(String value, String displayName) {
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

    public static ResourceType getEnum(String value) {
        return enumMap.get(value);
    }
}
