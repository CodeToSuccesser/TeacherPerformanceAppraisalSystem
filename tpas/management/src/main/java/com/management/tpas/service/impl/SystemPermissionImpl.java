package com.management.tpas.service.impl;

import com.management.common.base.BaseServiceImpl;
import com.management.tpas.dao.SystemPermissionMapper;
import com.management.tpas.entity.SystemPermission;
import com.management.tpas.model.SystemPermissionModel;
import com.management.tpas.model.SystemPermissionSearchModel;
import com.management.tpas.service.SystemPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.management.common.config.GlobalConst.SYSTEM_PERMISSIONS_KEY;

/**
 * @author dude
 * @version 1.0
 * @classname SystemPermissionImpl
 * @description 系统菜单详细权限 接口实现类
 * @date 2021/3/26
 **/
@Service
public class SystemPermissionImpl extends BaseServiceImpl<SystemPermissionMapper, SystemPermission> implements SystemPermissionService {

    @Resource
    private RedisTemplate<String, List<SystemPermissionModel>> redisTemplate;

    @Autowired
    private SystemPermissionMapper mapper;

    @Override
    public List<SystemPermissionModel> getSystemPermissionList() {
        List<SystemPermissionModel> permissionList;
        if (redisTemplate.hasKey(SYSTEM_PERMISSIONS_KEY) == Boolean.TRUE) {
            permissionList = redisTemplate.opsForValue().get(SYSTEM_PERMISSIONS_KEY);
        } else {
            permissionList = mapper.getSystemPermission(new SystemPermissionSearchModel());
            redisTemplate.opsForValue().set(SYSTEM_PERMISSIONS_KEY, permissionList, 1L, TimeUnit.DAYS);
        }
        return permissionList;
    }
}
