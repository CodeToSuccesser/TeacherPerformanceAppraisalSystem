package com.business.tpas.service;

import com.business.tpas.entity.AssessRule;
import com.business.tpas.model.AssessRuleModel;
import com.business.tpas.model.ParamSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

/**
 * @author dude
 * @version 1.0
 * @classname AssessRuleService
 * @description 绩效核算规则 业务接口类
 * @date 2021/3/3
 **/
public interface AssessRuleService extends BaseService<AssessRule> {

    /**
     * 逻辑删除绩效规则
     *
     * @author dude
     * @date 2021/3/5
     **/
    void deleteModel(AssessRuleModel model);

    /**
     * 新增编辑绩效规则
     *
     * @author dude
     * @date 2021/3/5
     **/
    void editModel(AssessRuleModel model);


    /**
     * 分页查询考核规则
     *
     * @author dude
     * @date 2021/4/1
     **/
    PageInfo<AssessRuleModel> queryAssessList(ParamSearchModel searchModel);
}
