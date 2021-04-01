package com.business.tpas.controller;

import com.business.tpas.enums.ParamsRulesValueTypeEnum;
import com.business.tpas.enums.RuleSettingColumnNameEnum;
import com.business.tpas.enums.RuleSettingRuleTypeEnum;
import com.business.tpas.model.ParamSearchModel;
import com.business.tpas.model.ParamsRulesSettingModel;
import com.business.tpas.model.RuleSettingModel;
import com.business.tpas.service.ParamsRulesSettingService;
import com.business.tpas.service.RuleSettingService;
import com.github.pagehelper.PageInfo;
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

import java.util.Arrays;
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

    @Autowired
    private RuleSettingService ruleSettingService;

    @ApiOperation(value = "新增/修改权值", notes = "新增/修改权值, cNum、cOption必传，不存在时为新增")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/editParamRules")
    public BaseResponse<?> addParamRules(@RequestBody ParamsRulesSettingModel model) {
        if (model == null || model.getcNum() == null || model.getcOption() == null
                || model.getValueType() == null || model.getcType() == null) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        Integer valueType = model.getValueType();
        if(!ParamsRulesValueTypeEnum.isExistByCode(valueType)) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_WRONG);
        } else {
            if ((valueType.equals(ParamsRulesValueTypeEnum.COLUMN.getCode()) && StringUtils.isBlank(model.getColumnName()))
                    || (valueType.equals(ParamsRulesValueTypeEnum.CONST.getCode()) && model.getParamValue() == null)) {
                return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
            }
        }
        paramsRulesSettingService.editParamsRulesSetting(model);
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

    @ApiOperation(value = "删除权值", notes = "删除权值")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteParamRules")
    public BaseResponse<?> deleteParamRules(@RequestBody ParamsRulesSettingModel model) {
        if (model == null || model.getId() == null) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        paramsRulesSettingService.deleteModelById(model);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "编辑权值规则", notes = "编辑权值规则")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/editRuleSetting")
    public BaseResponse<?> editRuleSetting(@RequestBody RuleSettingModel model) {
        if (model == null
                || model.getcType() == null
                || StringUtils.isBlank(model.getValueName())
                || model.getRuleType() == null
                || StringUtils.isBlank(model.getRuleName())
        ) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        // 判断规则字段是否有效
        if (Arrays.stream(RuleSettingColumnNameEnum.values())
                .noneMatch(it -> it.getcType().equals(model.getcType())
                        && it.getColumnName().equals(model.getValueName()))) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        // 判断规则参数取值类型
        if (!RuleSettingRuleTypeEnum.isExistByCode(model.getRuleType())) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        // 判断区间左值是否非空，右值为空表示不限制右区间
        if (model.getLeftValue() == null) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        ruleSettingService.editRuleSettingModel(model);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "删除权值规则", notes = "编辑权值规则")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteRuleSetting")
    public BaseResponse<?> deleteRuleSetting(@RequestBody RuleSettingModel model) {
        if (model == null || model.getId() == null) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        ruleSettingService.deleteModelById(model);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "查询权值元素下表列表")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = Arrays.class),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/queryCNumList")
    public BaseResponse<?> queryCNumList(@RequestBody ParamSearchModel searchModel) {
        if (searchModel == null) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        List<Integer> list = paramsRulesSettingService.queryCNumList(searchModel);
        return new BaseResponse<>(list);
    }

    @ApiOperation(value = "查询规则列表")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = RuleSettingModel.class),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/queryRuleList")
    public BaseResponse<?> queryRuleList(@RequestBody ParamSearchModel searchModel) {
        if (searchModel == null) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        PageInfo<RuleSettingModel> list = ruleSettingService.queryRuleList(searchModel);
        return new BaseResponse<>(list);
    }

    @ApiOperation(value = "查询规则信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok", response = RuleSettingModel.class),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/getRuleList")
    public BaseResponse<?> getRuleList() {
        List<RuleSettingModel> list = ruleSettingService.getRuleList();
        return new BaseResponse<>(list);
    }
}
