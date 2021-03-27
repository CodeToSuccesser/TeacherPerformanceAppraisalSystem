package com.management.tpas.service.impl;

import com.management.common.base.BaseServiceImpl;
import com.management.tpas.dao.SystemMenuMapper;
import com.management.tpas.entity.SystemMenu;
import com.management.tpas.model.SystemMenuModel;
import com.management.tpas.model.SystemMenusPermissionModel;
import com.management.tpas.model.SystemPermissionModel;
import com.management.tpas.service.SystemMenuService;
import com.management.tpas.service.SystemPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.management.common.config.GlobalConst.SYSTEM_MENUS_KEY;

/**
 * @author dude
 * @version 1.0
 * @classname SystemMenuImpl
 * @description 菜单权限管理
 * @date 2021/3/10
 **/
@Service
public class SystemMenuImpl extends BaseServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {

    @Autowired
    private SystemMenuMapper mapper;

    @Resource
    private RedisTemplate<String, List<SystemMenuModel>> redisTemplate;

    @Autowired
    private SystemPermissionService permissionService;

    @Override
    @Transactional(readOnly = true)
    public SystemMenusPermissionModel getMenuAndPermission() {
        List<SystemMenuModel> menuModelList;
        List<SystemPermissionModel> permissionModelList = permissionService.getSystemPermissionList();
        if (redisTemplate.hasKey(SYSTEM_MENUS_KEY) == Boolean.TRUE) {
            menuModelList = redisTemplate.opsForValue().get(SYSTEM_MENUS_KEY);
        } else {
            menuModelList = mapper.getMenu();
            redisTemplate.opsForValue().set(SYSTEM_MENUS_KEY, menuModelList, 1L, TimeUnit.DAYS);
        }
        SystemMenusPermissionModel data = new SystemMenusPermissionModel();
        data.setMenuModelList(menuModelList);
        data.setPermissionModelList(permissionModelList);
        return data;
    }
}
