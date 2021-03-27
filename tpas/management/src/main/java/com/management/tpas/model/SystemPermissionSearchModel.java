package com.management.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dude
 * @version 1.0
 * @classname SystemPermissionSearchModel
 * @description 系统具体权限查询类
 * @date 2021/3/26
 **/
@ApiModel(value = "系统具体权限查询类")
public class SystemPermissionSearchModel {

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
}
