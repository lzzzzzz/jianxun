package org.openmore.cms.entity.enums;

import java.util.HashMap;
import java.util.Map;

/**APP类型*/
public enum MessageDataType implements BaseEnum<MessageDataType, String> {
    TEXT("TEXT", "文字"),
    IMAGE("IMAGE", "图片"),
    AUDIO("AUDIO", "音频"),
    VIDEO("VIDEO", "录像");

    private String value;
    private String displayName;

    static Map<String, MessageDataType> enumMap = new HashMap<String, MessageDataType>();

    static {
for (MessageDataType type : MessageDataType.values()) {
    enumMap.put(type.getValue(), type);
}
    }

    private MessageDataType(String value, String displayName) {
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

    public static MessageDataType getEnum(String value) {
return enumMap.get(value);
    }

}