package com.management.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dude
 * @version 1.0
 * @classname SystemPermissionModel
 * @description 系统菜单详细权限
 * @date 2021/3/26
 **/
@ApiModel(value = "系统菜单详细权限")
public class SystemPermissionModel {

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
}
