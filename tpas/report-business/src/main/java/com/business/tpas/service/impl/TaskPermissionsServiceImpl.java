package com.business.tpas.service.impl;

import com.business.tpas.dao.TaskPermissionsMapper;
import com.business.tpas.entity.TaskPermissions;
import com.business.tpas.model.TaskPermissionsModel;
import com.business.tpas.service.TaskPermissionsService;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.tpas.model.RoleSearchModel;
import com.management.tpas.service.SystemRoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * <p>
 * 任务下发权限表 服务实现类
 * </p>
 *
 * @since 2021-03-30
 */
@Service
public class TaskPermissionsServiceImpl extends BaseServiceImpl<TaskPermissionsMapper, TaskPermissions> implements TaskPermissionsService {

    @Autowired
    private TaskPermissionsMapper taskPermissionsMapper;

    @Autowired
    private SystemRoleService systemRoleService;

    @Override
    public List<TaskPermissionsModel> getTaskPermissions(String roleName) {
        List<TaskPermissions> taskPermissions;
        if (StringUtils.isBlank(roleName)) {
            taskPermissions = taskPermissionsMapper.selectAllTaskPermissions();
        }else{
            Map<String, Object> map = new HashMap<>();
            map.put("publisher_role", roleName);
            map.put("is_deleted", 0);
            taskPermissions = taskPermissionsMapper.selectByMap(map);
        }
        return buildTaskPermissionsModels(taskPermissions);
    }

    @Override
    @Transactional
    public void modifyTaskPermissions(TaskPermissionsModel taskPermissionsModel) {
        validateModify(taskPermissionsModel);

        TaskPermissions taskPermissions = taskPermissionsMapper.selectById(taskPermissionsModel.getId());
        if (taskPermissions == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到任务下发权限记录");
        }

        // 更新任务接受者角色
        taskPermissions.setReceiverRoles(
            StringUtils.strip(taskPermissionsModel.getReceiverRoles().toString(), "[]").replace(" ", ""));
        taskPermissionsMapper.updateById(taskPermissions);
    }

    @Override
    @Transactional
    public void deleteTaskPermissions(List<Long> ids) {
        taskPermissionsMapper.deleteTaskPermissionsByIds(ids);
    }

    @Override
    @Transactional
    public void insertTaskPermissions(TaskPermissionsModel taskPermissionsModel) {
        validateInsert(taskPermissionsModel);
        TaskPermissions taskPermissions = new TaskPermissions();
        taskPermissions.setPublisherRole(taskPermissionsModel.getPublisherRole());
        taskPermissions.setReceiverRoles(
            StringUtils.strip(taskPermissionsModel.getReceiverRoles().toString(), "[]").replace(" ", ""));
        taskPermissionsMapper.insert(taskPermissions);
    }

    /**
     * 校验修改参数
     * @param taskPermissionsModel
     */
    private void validateModify(TaskPermissionsModel taskPermissionsModel){
        if (taskPermissionsModel.getId() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "请求id为空");
        }

       validateReceiverRoleNames(taskPermissionsModel.getReceiverRoles());
    }

    /**
     * 校验任务下发对象角色
     * @param receiverRoleNameList
     */
    private void validateReceiverRoleNames(List<String> receiverRoleNameList){
        // 校验请求的receiver角色是否存在
        Iterator<String> iterator = receiverRoleNameList.iterator();
        while (iterator.hasNext()) {
            String tmp = iterator.next();
            RoleSearchModel roleSearchModel = new RoleSearchModel();
            roleSearchModel.setName(tmp);
            if (systemRoleService.queryRoles(roleSearchModel) == null) {
                iterator.remove();
            }
        }
    }

    /**
     * 校验任务插入参数
     * @param taskPermissionsModel
     */
    private void validateInsert(TaskPermissionsModel taskPermissionsModel) {
        RoleSearchModel roleSearchModel = new RoleSearchModel();
        roleSearchModel.setName(taskPermissionsModel.getPublisherRole());
        if (systemRoleService.queryRoles(roleSearchModel) == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "下发权限角色不存在，无法插入");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("publisher_role", taskPermissionsModel.getPublisherRole());
        if (CollectionUtils.isNotEmpty(taskPermissionsMapper.selectByMap(map))) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_EXISTED.code, "下发权限角色配置已存在，无法插入");
        }
        validateReceiverRoleNames(taskPermissionsModel.getReceiverRoles());
    }

    private List<TaskPermissionsModel> buildTaskPermissionsModels(List<TaskPermissions> taskPermissions) {
        List<TaskPermissionsModel> result = new ArrayList<>();
        for (int i = 0; i < taskPermissions.size(); ++i) {
            TaskPermissionsModel taskPermissionsModel = new TaskPermissionsModel();
            taskPermissionsModel.setPublisherRole(taskPermissions.get(i).getPublisherRole());
            taskPermissionsModel.setId(taskPermissions.get(i).getId());
            taskPermissionsModel.setReceiverRoles(Arrays.asList(taskPermissions.get(i).getReceiverRoles().split(",")));
            taskPermissionsModel.setUpdateTime(taskPermissions.get(i).getUpdateTime());
            taskPermissionsModel.setCreateTime(taskPermissions.get(i).getCreateTime());
            taskPermissionsModel.setIsDeleted(taskPermissions.get(i).getIsDeleted());
            result.add(taskPermissionsModel);
        }
        return result;
    }
}
