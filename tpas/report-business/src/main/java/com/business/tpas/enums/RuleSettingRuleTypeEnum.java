package com.business.tpas.enums;

import java.util.Arrays;

/**
 * @author dude
 * @version 1.0
 * @classname RuleSettingRuleTypeEnum
 * @description 规则类型, 1 值区分, 2 区间区分
 * @date 2021/3/2
 **/
public enum RuleSettingRuleTypeEnum {

    CONST(1, "值区分"),
    FIELD(2, "区间区分");

    private final Integer code;

    private final String value;

    RuleSettingRuleTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static RuleSettingRuleTypeEnum getEnumByCode(Integer code) {
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(CONST);
    }

    public static RuleSettingRuleTypeEnum getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value.equals(value)).findFirst().orElse(CONST);
    }

    public static boolean isExistByCode(Integer code) {
        return Arrays.stream(RuleSettingRuleTypeEnum.values()).anyMatch(it -> it.code.equals(code));
    }
}
