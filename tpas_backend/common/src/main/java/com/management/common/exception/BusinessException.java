package com.management.common.exception;

import com.management.common.enums.ResponseCodeEnum;

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
        this.code = ResponseCodeEnum.EXCEPTION.code;
        this.msg = ResponseCodeEnum.EXCEPTION.msg;
    }

    public BusinessException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(ResponseCodeEnum responseCodeEnum) {
        this.code = responseCodeEnum.code;
        this.msg = responseCodeEnum.msg;
    }

    @Override
    public String toString() {
        return "BusinessException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

}