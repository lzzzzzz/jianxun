package org.openmore.cms.entity.enums;

import java.util.HashMap;
import java.util.Map;

public enum Language implements BaseEnum<Language, String> {
    EN("EN", "英文版"),
    ZH("ZH", "中文版");

    private String value;
    private String displayName;

    static Map<String, Language> enumMap = new HashMap<String, Language>();

    static {
        for (Language type : Language.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    private Language(String value, String displayName) {
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

    public static Language getEnum(String value) {
        return enumMap.get(value);
    }

}
