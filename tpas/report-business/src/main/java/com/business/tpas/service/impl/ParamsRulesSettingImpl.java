package com.business.tpas.service.impl;

import com.business.tpas.dao.ParamsRulesSettingMapper;
import com.business.tpas.dao.RuleSettingMapper;
import com.business.tpas.entity.ParamsRulesSetting;
import com.business.tpas.entity.RuleSetting;
import com.business.tpas.enums.ParamsRulesValueTypeEnum;
import com.business.tpas.enums.RuleSettingCTypeEnum;
import com.business.tpas.enums.RuleSettingColumnNameEnum;
import com.business.tpas.model.ParamSearchModel;
import com.business.tpas.model.ParamsRulesSettingModel;
import com.business.tpas.service.ParamsRulesSettingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dude
 * @version 1.0
 * @classname ParamsRulesSettingServiceImpl
 * @description 权值字段 服务实现类
 * @date 2021/2/27
 **/
@Service
public class ParamsRulesSettingImpl extends BaseServiceImpl<ParamsRulesSettingMapper, ParamsRulesSetting> implements ParamsRulesSettingService {

    @Autowired
    private ParamsRulesSettingMapper mapper;

    @Autowired
    private RuleSettingMapper ruleSettingMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<ParamsRulesSettingModel> queryParamsRulesSetting(ParamSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<ParamsRulesSettingModel> modelList = mapper.queryParamsRulesSetting(searchModel);
        return new PageInfo<>(modelList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editParamsRulesSetting(ParamsRulesSettingModel model) {
        // 查询是否存在旧数据
        ParamSearchModel searchModel = new ParamSearchModel();
        searchModel.setcNum(model.getcNum());
        searchModel.setcOption(model.getcOption());
        ParamsRulesSetting oldData = mapper.getParamsRulesSetting(searchModel);
        if (oldData != null) {
            if (model.getId() == null || !model.getId().equals(oldData.getId())) {
                // 新增或修改数据与已存在数据冲突
                throw new BusinessException(ErrorCodeEnum.OBJECT_EXISTED);
            }
        } else if (model.getId() != null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        // 校验权值字段是否存在
        if (StringUtils.isNotBlank(model.getRulesSettingIds())) {
            List<Long> idList = Arrays.stream(model.getRulesSettingIds().split(","))
                    .filter(it -> StringUtils.isNumeric(it.trim()))
                    .map(it -> Long.valueOf(it.trim()))
                    .distinct()
                    .collect(Collectors.toList());
            if (!idList.isEmpty()) {
                List<RuleSetting> ruleSettings = ruleSettingMapper.queryByIdList(idList);
                if (ruleSettings.size() != idList.size()) {
                    // 规则id匹配
                    throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
                }
            }
        }
        // 值区分时，检验字段名
        if (model.getValueType().equals(ParamsRulesValueTypeEnum.COLUMN.getCode())) {
            if (!RuleSettingCTypeEnum.isExistByCode(model.getcType())) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
            if (Arrays.stream(RuleSettingColumnNameEnum.values())
                    .noneMatch(it -> it.getcType().equals(model.getcType())
                            && it.getColumnName().equals(model.getColumnName()))) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        } else {
            if (model.getParamValue() == null) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY);
            }
        }
        if (model.getId() != null) {
            mapper.updateModel(model);
        } else {
            mapper.insertModel(model);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteModelById(ParamsRulesSettingModel model) {
        ParamSearchModel searchModel = new ParamSearchModel();
        searchModel.setId(model.getId());
        ParamsRulesSetting oldData = mapper.getParamsRulesSetting(searchModel);
        if (oldData == null) {
            // 查不到操作数据
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        mapper.deleteModelById(model.getId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Integer> queryCNumList(ParamSearchModel model) {
        return mapper.getCNumList(model);
    }
}
