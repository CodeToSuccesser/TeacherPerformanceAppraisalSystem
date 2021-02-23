package com.business.tpas.enums;

/**
 * @description 论文信息修改审批结果枚举
 **/
public enum PaperModifyCheckResultEnum {

    PENDING(0, "未处理"),
    PASS(1, "通过"),
    REJECT(2, "驳回");

    private final Integer code;

    private final String value;

    PaperModifyCheckResultEnum(Integer code, String value) {
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
