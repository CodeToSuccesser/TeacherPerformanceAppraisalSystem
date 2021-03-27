package com.management.tpas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname SystemRolePermissionRef
 * @description 系统角色权限关系表
 * @date 2021/3/26
 **/
@ApiModel(value = "系统角色权限关系表")
public class SystemRolePermissionRef implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id = 0L;

    @ApiModelProperty(value = "权限key")
    private String permissionKey;

    @ApiModelProperty(value = "角色名称")
    private String roleName;

    @ApiModelProperty(value = "菜单值")
    private String value;

    private Date updateTime;

    private Date createTime;

    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "SystemRolePermissionRef{" +
                "id=" + id +
                ", permissionKey='" + permissionKey + '\'' +
                ", roleName='" + roleName + '\'' +
                ", value='" + value + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
