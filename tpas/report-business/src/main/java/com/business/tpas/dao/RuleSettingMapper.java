package com.business.tpas.dao;

import com.business.tpas.entity.RuleSetting;
import com.business.tpas.model.RuleSettingModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname RuleSettingMapper
 * @description 权值字段规则表 Mapper 接口
 * @date 2021/2/28
 **/
@Repository
public interface RuleSettingMapper extends BaseDao<RuleSetting> {

    /**
     * 由id查询规则列表
     *
     * @author dude
     * @date 2021/3/2
     **/
    List<RuleSetting> queryByIdList(@Param("data") List<Long> data);

    @Override
    RuleSetting getById(@Param("id") Long id);

    /**
     * 新增规则信息
     *
     * @author dude
     * @date 2021/3/2
     **/
    void insertModel(@Param("data") RuleSettingModel model);

    /**
     * 更新规则信息
     *
     * @author dude
     * @date 2021/3/2
     **/
    void updateModel(@Param("data") RuleSettingModel model);

    /**
     * 由id删除规则信息
     *
     * @author dude
     * @date 2021/3/3
     **/
    void deleteModelById(@Param("data") RuleSettingModel model);
}
