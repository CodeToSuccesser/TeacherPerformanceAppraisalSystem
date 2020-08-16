package com.management.tpas.model;

import java.io.Serializable;

/**
 * @author Pietra
 * @version 1.0
 * @classname RegisterMsgModel
 * @description TODO
 * @date 2020/8/16
 **/
public class RegisterMsgModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * @description 用户姓名
     **/
    private String registerName;

    /**
     * @description 登录名，唯一
     **/
    private String logName;

    /**
     * @description 登录密码
     **/
    private String password;

    /**
     * @description 注册用户类型
     **/
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
