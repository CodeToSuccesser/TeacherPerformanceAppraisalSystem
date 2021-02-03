package com.management.common.exception;

import com.management.common.enums.ErrorCodeEnum;

/**
 * @author dude
 * @version 1.0
 * @classname ServiceException
 * @description 自定义业务异常类
 * @date 2020/7/20
 **/
public class BusinessException extends RuntimeException {

    /**
     * @description 异常码
     **/
    public Integer code;

    /**
     * @description 异常消息
     **/
    public String msg;

    public BusinessException() {
        super();
        this.code = ErrorCodeEnum.EXCEPTION.code;
        this.msg = ErrorCodeEnum.EXCEPTION.msg;
    }

    public BusinessException(Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.code;
        this.msg = errorCodeEnum.msg;
    }

    public BusinessException(Integer code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

}