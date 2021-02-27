package com.business.tpas.dao;

import com.business.tpas.entity.ParamsRulesSetting;
import com.business.tpas.model.ParamSearchModel;
import com.business.tpas.model.ParamsRulesSettingModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname ParamsRulesSettingMapper
 * @description 权值字段取值表 Mapper 接口
 * @date 2021/2/27
 **/
@Repository
public interface ParamsRulesSettingMapper extends BaseDao<ParamsRulesSetting> {

    List<ParamsRulesSettingModel> queryParamsRulesSetting(@Param("data") ParamSearchModel searchModel);
}
