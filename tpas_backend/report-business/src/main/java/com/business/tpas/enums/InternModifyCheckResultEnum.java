package com.business.tpas.enums;

/**
 * @description 实习带队信息审核结果枚举
 **/
public enum InternModifyCheckResultEnum {

    PENDING(0, "未处理"),
    PASS(1, "通过"),
    REJECT(2, "驳回");

    private final Integer code;

    private final String value;

    InternModifyCheckResultEnum(Integer code, String value) {
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
