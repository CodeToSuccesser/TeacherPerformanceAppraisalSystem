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

    /**
     * 批量查询权值信息
     *
     * @author dude
     * @date 2021/2/28
     **/
    List<ParamsRulesSettingModel> queryParamsRulesSetting(@Param("data") ParamSearchModel searchModel);

    /**
     * 由id查询权值
     *
     * @author dude
     * @date 2021/2/28
     **/
    ParamsRulesSetting getParamsRulesSetting(@Param("data") ParamSearchModel searchModel);

    /**
     * 新增
     *
     * @author dude
     * @date 2021/3/1
     **/
    void insertModel(@Param("data") ParamsRulesSettingModel data);

    /**
     * 修改数据
     *
     * @author dude
     * @date 2021/3/1
     **/
    void updateModel(@Param("data") ParamsRulesSettingModel date);

    /**
     * 删除数据
     *
     * @author dude
     * @date 2021/3/1
     **/
    void deleteModelById(@Param("id") Long id);

    /**
     * 由id批量查询参数
     *
     * @author dude
     * @date 2021/3/5
     **/
    List<ParamsRulesSetting> queryByIdList(@Param("idList") List<Long> idList);

    /**
     * 由cNum批量查询参数
     *
     * @author dude
     * @date 2021/3/5
     **/
    List<ParamsRulesSetting> queryByCNumList(@Param("data") List<Integer> list);

}
