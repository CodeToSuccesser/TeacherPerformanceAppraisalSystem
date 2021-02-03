package com.business.tpas.enums;

import java.util.Arrays;

/**
 * @description 是否双语授课,0 非双语, 1 双语
 **/
public enum IsBilingualEnum {
    NOT_BILINGUAL(0, "非双语言"),
    IS_BILINGUAL(1, "双语");

    private final Integer code;

    private final String value;

    IsBilingualEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static IsBilingualEnum getEnumByCode(Integer code) {
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(NOT_BILINGUAL);
    }

    public static IsBilingualEnum getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value.equals(value)).findFirst().orElse(NOT_BILINGUAL);
    }
}
