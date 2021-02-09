package com.business.tpas.enums;

import java.util.Arrays;

/**
 * @description 主讲辅讲枚举
 **/
public enum PrimarySecondaryEnum {

    PRIMARY(0, "主讲"),
    SECONDARY(1,"辅讲");

    private final Integer code;

    private final String value;

    PrimarySecondaryEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static PrimarySecondaryEnum getEnumByCode(Integer code) {
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(PRIMARY);
    }

    public static PrimarySecondaryEnum getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value.equals(value)).findFirst().orElse(PRIMARY);
    }

}
