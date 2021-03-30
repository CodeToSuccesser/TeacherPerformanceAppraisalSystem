package com.management.tpas.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.CommonUtil;
import com.management.tpas.dao.*;
import com.management.tpas.entity.SystemRole;
import com.management.tpas.entity.SystemRolePermissionRef;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.model.*;
import com.management.tpas.service.SystemMenuService;
import com.management.tpas.service.SystemPermissionService;
import com.management.tpas.service.SystemRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.management.common.config.GlobalConst.SYSTEM_ROLE_KEY;

/**
 * @author dude
 * @version 1.0
 * @classname SystemRoleImpl
 * @description 角色管理接口实现
 * @date 2021/3/10
 **/
@Service
public class SystemRoleImpl extends BaseServiceImpl<SystemRoleMapper, SystemRole> implements SystemRoleService {

    @Autowired
    private SystemRoleMapper systemRoleMapper;

    @Autowired
    private SystemMenuMapper systemMenuMapper;

    @Autowired
    private UserMsgMapper userMsgMapper;

    @Autowired
    private SystemRolePermissionRefMapper refMapper;

    @Autowired
    private SystemPermissionService systemPermissionService;

    @Resource
    private RedisTemplate<String, List<SystemRoleModel>> redisTemplate;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<SystemRoleModel> queryRoles(RoleSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<SystemRoleModel> list = systemRoleMapper.getRoles(searchModel);
        if (list.isEmpty()) {
            return new PageInfo<>(list);
        }
        PageHelper.clearPage();
        List<SystemRolePermissionRef> refList = refMapper.getRefByRoleName(
                list.stream().map(SystemRoleModel::getName).collect(Collectors.toList()));
        Map<String, List<SystemRolePermissionRef>> refMap = refList.stream()
                .collect(Collectors.groupingBy(SystemRolePermissionRef::getRoleName));
        // 解析权限名称
        for (SystemRoleModel role : list) {
            List<SystemRolePermissionRef> refData = refMap.getOrDefault(role.getName(), Collections.emptyList());
            if (!refData.isEmpty()) {
                List<String> refKeys = refData.stream().map(SystemRolePermissionRef::getPermissionKey).collect(Collectors.toList());
                role.setPermissionKeys(StringUtils.join(refKeys, ","));
            }
        }
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SystemRoleModel> getRoles() {
        List<SystemRoleModel> data;
        if (redisTemplate.hasKey(SYSTEM_ROLE_KEY) == Boolean.TRUE) {
            data = redisTemplate.opsForValue().get(SYSTEM_ROLE_KEY);
        } else {
            data = systemRoleMapper.getRoles(new RoleSearchModel());
            redisTemplate.opsForValue().set(SYSTEM_ROLE_KEY, data);
        }
        return data;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRole(SystemRoleModel roleModel) {
        SystemRole roleById = null;
        List<SystemPermissionModel> targetPermission = Collections.emptyList();
        if (roleModel.getId() != null) {
            // 检验编辑数据是否存在
            roleById = systemRoleMapper.selectById(roleModel.getId());
            if (roleById == null || roleById.getIsDeleted() != 0) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        }
        // 新增或者编辑角色名校验是否重复
        if (roleById == null || !roleById.getName().equals(roleModel.getName())) {
            if (systemRoleMapper.getRoleByName(roleModel.getName()) != null) {
                throw new BusinessException(ErrorCodeEnum.OBJECT_EXISTED);
            }
        }
        // 检验菜单是否存在
        if (!StringUtils.isBlank(roleModel.getMenusValue())) {
            List<String> menusValueList = CommonUtil.parseStringList(roleModel.getMenusValue(), ",");
            if (menusValueList.isEmpty() || systemMenuMapper.countMenusByValue(menusValueList) != menusValueList.size()) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        }
        // 校验权限是否存在
        if (!StringUtils.isBlank(roleModel.getPermissionKeys())) {
            List<String> permissionKeyList = CommonUtil.parseStringList(roleModel.getPermissionKeys(), ",");
            if (!permissionKeyList.isEmpty()) {
                List<SystemPermissionModel> allPermission = systemPermissionService.getSystemPermissionList();
                targetPermission = allPermission.stream()
                        .filter(it -> permissionKeyList.contains(it.getPermissionKey()))
                        .collect(Collectors.toList());
                if (targetPermission.size() != permissionKeyList.size()) {
                    throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
                }
            }
        }
        if (roleModel.getId() == null){
            refMapper.batchInsertOrUpdate(targetPermission, roleModel.getName());
            systemRoleMapper.insertModel(roleModel);
        } else {
            // 删除已有的角色权限ref
            Map<String, SystemRolePermissionRef> oldRef = refMapper
                    .getRefByRoleName(Collections.singletonList(roleModel.getName()))
                    .stream().collect(Collectors.toMap(SystemRolePermissionRef::getPermissionKey, ref -> ref));
            List<String> finalTargetKey = targetPermission.stream().map(SystemPermissionModel::getPermissionKey).collect(Collectors.toList());
            Map<Boolean, List<String>> exitMap = oldRef.keySet().stream().collect(Collectors.groupingBy(finalTargetKey::contains));
            // 被删除的权限id
            List<String> deleteKeys = exitMap.getOrDefault(Boolean.FALSE, Collections.emptyList());
            if (!deleteKeys.isEmpty()) {
                refMapper.deleteByKeysAndRoleName(deleteKeys, roleModel.getName());
            }
            // 已存在的权限id
            List<String> exitKeys = exitMap.getOrDefault(Boolean.TRUE, Collections.emptyList());
            // 插入新权限
            List<SystemPermissionModel> newRefPermission = targetPermission.stream()
                    .filter(it -> !exitKeys.contains(it.getPermissionKey()))
                    .collect(Collectors.toList());
            if (!newRefPermission.isEmpty()) {
                refMapper.batchInsertOrUpdate(newRefPermission, roleModel.getName());
            }
            systemRoleMapper.updateModel(roleModel);
        }
        if (redisTemplate.hasKey(SYSTEM_ROLE_KEY) == Boolean.TRUE) {
            redisTemplate.delete(SYSTEM_ROLE_KEY);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(SystemRoleModel roleModel) {
        List<UserMsg> userMsgs;
        int pageNum = 0;
        int pageSize = 10;
        PageHelper.startPage(pageNum, 10);
        // 校验所删除的规则是否被取用
        do {
            userMsgs = userMsgMapper.getUserMsgList();
            userMsgs.forEach(it -> {
                List<String> ruleList = CommonUtil.parseStringList(it.getRolesName(), ",");
                if (ruleList.contains(roleModel.getName())) {
                    throw new BusinessException(ErrorCodeEnum.DATA_IS_USING);
                }
            });
            PageHelper.offsetPage(pageNum * pageSize + userMsgs.size(), pageSize);
            pageNum++;
        } while (userMsgs.size()>0);
        refMapper.deleteByKeysAndRoleName(null, roleModel.getName());
        systemRoleMapper.deletedModel(roleModel);
        if (redisTemplate.hasKey(SYSTEM_ROLE_KEY) == Boolean.TRUE) {
            redisTemplate.delete(SYSTEM_ROLE_KEY);
        }
    }


}
