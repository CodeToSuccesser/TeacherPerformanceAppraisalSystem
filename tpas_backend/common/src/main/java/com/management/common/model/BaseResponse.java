package com.management.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.management.common.enums.ResponseCodeEnum;

import java.io.Serializable;

/**
 * @author dude
 * @version 1.0
 * @classname Response_2
 * @description 返回前端统一模板重构
 * @date 2020/7/20
 **/
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回状态码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回数据内容
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public BaseResponse() {
        this.code = ResponseCodeEnum.OK.code;
        this.msg = ResponseCodeEnum.OK.msg;
    }

    public BaseResponse(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public BaseResponse(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResponse(ResponseCodeEnum responseCodeEnum) {
        this.code = responseCodeEnum.code;
        this.msg = responseCodeEnum.msg;
    }

    public BaseResponse(T data) {
        this.code = ResponseCodeEnum.OK.getCode();
        this.msg = ResponseCodeEnum.OK.getMsg();
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}