package com.business.tpas.controller;

import com.business.tpas.model.OtherPerformanceModel;
import com.business.tpas.model.OtherPerformanceSearchModel;
import com.business.tpas.service.OtherPerformanceService;
import com.github.pagehelper.PageInfo;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 其他教务工作内容 前端控制器
 * </p>
 *
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/other")
@Api(tags = {"其他教务工作信息"})
public class OtherPerformanceController {

    private static Logger logger = LoggerFactory.getLogger(OtherPerformanceController.class);

    @Autowired
    private OtherPerformanceService otherPerformanceService;


    @ApiOperation(value = "根据条件查询其他教务工作信息", notes = "根据条件查询其他教务工作信息")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = OtherPerformanceModel.class),
        @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/getOtherPerformance")
    public BaseResponse<?> getOtherPerformance(@RequestBody OtherPerformanceSearchModel searchModel) {
        PageInfo<OtherPerformanceModel> otherPerformanceModelPageInfo =
            otherPerformanceService.getOtherPerformanceByPage(searchModel);
        return new BaseResponse<>(otherPerformanceModelPageInfo);
    }

    @ApiOperation(value = "修改单条其他教务工作信息", notes = "修改单条其他教务工作信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{id}/modify")
    public BaseResponse<?> modifyOtherPerformance(@PathVariable("id") Long id,
        @RequestBody OtherPerformanceModel otherPerformanceModel) {
        validateModifyInsertOtherPerformanceParam(otherPerformanceModel);
        otherPerformanceModel.setId(id);
        validateModifyParam(otherPerformanceModel);
        otherPerformanceService.modifyOtherPerformance(otherPerformanceModel);
        return new BaseResponse<>();
    }


    @ApiOperation(value = "删除其他教务供工作信息", notes = "删除其他教务工作信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteOtherPerformance")
    public BaseResponse<?> deleteOtherPerformance(@RequestBody List<Long> ids) {
        otherPerformanceService.deleteOtherPerformance(ids);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "插入单条其他教务工作信息记录" ,notes = "插入单条其他教务工作信息记录")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/insert")
    public BaseResponse<?> insertOtherPerformance(@RequestBody OtherPerformanceModel otherPerformanceModel) {
        validateModifyInsertOtherPerformanceParam(otherPerformanceModel);
        otherPerformanceService.insertOtherPerformance(otherPerformanceModel);
        return new BaseResponse<>();
    }

    private void validateModifyInsertOtherPerformanceParam(OtherPerformanceModel otherPerformanceModel) {
        if (StringUtils.isBlank(otherPerformanceModel.getTeacherCode())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "教师编号为空");
        }
        if (otherPerformanceModel.getSemester() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "学期参数为空");
        }
        if (StringUtils.isBlank(otherPerformanceModel.getSchoolYear())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "学年参数为空");
        }
    }

    /**
     * 修改参数校验
     * @param otherPerformanceModel
     */
    private void validateModifyParam(OtherPerformanceModel otherPerformanceModel) {
        if (otherPerformanceModel.getId() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "请求ID缺失，修改失败");
        }
        if (StringUtils.isBlank(otherPerformanceModel.getTitle())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "标题内容缺失，修改失败");
        }
        if (StringUtils.isBlank(otherPerformanceModel.getContent())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "内容缺失，修改失败");
        }
    }
}

