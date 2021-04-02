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

    COURSE(1, "授课", "course"),
    PAPER(2, "论文", "paper"),
    INTERN(3, "实习", "intern"),
    TASK(4, "任务", "task"),
    OTHER(5, "其他", "other");

    private final Integer code;

    private final String value;

    private final String path;

    RuleSettingCTypeEnum(Integer code, String value, String path) {
        this.code = code;
        this.value = value;
        this.path = path;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public String getPath() {
        return path;
    }

    public static RuleSettingCTypeEnum getEnumByCode(Integer code) {
        return Arrays.stream(values()).filter(it -> it.code.equals(code)).findFirst().orElse(COURSE);
    }

    public static RuleSettingCTypeEnum getEnumByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value.equals(value)).findFirst().orElse(COURSE);
    }

    public static RuleSettingCTypeEnum getEnumByPath(String path) {
        return Arrays.stream(values()).filter(it -> it.path.equals(path)).findFirst().orElse(null);
    }

    public static boolean isExistByCode(Integer code) {
        return Arrays.stream(RuleSettingCTypeEnum.values()).anyMatch(it -> it.code.equals(code));
    }

    public static boolean isExistByPath(String path) {
        return Arrays.stream(RuleSettingCTypeEnum.values()).anyMatch(it -> it.path.equals(path));
    }
}
