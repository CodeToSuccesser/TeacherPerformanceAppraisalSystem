package com.management.common.enums;

import java.util.Arrays;

/**
 * @author dude
 * @version 1.0
 * @classname CourseTypeEnum
 * @description 课程类别/专业方向, 1 专业核心, 2 教师教育, 3 实践教学, 4 大类教育
 * @date 2021/1/31
 **/
public enum CourseTypeEnum {

    CORE(1, "专业方向课程"),

    NORMAL(2, "教师教育课程"),

    ACTIVE(3, "实践教学"),

    CLASS(4, "学科大类课程");

    private final Integer code;

    private final String value;

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    CourseTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public static CourseTypeEnum getEnumByCode(Integer code) {
        return Arrays.stream(values())
                .filter(it -> it.code.equals(code))
                .findFirst()
                .orElse(CORE);
    }

    public static CourseTypeEnum getEnumByValue(String value) {
        return Arrays.stream(values())
                .filter(it -> it.value.equals(value))
                .findFirst()
                .orElse(CORE);
    }
}
