package com.management.tpas.model;

/**
 * @author dude
 * @version 1.0
 * @classname LoginMsgModel
 * @description 登录信息model
 * @date 2020/8/9
 **/
public class LoginMsgModel {

    private static final long serialVersionUID = 1L;

    /**
     * @description 登录名
     **/
    private String logName;

    /**
     * @description 登录密码
     **/
    private String logPassword;

    /**
     * @description 用户类型，0 教师， 1 管理员
     **/
    private Integer userType;

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getLogPassword() {
        return logPassword;
    }

    public void setLogPassword(String logPassword) {
        this.logPassword = logPassword;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "LoginMsgModel{" +
                "logName='" + logName + '\'' +
                ", logPassword='" + logPassword + '\'' +
                ", userType=" + userType +
                '}';
    }
}
