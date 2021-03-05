package com.management.common.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.management.common.enums.ErrorCodeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author dude
 * @version 1.0
 * @classname Response_2
 * @description 返回前端统一模板重构
 * @date 2020/7/20
 **/
@ApiModel(value = "返回前端统一模板", description = "返回前端统一模板")
public class BaseResponse<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回状态码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String msg;

    @ApiModelProperty(value = "返回数据内容")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public BaseResponse() {
        this.code = ErrorCodeEnum.OK.code;
        this.msg = ErrorCodeEnum.OK.msg;
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

    public BaseResponse(ErrorCodeEnum errorCodeEnum) {
        this.code = errorCodeEnum.code;
        this.msg = errorCodeEnum.msg;
    }

    public BaseResponse(T data) {
        this.code = ErrorCodeEnum.OK.getCode();
        this.msg = ErrorCodeEnum.OK.getMsg();
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