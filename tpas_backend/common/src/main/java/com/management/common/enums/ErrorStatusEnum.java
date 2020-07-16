package com.management.common.enums;

/**
 * @description 返回到前端的统一状态枚举
 * code 错误码
 * msg 错误描述
 * @author dude
 */
public enum ErrorStatusEnum {

    // TODO
    NO_PERMISSION("fail", "No permission");

    private final String code;
    private final String msg;

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ErrorStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
