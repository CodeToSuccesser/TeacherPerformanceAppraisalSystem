package com.business.tpas.enums;

import java.util.Arrays;

/**
 * @description 软硬件课程, 0 软件, 1 硬件
 **/
public enum SoftHardEnum {
    SOFT(0, "软件"),
    HARD(1, "硬件");

    private final Integer code;

    private final String value;

    SoftHardEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static SoftHardEnum getEnumByCode(Integer code) {
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(SOFT);
    }

    public static SoftHardEnum getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value.equals(value)).findFirst().orElse(SOFT);
    }

    public static boolean isExistByCode(Integer code) {
        return Arrays.stream(SoftHardEnum.values()).anyMatch(it -> it.code.equals(code));
    }
}
