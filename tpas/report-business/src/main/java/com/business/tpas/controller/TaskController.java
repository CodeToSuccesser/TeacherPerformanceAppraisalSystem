package com.business.tpas.controller;


import com.business.tpas.model.TaskModel;
import com.business.tpas.model.TaskSearchModel;
import com.business.tpas.service.TaskService;
import com.github.pagehelper.PageInfo;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 任务下发内容表 前端控制器
 * </p>
 *
 * @since 2021-03-30
 */
@RequestMapping("/task")
@Api(tags = {"任务下发内容"})
@RestController
public class TaskController {

    @Autowired
    private TaskService taskService;

    @ApiOperation(value = "根据用户编码获取对应任务信息", notes = "根据用户编码获取对应任务信息")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = TaskModel.class),
        @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/getTask")
    public BaseResponse<?> getTask(@RequestBody TaskSearchModel searchModel) {
        PageInfo<TaskModel> taskModelPageInfo = taskService.getTaskByPage(searchModel);
        return new BaseResponse<>(taskModelPageInfo);
    }

    @ApiOperation(value = "发布单条任务" ,notes = "发布单条任务")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/insert")
    public BaseResponse<?> insertTask(@RequestBody TaskModel taskModel) {
        validateTaskParam(taskModel);
        taskService.insertTask(taskModel);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "修改任务信息", notes = "修改任务信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{id}/modify")
    public BaseResponse<?> modifyTask(@PathVariable("id") Long id, @RequestBody TaskModel taskModel) {
        validateTaskParam(taskModel);
        taskModel.setId(id);
        taskService.modifyTask(taskModel);
        return new BaseResponse<>();
    }


    @ApiOperation(value = "删除下发任务", notes = "删除下发任务")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteTask")
    public BaseResponse<?> deleteTask(@RequestBody List<Long> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "删除任务ID为空");
        }
        taskService.deleteTask(ids);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "任务完成情况反馈", notes = "任务完成情况反馈")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{id}/feedBack")
    public BaseResponse<?> feedBackTask(@PathVariable("id") Long id, @RequestBody TaskModel taskModel) {
        validateTaskFeedBackParam(taskModel);
        taskModel.setId(id);
        taskService.feedBackTask(taskModel);
        return new BaseResponse<>();
    }



    private void validateTaskParam(TaskModel taskModel) {
        if (StringUtils.isBlank(taskModel.getTitle())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "任务标题为空");
        }
        if (StringUtils.isBlank(taskModel.getReceiverCode())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "任务发布对象编码为空");
        }
        if (taskModel.getStartTime() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "任务开始日期为空");
        }
        if (taskModel.getEndTime() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "任务截止日期为空");
        }
        if (StringUtils.isBlank(taskModel.getContent())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "任务描述内容为空");
        }
    }

    private void validateTaskFeedBackParam(TaskModel taskModel) {
        if (StringUtils.isBlank(taskModel.getFeedbackContent())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "反馈内容描述为空");
        }
        if (taskModel.getCompleteDegree() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "反馈进度为空");
        }
    }

}

