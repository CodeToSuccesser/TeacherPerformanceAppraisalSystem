package com.management.tpas.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname SystemPermission
 * @description 系统菜单详细权限表
 * @date 2021/3/26
 **/
@ApiModel(value = "系统菜单详细权限表")
public class SystemPermission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id = 0L;

    @ApiModelProperty(value = "权限名称")
    private String permissionName;

    @ApiModelProperty(value = "权限key")
    private String permissionKey;

    @ApiModelProperty(value = "菜单值")
    private String value;

    @ApiModelProperty(value = "控制路径，api路径")
    private String urlPath;

    @ApiModelProperty(value = "控制类型，1列表(存在1时同菜单下存在其他查询框权限表示为必传)，2查询框，3按钮，4其他")
    private Integer controlType;

    @ApiModelProperty(value = "控制类型为2时表示查询的字段")
    private String filedName;

    @ApiModelProperty(value = "备注")
    private String remark;

    private Date updateTime;

    private Date createTime;

    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    public String getPermissionKey() {
        return permissionKey;
    }

    public void setPermissionKey(String permissionKey) {
        this.permissionKey = permissionKey;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public void setUrlPath(String urlPath) {
        this.urlPath = urlPath;
    }

    public Integer getControlType() {
        return controlType;
    }

    public void setControlType(Integer controlType) {
        this.controlType = controlType;
    }

    public String getFiledName() {
        return filedName;
    }

    public void setFiledName(String filedName) {
        this.filedName = filedName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "SystemPermission{" +
                "id=" + id +
                ", permissionName='" + permissionName + '\'' +
                ", permissionKey='" + permissionKey + '\'' +
                ", value='" + value + '\'' +
                ", urlPath='" + urlPath + '\'' +
                ", controlType=" + controlType +
                ", filedName='" + filedName + '\'' +
                ", remark='" + remark + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
