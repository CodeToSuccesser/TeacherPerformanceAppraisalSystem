package com.business.tpas.service.impl;

import com.business.tpas.dao.TaskMapper;
import com.business.tpas.entity.Task;
import com.business.tpas.model.TaskModel;
import com.business.tpas.model.TaskSearchModel;
import com.business.tpas.service.TaskService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.UserMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 任务下发内容表 服务实现类
 * </p>
 *
 * @since 2021-03-30
 */
@Service
public class TaskServiceImpl extends BaseServiceImpl<TaskMapper, Task> implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Autowired
    private UserMsgService userMsgService;

    @Override
    public PageInfo<TaskModel> getTaskByPage(TaskSearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        searchModel.setCurrentTime(new Date());
        List<TaskModel> taskModels = taskMapper.selectTask(searchModel);
        return new PageInfo<>(taskModels);
    }

    @Override
    public void insertTask(TaskModel taskModel) {
        UserMsgModel publisherModel = userMsgService.getByLoginName(taskModel.getPublisherCode());
        UserMsgModel receiverModel = userMsgService.getByLoginName(taskModel.getReceiverCode());

        if (publisherModel == null || receiverModel == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "发布者不存在或任务接受者不存在，插入失败");
        }
        Task task = BeanMapper.map(taskModel, Task.class);
        task.setPublisherId(publisherModel.getId());
        task.setReceiverId(publisherModel.getId());

        taskMapper.insert(task);
    }

    @Override
    public void modifyTask(TaskModel taskModel) {
        Task oldTask = taskMapper.selectById(taskModel.getId());
        if (oldTask == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到任务记录，修改失败");
        }
        oldTask.setTitle(taskModel.getTitle());
        oldTask.setContent(taskModel.getContent());
        oldTask.setStartTime(taskModel.getStartTime());
        oldTask.setEndTime(taskModel.getEndTime());

        taskMapper.updateById(oldTask);
    }

    @Override
    public void deleteTask(List<Long> ids) {
        taskMapper.deleteTaskByIds(ids);
    }

    @Override
    public void feedBackTask(TaskModel taskModel) {
        Task task = taskMapper.selectById(taskModel.getId());
        if (task == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到任务记录，反馈失败");
        }
        task.setFeedbackContent(taskModel.getFeedbackContent());
        task.setCompleteDegree(taskModel.getCompleteDegree());
        taskMapper.updateById(task);
    }
}
