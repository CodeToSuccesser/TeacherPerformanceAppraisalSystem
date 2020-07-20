package com.management.common.enums;

/**
 * @author dude
 * @description 返回到前端的统一状态枚举
 * code 状态码
 * msg 返回描述
 */
public enum ResponseCodeEnum {

    // TODO
    OK(200, "OK"),
    NO_PERMISSION(401, "No permission"),
    EXCEPTION(500, "系统错误"),
    JSON_CONVERT_ERROR(555, "JSON转换异常");

    public final int code;

    public final String msg;

    ResponseCodeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
