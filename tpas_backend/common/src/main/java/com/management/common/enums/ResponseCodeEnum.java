package com.management.common.enums;

/**
 * @author dude
 * @description 返回到前端的统一状态枚举
 * code 状态码
 * msg 返回描述
 */
public enum ResponseCodeEnum {

    // TODO
    NO_PERMISSION(401, "No permission");

    private final int code;
    private final String msg;

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
