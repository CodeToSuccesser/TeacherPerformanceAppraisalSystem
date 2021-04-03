package com.business.tpas.enums;

import java.util.Arrays;

/**
 * @description 任务状态枚举
 **/
public enum TaskStateEnum {

    GOING(0, "进行中"),
    FINISHED(1, "已截止"),
    DELETED(2, "已删除/已取消");

    private final Integer code;

    private final String value;

    TaskStateEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static TaskStateEnum getEnumByCode(Integer code) {
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(GOING);
    }

    public static TaskStateEnum getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value.equals(value)).findFirst().orElse(GOING);
    }

    public static boolean isExistByCode(Integer code) {
        return Arrays.stream(TaskStateEnum.values()).anyMatch(it -> it.code.equals(code));
    }
}
