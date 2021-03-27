package com.management.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname SystemMenusPermissionModel
 * @description 系统目录和权限信息
 * @date 2021/3/26
 **/
@ApiModel(value = "系统目录和权限信息")
public class SystemMenusPermissionModel {

    @ApiModelProperty(value = "目录列表")
    private List<SystemMenuModel> menuModelList;

    @ApiModelProperty(value = "权限列表")
    private List<SystemPermissionModel> permissionModelList;

    public List<SystemMenuModel> getMenuModelList() {
        return menuModelList;
    }

    public void setMenuModelList(List<SystemMenuModel> menuModelList) {
        this.menuModelList = menuModelList;
    }

    public List<SystemPermissionModel> getPermissionModelList() {
        return permissionModelList;
    }

    public void setPermissionModelList(List<SystemPermissionModel> permissionModelList) {
        this.permissionModelList = permissionModelList;
    }
}
