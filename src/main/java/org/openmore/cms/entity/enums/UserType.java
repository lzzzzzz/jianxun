package org.openmore.cms.entity.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户类型
 */
public enum UserType implements BaseEnum<UserType, String> {
    USER("USER", "普通用户"),
    STAFF("STAFF", "员工"),
    MANAGER("MANAGER", "普通管理员"),
    ROOT("ROOT", "超级管理员");

    private String value;
    private String displayName;

    static Map<String, UserType> enumMap = new HashMap<String, UserType>();

    static {
        for (UserType type : UserType.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    private UserType(String value, String displayName) {
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

    public static UserType getEnum(String value) {
        return enumMap.get(value);
    }

}
