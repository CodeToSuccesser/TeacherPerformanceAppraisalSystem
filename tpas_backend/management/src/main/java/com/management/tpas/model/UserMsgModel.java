package com.management.tpas.model;

import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname UserMsgModel
 * @description TODO
 * @date 2020/8/9
 **/
public class UserMsgModel {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 管理员姓名
     */
    private String userName;

    /**
     * 唯一登录名, 默认教务员工号
     */
    private String logName;

    /**
     * 联系方式, 手机号/邮箱, 可为空
     */
    private String contact;

    /**
     * 头像图片url, 可为空
     */
    private String portrait;

    /**
     * 用户类型，0 教师， 1 管理员
     */
    private Integer userType;

    /**
     * 数据最新操作时间
     */
    private Date updateTime;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "UserMsgModel{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", logName='" + logName + '\'' +
                ", contact='" + contact + '\'' +
                ", portrait='" + portrait + '\'' +
                ", userType=" + userType +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}
