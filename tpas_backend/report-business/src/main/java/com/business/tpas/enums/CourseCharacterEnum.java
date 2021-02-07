package com.business.tpas.enums;

import java.util.Arrays;

/**
 * @author dude
 * @version 1.0
 * @classname CourseCharacterEnum
 * @description 课程性质enum, 0 必修, 1 选修
 * @date 2021/1/31
 **/
public enum CourseCharacterEnum {

    MUST_COURSE(0, "必修"),

    CHOOSE_COURSE(1, "选修")
    ;

    private final Integer code;

    private final String value;

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    CourseCharacterEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static CourseCharacterEnum getEnumByCode(Integer code) {
        return Arrays.stream(values())
                .filter(it -> it.code.equals(code))
                .findFirst()
                .orElse(MUST_COURSE);
    }

    public static CourseCharacterEnum getEnumByValue(String value) {
        return Arrays.stream(values())
                .filter(it -> it.value.equals(value))
                .findFirst()
                .orElse(MUST_COURSE);
    }

    public static boolean isExistByCode(Integer code) {
        for (CourseCharacterEnum tmp : CourseCharacterEnum.values()) {
            if (tmp.code.equals(code))
                return true;
        }
        return false;
    }
}
