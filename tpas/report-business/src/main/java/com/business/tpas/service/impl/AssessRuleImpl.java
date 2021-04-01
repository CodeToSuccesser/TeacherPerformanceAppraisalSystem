package com.business.tpas.service.impl;

import com.business.tpas.dao.AssessRuleMapper;
import com.business.tpas.dao.ParamsRulesSettingMapper;
import com.business.tpas.dao.RuleSettingMapper;
import com.business.tpas.entity.AssessRule;
import com.business.tpas.entity.ParamsRulesSetting;
import com.business.tpas.entity.RuleSetting;
import com.business.tpas.model.AssessRuleModel;
import com.business.tpas.model.ParamSearchModel;
import com.business.tpas.service.AssessRuleService;
import com.business.tpas.utils.AssessRuleUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
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
 * @classname AssessRuleImpl
 * @description 绩效核算规则 业务接口实现类
 * @date 2021/3/3
 **/
@Service
public class AssessRuleImpl extends BaseServiceImpl<AssessRuleMapper, AssessRule> implements AssessRuleService {

    @Autowired
    private AssessRuleMapper assessRuleMapper;

    @Autowired
    private ParamsRulesSettingMapper paramsRulesSettingMapper;

    @Autowired
    private RuleSettingMapper ruleSettingMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteModel(AssessRuleModel model) {
        if (assessRuleMapper.selectById(model.getId()) == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        assessRuleMapper.deletedModelById(model);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editModel(AssessRuleModel model) {
        // 检验权值是否存在
        List<Integer> CNumList = AssessRuleUtil.parseParamsFromAssessDetail(model.getAssessDetail(), true);
        if (!CNumList.isEmpty()) {
            List<ParamsRulesSetting> paramsRulesSettingList = paramsRulesSettingMapper.queryByCNumList(CNumList);
            if (paramsRulesSettingList.stream().collect(Collectors.groupingBy(ParamsRulesSetting::getcNum)).size() != CNumList.size()) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
        }
        // 校验前提条件
        if (!StringUtils.isBlank(model.getRuleSettingIds())) {
            List<Long> ruleIdList = Arrays.stream(model.getRuleSettingIds().split(","))
                    .filter(it -> StringUtils.isNumeric(it.trim()))
                    .map(it -> Long.valueOf(it.trim()))
                    .distinct()
                    .collect(Collectors.toList());
            if (!ruleIdList.isEmpty()) {
                List<RuleSetting> ruleSettingList = ruleSettingMapper.queryByIdList(ruleIdList);
                if (ruleSettingList.size() != ruleIdList.size()) {
                    throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
                }
            }
        }
        if (model.getId() != null) {
            if (assessRuleMapper.selectById(model.getId()) == null) {
                throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
            }
            assessRuleMapper.updateById(BeanMapper.map(model, AssessRule.class));
        } else {
            assessRuleMapper.insert(BeanMapper.map(model, AssessRule.class));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public PageInfo<AssessRuleModel> queryAssessList(ParamSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<AssessRuleModel> list = assessRuleMapper.queryAssessList(searchModel);
        return new PageInfo<>(list);
    }
}
