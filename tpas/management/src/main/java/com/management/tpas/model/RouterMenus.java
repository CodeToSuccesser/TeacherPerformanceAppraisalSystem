package com.management.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname RouterMenus
 * @description 目录菜单
 * @date 2021/3/11
 **/
@ApiModel("目录菜单")
public class RouterMenus {

    @ApiModelProperty(value = "web路径")
    private String path;

    @ApiModelProperty(value = "web字段")
    private Boolean alwaysShow;

    @ApiModelProperty(value = "web显示文本")
    private String name;

    @ApiModelProperty(value = "web配置")
    private Meta meta;

    @ApiModelProperty(value = "菜单等级，0第一级")
    private Integer level;

    @ApiModelProperty(value = "")
    private String parentValue;

    @ApiModelProperty(value = "子菜单")
    private List<RouterMenus> children;

    @ApiModelProperty(value = "权限")
    private List<SystemPermissionModel> permissionList;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Boolean getAlwaysShow() {
        return alwaysShow;
    }

    public void setAlwaysShow(Boolean alwaysShow) {
        this.alwaysShow = alwaysShow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<RouterMenus> getChildren() {
        return children;
    }

    public void setChildren(List<RouterMenus> children) {
        this.children = children;
    }

    public RouterMenus() {
        this.alwaysShow = null;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getParentValue() {
        return parentValue;
    }

    public void setParentValue(String parentValue) {
        this.parentValue = parentValue;
    }

    public List<SystemPermissionModel> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SystemPermissionModel> permissionList) {
        this.permissionList = permissionList;
    }
}