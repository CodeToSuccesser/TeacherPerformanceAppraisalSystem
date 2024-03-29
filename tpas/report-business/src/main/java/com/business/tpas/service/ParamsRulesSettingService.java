package com.business.tpas.service;

import com.business.tpas.entity.ParamsRulesSetting;
import com.business.tpas.model.ParamSearchModel;
import com.business.tpas.model.ParamsRulesSettingModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname ParamsRulesSettingService
 * @description 权值字段 服务类
 * @date 2021/2/27
 **/
public interface ParamsRulesSettingService extends BaseService<ParamsRulesSetting> {

    /**
     * @description 查询权值信息
     * @param searchModel 查询条件
     *
     * @author dude
     * @date 2021/2/27
     **/
    PageInfo<ParamsRulesSettingModel> queryParamsRulesSetting(ParamSearchModel searchModel);

    /**
     * @description 新增编辑权值信息
     * @param model 数据
     * @return void
     *
     * @author dude
     * @date 2021/2/28
     **/
    void editParamsRulesSetting(ParamsRulesSettingModel model);

    /**
     * 删除权值信息
     *
     * @author dude
     * @date 2021/3/1
     **/
    void deleteModelById(ParamsRulesSettingModel model);

    /**
     * 获取权值元素下表
     *
     * @author dude
     * @date 2021/3/16
     **/
    List<Integer> queryCNumList(ParamSearchModel model);

    List<ParamsRulesSetting> queryByCNumList(List<Integer> list);
}
