package com.management.tpas.service;

import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;
import com.management.common.model.PageModel;
import com.management.tpas.entity.SystemRole;
import com.management.tpas.model.SystemRoleModel;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname SystemRoleService
 * @description 角色管理接口
 * @date 2021/3/10
 **/
public interface SystemRoleService extends BaseService<SystemRole> {

    PageInfo<SystemRoleModel> queryRoles(PageModel pageModel);

    List<SystemRoleModel> getRoles();

    void editRole(SystemRoleModel roleModel);

    void deleteRole(SystemRoleModel roleModel);
}
