package com.business.tpas.controller;

import com.business.tpas.enums.RuleSettingCTypeEnum;
import com.business.tpas.model.AssessCalculateSearchModel;
import com.business.tpas.model.AssessRuleModel;
import com.business.tpas.service.AssessRuleService;
import com.business.tpas.service.AssessmentService;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dude
 * @version 1.0
 * @classname AssessController
 * @description 绩效计算相关接口
 * @date 2021/3/1
 **/
@RestController
@RequestMapping("/params/info")
@Api(tags = {"绩效计算相关接口"})
public class AssessController {

    @Autowired
    private AssessRuleService assessRuleService;

    @Autowired
    private AssessmentService assessmentService;


    @ApiOperation(value = "编辑权值绩效规则", notes = "编辑权值规则")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/editAssessRule")
    public BaseResponse<?> editAssessRule(@RequestBody AssessRuleModel model) {
        if (model == null || model.getcType() == null || StringUtils.isEmpty(model.getAssessDetail())) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        if (!RuleSettingCTypeEnum.isExistByCode(model.getcType())) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        assessRuleService.editModel(model);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "删除权值绩效规则", notes = "编辑权值规则")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteAssessRule")
    public BaseResponse<?> deleteAssessRule(@RequestBody AssessRuleModel model) {
        if (model == null || model.getId() == null) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        assessRuleService.deleteModel(model);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "绩效统计", notes = "绩效统计")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/calculateAssess")
    public BaseResponse<?> calculateAssess(@RequestBody AssessCalculateSearchModel model) {
        if (model == null || model.getcType() == null
                || model.getSemester() == null || StringUtils.isBlank(model.getSchoolYear())) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        assessmentService.calculateAssessment(model);
        return new BaseResponse<>();
    }
}
