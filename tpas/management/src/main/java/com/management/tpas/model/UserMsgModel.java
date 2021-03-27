package com.management.tpas.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname UserMsgModel
 * @description 用户信息model
 * @date 2020/8/9
 **/
@ApiModel(value = "用户信息model")
public class UserMsgModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long id = 0L;

    @ApiModelProperty(value = "管理员姓名")
    private String userName = "";

    @ApiModelProperty(value = "唯一登录名, 默认教务员工号")
    private String logName = "";

    @ApiModelProperty(value = "联系方式, 手机号/邮箱, 可为空")
    private String contact = "";

    @ApiModelProperty(value = "头像图片url, 可为空")
    private String portrait = "";

    @ApiModelProperty(value = "用户类型，0 教师， 1 管理员")
    private Integer userType = -1;

    @ApiModelProperty(value = "用户角色，用逗号拼接，如：role1,role2")
    private String rolesName = "";

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "数据最新操作时间")
    private Date updateTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String token = "";

    @ApiModelProperty(value = "权限菜单")
    private List<RouterMenus> routerMenuModels;

    @ApiModelProperty(value = "权限列表")
    private List<SystemPermissionModel> permissionList;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRolesName() {
        return rolesName;
    }

    public void setRolesName(String rolesName) {
        this.rolesName = rolesName;
    }

    public List<RouterMenus> getRouterMenuModels() {
        return routerMenuModels;
    }

    public void setRouterMenuModels(List<RouterMenus> routerMenuModels) {
        this.routerMenuModels = routerMenuModels;
    }

    public List<SystemPermissionModel> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SystemPermissionModel> permissionList) {
        this.permissionList = permissionList;
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
                ", rolesName='" + rolesName + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", token='" + token + '\'' +
                ", routerMenuModels=" + routerMenuModels +
                ", permissionList=" + permissionList +
                '}';
    }
}
