package com.business.tpas.enums;

import java.util.Arrays;

/**
 * @description 学生类型, 0 本科生, 1 专科生, 2 研究生
 **/
public enum StudentTypeEnum {
    UNDERGRADUATES(0, "本科生"),
    SPECIALIZED_SUBJECT(1, "专科生"),
    GRADUATE_STUDENT(2, "研究生");

    private final Integer code;

    private final String value;

    StudentTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static StudentTypeEnum getEnumByCode(Integer code) {
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(UNDERGRADUATES);
    }

    public static StudentTypeEnum getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value.equals(value)).findFirst().orElse(UNDERGRADUATES);
    }

    public static boolean isExistByCode(Integer code) {
        return Arrays.stream(StudentTypeEnum.values()).anyMatch(it -> it.code.equals(code));
    }
}
