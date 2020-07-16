package com.management.common.entity;

import com.management.common.base.BaseResponse;

import java.io.Serializable;

/**
 * @author dude
 * @description 返回前端统一封装类
 * @date 2020/7/16
 **/
public class Response<T> extends BaseResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回状态码
     */
    private String code;

    /**
     * 返回消息
     */
    private String msg;

    /**
     * 返回数据内容
     */
    private T data;

    // TODO
}
