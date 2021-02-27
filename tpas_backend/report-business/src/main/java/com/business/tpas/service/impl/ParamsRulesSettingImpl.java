package com.business.tpas.service.impl;

import com.business.tpas.dao.ParamsRulesSettingMapper;
import com.business.tpas.entity.ParamsRulesSetting;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.model.ParamSearchModel;
import com.business.tpas.model.ParamsRulesSettingModel;
import com.business.tpas.service.ParamsRulesSettingService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public PageInfo<ParamsRulesSettingModel> queryParamsRulesSetting(ParamSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<ParamsRulesSettingModel> modelList = mapper.queryParamsRulesSetting(searchModel);
        return new PageInfo<>(modelList);
    }
}
