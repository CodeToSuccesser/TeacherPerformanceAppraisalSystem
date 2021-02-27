package com.business.tpas.controller;

import com.business.tpas.model.ParamSearchModel;
import com.business.tpas.model.ParamsRulesSettingModel;
import com.business.tpas.service.ParamsRulesSettingService;
import com.github.pagehelper.PageInfo;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname ParamsInfoController
 * @description 绩效权值参数相关接口
 * @date 2021/2/19
 **/

@RestController
@RequestMapping("/params/info")
@Api(tags = {"绩效权值参数相关接口"})
public class ParamsInfoController {

    @Autowired
    private ParamsRulesSettingService paramsRulesSettingService;

    @ApiOperation(value = "新增权值", notes = "新增权值")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/addParamRules")
    public BaseResponse<?> addParamRules(@RequestBody List<Long> ids) {
        return new BaseResponse<>();
    }

    @ApiOperation(value = "修改权值", notes = "修改权值")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/modifyParamRules")
    public BaseResponse<?> modifyParamRules(@RequestBody List<Long> ids) {
        return new BaseResponse<>();
    }

    @ApiOperation(value = "查询权值", notes = "查询权值")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = ParamsRulesSettingModel.class),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/queryParamRules")
    public BaseResponse<?> queryParamRules(@RequestBody ParamSearchModel searchModel) {
        if (searchModel == null) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        PageInfo<ParamsRulesSettingModel> page = paramsRulesSettingService.queryParamsRulesSetting(searchModel);
        return new BaseResponse<>(page);
    }

}
