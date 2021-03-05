package com.business.tpas.enums;

import java.util.Arrays;

/**
 * @author dude
 * @version 1.0
 * @classname ParamsRulesValueTypeEnum
 * @description 权值参数取值类型 1 取字段值, 2 取参数权值
 * @date 2021/2/28
 **/
public enum ParamsRulesValueTypeEnum {

    COLUMN(1, "字段值"),
    CONST(2,"参数权值");

    private final Integer code;

    private final String value;

    ParamsRulesValueTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static ParamsRulesValueTypeEnum getEnumByCode(Integer code) {
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(COLUMN);
    }

    public static ParamsRulesValueTypeEnum getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value.equals(value)).findFirst().orElse(COLUMN);
    }

    public static boolean isExistByCode(Integer code) {
        return Arrays.stream(ParamsRulesValueTypeEnum.values()).anyMatch(it -> it.code.equals(code));
    }
}
