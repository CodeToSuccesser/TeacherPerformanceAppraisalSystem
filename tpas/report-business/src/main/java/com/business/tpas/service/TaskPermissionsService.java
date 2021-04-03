package com.business.tpas.service;

import com.business.tpas.entity.TaskPermissions;
import com.business.tpas.model.TaskPermissionsModel;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 任务下发权限表 服务类
 * </p>
 *
 * @since 2021-03-30
 */
public interface TaskPermissionsService extends BaseService<TaskPermissions> {

    /**
     * 根据角色名获取对应的任务下发权限角色记录，若为空则返回所有记录
     * @param roleName
     * @return
     */
    List<TaskPermissionsModel> getTaskPermissions(String roleName);

    /**
     * 修改任务下发权限
     * @param taskPermissionsModel
     */
    void modifyTaskPermissions(TaskPermissionsModel taskPermissionsModel);

    /**
     * 批量删除任务下发权限
     * @param ids
     */
    void deleteTaskPermissions(List<Long> ids);

    /**
     * 插入任务下发权限
     * @param taskPermissionsModel
     */
    void insertTaskPermissions(TaskPermissionsModel taskPermissionsModel);


}
