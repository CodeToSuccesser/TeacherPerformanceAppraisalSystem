package com.management.tpas.service;

import com.management.common.base.BaseService;
import com.management.tpas.entity.SystemPermission;
import com.management.tpas.model.SystemPermissionModel;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname SystemPermissionService
 * @description 系统菜单详细权限接口
 * @date 2021/3/26
 **/
public interface SystemPermissionService extends BaseService<SystemPermission> {

    List<SystemPermissionModel> getSystemPermissionList();
}
