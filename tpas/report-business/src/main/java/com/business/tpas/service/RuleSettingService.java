package com.business.tpas.service;

import com.business.tpas.entity.RuleSetting;
import com.business.tpas.model.ParamSearchModel;
import com.business.tpas.model.RuleSettingModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname RuleSettingService
 * @description 权值规则服务类
 * @date 2021/3/2
 **/
public interface RuleSettingService extends BaseService<RuleSetting> {

    /**
     * 新增编辑权值规则
     *
     * @author dude
     * @date 2021/3/2
     **/
    void editRuleSettingModel(RuleSettingModel model);

    /**
     * 删除权值规则
     *
     * @author dude
     * @date 2021/3/2
     **/
    void deleteModelById(RuleSettingModel model);

    /**
     * 分页查询列表
     *
     * @author dude
     * @date 2021/4/1
     **/
    PageInfo<RuleSettingModel> queryRuleList(ParamSearchModel searchModel);

    List<RuleSettingModel> getRuleList();
}
