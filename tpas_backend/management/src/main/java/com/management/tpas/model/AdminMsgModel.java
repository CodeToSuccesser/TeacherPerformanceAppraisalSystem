package com.management.tpas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname AdminMsgModel
 * @description 管理员账号信息model
 * @date 2020/8/9
 **/
@ApiModel(value = "管理员账号信息model", description = "管理员账号信息model")
public class AdminMsgModel {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "管理员姓名")
    private String adminName;

    @ApiModelProperty(value = "唯一登录名, 默认教务员工号")
    private String logName;

    @ApiModelProperty(value = "联系方式, 手机号/邮箱, 可为空")
    private String contact;

    @ApiModelProperty(value = "头像图片url, 可为空")
    private String portrait;

    @ApiModelProperty(value = "账号逻辑删除标识, 0 无删除, 1 已删除")
    private Integer isDeleted;

    @ApiModelProperty(value = "数据最新操作时间, yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    @ApiModelProperty(value = "数据新建时间, yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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
        return "AdminMsgModel{" +
                "id=" + id +
                ", adminName='" + adminName + '\'' +
                ", logName='" + logName + '\'' +
                ", contact='" + contact + '\'' +
                ", portrait='" + portrait + '\'' +
                ", isDeleted=" + isDeleted +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}
