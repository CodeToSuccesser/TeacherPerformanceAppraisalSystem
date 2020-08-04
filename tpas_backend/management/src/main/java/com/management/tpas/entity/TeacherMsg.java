package com.management.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 教师账号信息表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class TeacherMsg implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 教师姓名
     */
    private String teacherName;

    /**
     * 唯一登录名, 默认教师工号
     */
    private String logName;

    /**
     * 登录密码, 初始密码123456
     */
    private String logPassword;

    /**
     * 联系方式, 手机号/邮箱, 可为空
     */
    private String contact;

    /**
     * 头像图片url, 可为空
     */
    private String portrait;

    /**
     * 创建账号的管理员编号, 可为空
     */
    private Long adminId;

    /**
     * 账号逻辑删除标识, 0 无删除, 1 已删除
     */
    private Integer isDeleted;

    /**
     * 数据最新操作时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

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

    public String getLogPassword() {
        return logPassword;
    }

    public void setLogPassword(String logPassword) {
        this.logPassword = logPassword;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "TeacherMsg{" +
        "id=" + id +
        ", teacherName=" + teacherName +
        ", logName=" + logName +
        ", logPassword=" + logPassword +
        ", contact=" + contact +
        ", portrait=" + portrait +
        ", adminId=" + adminId +
        ", isDeleted=" + isDeleted +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
