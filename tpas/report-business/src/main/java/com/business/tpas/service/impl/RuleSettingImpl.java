package com.business.tpas.service.impl;

import com.business.tpas.dao.ParamsRulesSettingMapper;
import com.business.tpas.dao.RuleSettingMapper;
import com.business.tpas.entity.RuleSetting;
import com.business.tpas.model.ParamSearchModel;
import com.business.tpas.model.ParamsRulesSettingModel;
import com.business.tpas.model.RuleSettingModel;
import com.business.tpas.service.RuleSettingService;
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
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dude
 * @version 1.0
 * @classname RuleSettingImpl
 * @description 权值规则服务实现类
 * @date 2021/3/2
 **/
@Service
public class RuleSettingImpl extends BaseServiceImpl<RuleSettingMapper, RuleSetting> implements RuleSettingService {

    @Autowired
    private RuleSettingMapper mapper;

    @Autowired
    private ParamsRulesSettingMapper paramsRulesSettingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editRuleSettingModel(RuleSettingModel model) {
        if (model.getId() != null) {
            if (mapper.getById(model.getId()) == null) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
            mapper.updateModel(model);
        } else {
            mapper.insertModel(model);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteModelById(RuleSettingModel model) {
        if (mapper.getById(model.getId()) == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        List<ParamsRulesSettingModel> allParamRules;
        int pageNum = 0;
        int pageSize = 10;
        PageHelper.startPage(pageNum, 10);
        // 校验所删除的规则是否被取用
        do {
            allParamRules = paramsRulesSettingMapper.queryParamsRulesSetting(new ParamSearchModel());
            allParamRules.forEach(it -> {
                List<Long> ruleList = Arrays.stream(it.getRulesSettingIds().split(","))
                        .filter(StringUtils::isNumeric)
                        .map(Long::valueOf)
                        .collect(Collectors.toList());
                if (ruleList.contains(model.getId())) {
                    throw new BusinessException(ErrorCodeEnum.DATA_IS_USING);
                }
            });
            PageHelper.offsetPage(pageNum * pageSize + allParamRules.size(), pageSize);
            pageNum++;
        } while (allParamRules.size()>0);
        mapper.deleteModelById(model);
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<RuleSettingModel> queryRuleList(ParamSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<RuleSettingModel> list = mapper.queryRuleList(searchModel);
        return new PageInfo<>(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RuleSettingModel> getRuleList() {
        return mapper.getRuleMsgList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<RuleSetting> queryByIdList(List<Long> ruleIdList) {
        if (ruleIdList == null || ruleIdList.isEmpty()) {
            return Collections.emptyList();
        } else {
            return mapper.queryByIdList(ruleIdList);
        }
    }
}
