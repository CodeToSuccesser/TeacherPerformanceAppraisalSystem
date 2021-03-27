package com.management.tpas.enums;

import java.util.Arrays;

/**
 * @author dude
 * @version 1.0
 * @classname SystemPermissionControlTypeEnum
 * @description 权限控制类型 1列表(存在1时同菜单下存在其他查询框权限表示为必传)，2查询框，3按钮，4其他
 * @date 2021/3/27
 **/
public enum SystemPermissionControlTypeEnum {

    LIST(1, "列表"),
    SEARCH_FILED(2, "查询框"),
    BUTTON_FILED(3, "按钮"),
    OTHER(4, "其他")
    ;

    private final Integer code;

    private final String value;

    SystemPermissionControlTypeEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }

    public static SystemPermissionControlTypeEnum getEnumByCode(Integer code) {
        return Arrays.stream(values())
                .filter(it -> it.code.equals(code))
                .findFirst()
                .orElse(null);
    }

    public static SystemPermissionControlTypeEnum getEnumByValue(String value) {
        return Arrays.stream(values())
                .filter(it -> it.value.equals(value))
                .findFirst()
                .orElse(null);
    }

    public static boolean isExistByCode(Integer code) {
        return Arrays.stream(SystemPermissionControlTypeEnum.values()).anyMatch(it -> it.code.equals(code));
    }
}
