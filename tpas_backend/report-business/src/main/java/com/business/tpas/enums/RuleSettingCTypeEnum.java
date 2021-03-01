package com.business.tpas.enums;

import java.util.Arrays;

/**
 * @author dude
 * @version 1.0
 * @classname RUleSettingCType
 * @description 权值规则类型, 1 授课, 2 指导论文
 * @date 2021/3/1
 **/
public enum RuleSettingCTypeEnum {

    COURSE(1, "授课"),
    PAPER(2, "论文"),
    INTERN(3, "实习");

    private final Integer code;

    private final String value;

    RuleSettingCTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static RuleSettingCTypeEnum getEnumByCode(Integer code) {
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(COURSE);
    }

    public static RuleSettingCTypeEnum getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value.equals(value)).findFirst().orElse(COURSE);
    }

    public static boolean isExistByCode(Integer code) {
        return Arrays.stream(RuleSettingCTypeEnum.values()).anyMatch(it -> it.code.equals(code));
    }
}
