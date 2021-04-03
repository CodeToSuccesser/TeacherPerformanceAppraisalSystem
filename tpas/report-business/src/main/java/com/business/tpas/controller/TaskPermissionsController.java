package com.business.tpas.controller;


import com.business.tpas.model.TaskPermissionsModel;
import com.business.tpas.service.TaskPermissionsService;
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
 * 任务下发权限表 前端控制器
 * </p>
 *
 * @since 2021-03-30
 */
@RequestMapping("/taskPermissions")
@Api(tags = {"任务下发权限"})
@RestController
public class TaskPermissionsController {

    @Autowired
    private TaskPermissionsService taskPermissionsService;

    @ApiOperation(value = "根据用户角色获取对应下发权限角色", notes = "根据用户角色获取对应下发权限角色")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = TaskPermissionsModel.class),
        @ApiResponse(code = 500, message = "系统错误")})
    @GetMapping("/getTaskPermissions")
    public BaseResponse<?> getTaskPermissions(@RequestParam(required = false) String roleName) {
        List<TaskPermissionsModel> taskPermissionsModelPageInfo = taskPermissionsService.getTaskPermissions(roleName);
        return new BaseResponse<>(taskPermissionsModelPageInfo);
    }

    @ApiOperation(value = "插入任务下发权限" ,notes = "插入任务下发权限")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/insert")
    public BaseResponse<?> insertTaskPermissions(@RequestBody TaskPermissionsModel taskPermissionsModel) {
        validateModifyInsertTaskPermissionsParam(taskPermissionsModel);
        taskPermissionsService.insertTaskPermissions(taskPermissionsModel);
        return new BaseResponse<>();
    }


    @ApiOperation(value = "修改单条任务下发权限", notes = "修改单条任务下发权限")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{id}/modify")
    public BaseResponse<?> modifyTaskPermissions(@PathVariable("id") Long id,
        @RequestBody TaskPermissionsModel taskPermissionsModel) {
        validateModifyInsertTaskPermissionsParam(taskPermissionsModel);
        taskPermissionsModel.setId(id);
        taskPermissionsService.modifyTaskPermissions(taskPermissionsModel);
        return new BaseResponse<>();
    }


    @ApiOperation(value = "删除任务下发权限", notes = "删除任务下发权限")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteTaskPermissions")
    public BaseResponse<?> deleteTaskPermissions(@RequestBody List<Long> ids) {
        taskPermissionsService.deleteTaskPermissions(ids);
        return new BaseResponse<>();
    }


    /**
     * 修改新增参数字段校验
     * @param taskPermissionsModel
     */
    private void validateModifyInsertTaskPermissionsParam(TaskPermissionsModel taskPermissionsModel) {
        if (StringUtils.isBlank(taskPermissionsModel.getPublisherRole())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "任务发布角色参数为空");
        }
        if (CollectionUtils.isEmpty(taskPermissionsModel.getReceiverRoles())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "任务接受角色参数为空");
        }
    }


}

