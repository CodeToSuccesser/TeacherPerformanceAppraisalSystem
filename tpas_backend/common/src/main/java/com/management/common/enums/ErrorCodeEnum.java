package com.management.common.enums;

/**
 * @author dude
 * @description 返回到前端的统一状态枚举
 * code 状态码
 * msg 返回描述
 */
public enum ErrorCodeEnum {

    // TODO
    OK(200, "OK"),
    NO_PERMISSION(401, "No permission"),
    EXCEPTION(500, "系统错误"),
    JSON_CONVERT_ERROR(555, "JSON转换异常"),
    DUPLICATE_OBJECT_EXIST(409,"logName已存在"),
    PARAM_IS_EMPTY(411, "请求参数为空"),
    PARAM_IS_WRONG(416, "请求参数有误");

    public final int code;

    public final String msg;

    ErrorCodeEnum(int code, String msg) {
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
