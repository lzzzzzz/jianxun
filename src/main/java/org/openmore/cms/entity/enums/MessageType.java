package org.openmore.cms.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum MessageType implements BaseEnum<MessageType, String> {
    SYSTEM("SYSTEM", "系统消息"),
    CHAT("CHAT", "聊天消息"),
    JOIN("JOIN", "加入房间消息"),
    LEAVE("LEAVE", "离开消息");

    private String value;
    private String displayName;

    static Map<String, MessageType> enumMap = new HashMap<String, MessageType>();

    static {
        for (MessageType type : MessageType.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    private MessageType(String value, String displayName) {
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

    public static MessageType getEnum(String value) {
        return enumMap.get(value);
    }

}
