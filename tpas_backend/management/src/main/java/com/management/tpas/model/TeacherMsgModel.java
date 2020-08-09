package com.management.tpas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Pietra
 * @version 1.0
 * @classname TeacherMsgModel
 * @description TODO
 * @date 2020/8/2
 **/
public class TeacherMsgModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String teacherName;

    private String logName;

    private String contact;

    private String portrait;

    private Long adminId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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

    @Override
    public String toString() {
        return "TeacherMsgModel{" +
                "id=" + id +
                ", teacherName='" + teacherName + '\'' +
                ", logName='" + logName + '\'' +
                ", contact='" + contact + '\'' +
                ", portrait='" + portrait + '\'' +
                ", adminId=" + adminId +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}
