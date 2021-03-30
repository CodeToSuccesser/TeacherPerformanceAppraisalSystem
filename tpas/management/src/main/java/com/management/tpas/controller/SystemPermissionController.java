package com.management.tpas.controller;

import com.github.pagehelper.PageInfo;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.model.BaseResponse;
import com.management.tpas.model.RoleSearchModel;
import com.management.tpas.model.SystemMenuModel;
import com.management.tpas.model.SystemMenusPermissionModel;
import com.management.tpas.model.SystemRoleModel;
import com.management.tpas.service.SystemMenuService;
import com.management.tpas.service.SystemRoleService;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname SystemRoleController
 * @description 系统权限相关接口
 * @date 2021/3/10
 **/
@Api(tags = {"系统权限相关接口"})
@RestController
@RequestMapping("/permission")
public class SystemPermissionController {

    private static final Logger logger = LoggerFactory.getLogger(SystemPermissionController.class);

    @Autowired
    private SystemMenuService menuService;

    @Autowired
    private SystemRoleService roleService;

    @PostMapping("/getAllRoles")
    @ApiOperation(value = "获取全部角色列表", notes = "用户管理页角色配置")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = SystemRoleModel.class),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> getAllRoles() {
        return new BaseResponse<>(roleService.getRoles());
    }

    @PostMapping("/queryRoles")
    @ApiOperation("获取角色列表")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = SystemRoleModel.class),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> querySystemRoles(@RequestBody RoleSearchModel searchModel) {
        if (searchModel == null) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        PageInfo<SystemRoleModel> data = roleService.queryRoles(searchModel);
        return new BaseResponse<>(data);
    }

    @PostMapping("/editRole")
    @ApiOperation("新增/编辑角色")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> editRole(@RequestBody SystemRoleModel role) {
        if (role == null || StringUtils.isBlank(role.getName())) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        roleService.editRole(role);
        return new BaseResponse<>();
    }

    @PostMapping("/deleteRole")
    @ApiOperation(value = "删除角色", notes = "name必传")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> deleteRole(@RequestBody SystemRoleModel role) {
        if (role == null || StringUtils.isBlank(role.getName())) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        roleService.deleteRole(role);
        return new BaseResponse<>();
    }

    @PostMapping("/queryMenus")
    @ApiOperation(value = "查询菜单")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = SystemMenusPermissionModel.class),
            @ApiResponse(code = 1, message = "-1 服务器内部异常")})
    public BaseResponse<?> queryMenus() {
        SystemMenusPermissionModel model = menuService.getMenuAndPermission();
        return new BaseResponse<>(model);
    }

}
