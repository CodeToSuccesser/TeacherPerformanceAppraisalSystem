package com.business.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.business.tpas.entity.AssessRule;
import com.business.tpas.model.AssessRuleModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname AssessRuleMapper
 * @description 绩效核算规则 mapper
 * @date 2021/3/3
 **/
@Repository
public interface AssessRuleMapper extends BaseMapper<AssessRule> {

    @Override
    AssessRule selectById(@Param("id") Serializable id);

    void deletedModelById(@Param("data") AssessRuleModel model);

    List<AssessRule> selectByCType(@Param("cType") Integer type);
}
