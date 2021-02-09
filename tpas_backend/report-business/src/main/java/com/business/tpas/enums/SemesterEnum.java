package com.business.tpas.enums;

import java.util.Arrays;

/**
 * @description 学期枚举
 **/
public enum SemesterEnum {

    FIRST(0, "第一学期"),
    SECOND(1,"第二学期");

    private final Integer code;

    private final String value;

    SemesterEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static SemesterEnum getEnumByCode(Integer code) {
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(FIRST);
    }

    public static SemesterEnum getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value.equals(value)).findFirst().orElse(FIRST);
    }

    public static boolean isExistByCode(Integer code) {
        return Arrays.stream(SemesterEnum.values()).anyMatch(it -> it.code.equals(code));
    }
}
