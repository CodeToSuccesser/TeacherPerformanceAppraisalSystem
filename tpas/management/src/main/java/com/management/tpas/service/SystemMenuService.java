package com.management.tpas.service;

import com.management.common.base.BaseService;
import com.management.tpas.entity.SystemMenu;
import com.management.tpas.model.SystemMenusPermissionModel;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname SystemMenuService
 * @description 系统菜单权限管理
 * @date 2021/3/10
 **/
public interface SystemMenuService extends BaseService<SystemMenu> {

    SystemMenusPermissionModel getMenuAndPermission();
}
