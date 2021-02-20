package com.business.tpas.enums;

/**
 * @description 管理员处理结果, 默认0 未处理, 1 通过, 2 驳回
 **/
public enum CourseHoursModifyCheckResultEnum {

    PENDING(0, "未处理"),
    PASS(1, "通过"),
    REJECT(2, "驳回");

    private final Integer code;

    private final String value;

    CourseHoursModifyCheckResultEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
