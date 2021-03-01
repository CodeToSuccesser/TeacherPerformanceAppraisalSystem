package com.business.tpas.dao;

import com.business.tpas.entity.RuleSetting;
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

    List<RuleSetting> queryByIdList(@Param("data") List<Long> data);
}
