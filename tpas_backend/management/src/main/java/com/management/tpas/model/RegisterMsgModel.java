package com.management.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author Pietra
 * @version 1.0
 * @classname RegisterMsgModel
 * @description 用户注册信息model
 * @date 2020/8/16
 **/
@ApiModel(value = "用户注册信息model", description = "用户注册信息model")
public class RegisterMsgModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户姓名")
    private String registerName;

    @ApiModelProperty(value = "登录名，唯一")
    private String logName;

    @ApiModelProperty(value = "登录密码")
    private String password;

    @ApiModelProperty(value = "注册用户类型")
    private Integer type;

    public String getRegisterName() {
        return registerName;
    }

    public void setRegisterName(String registerName) {
        this.registerName = registerName;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
