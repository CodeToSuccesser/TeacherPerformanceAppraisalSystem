package com.business.tpas.service;

import com.business.tpas.entity.Task;
import com.business.tpas.model.TaskModel;
import com.business.tpas.model.TaskSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 任务下发内容表 服务类
 * </p>
 *
 * @since 2021-03-30
 */
public interface TaskService extends BaseService<Task> {

    /**
     * 根据条件分页获取任务下发内容
     * @param searchModel 搜索条件
     * @return 分页
     */
    PageInfo<TaskModel> getTaskByPage(TaskSearchModel searchModel);

    /**
     * 插入任务下发内容
     * @param taskModel
     */
    void insertTask(TaskModel taskModel);

    /**
     * 修改任务内容
     * @param taskModel
     */
    void modifyTask(TaskModel taskModel);

    /**
     * 批量删除任务
     * @param ids
     */
    void deleteTask(List<Long> ids);

    /**
     * 任务完成反馈
     * @param taskModel
     */
    void feedBackTask(TaskModel taskModel);

}
