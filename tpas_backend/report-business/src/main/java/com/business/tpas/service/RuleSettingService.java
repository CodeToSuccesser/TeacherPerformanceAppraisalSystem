package com.business.tpas.service;

import com.business.tpas.entity.RuleSetting;
import com.business.tpas.model.RuleSettingModel;
import com.management.common.base.BaseService;

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
}
