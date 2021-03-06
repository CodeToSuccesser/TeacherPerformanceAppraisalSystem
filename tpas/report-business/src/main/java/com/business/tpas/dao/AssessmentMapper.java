package com.business.tpas.dao;

import com.business.tpas.entity.Assessment;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 教师绩效核算记录表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface AssessmentMapper extends BaseDao<Assessment> {

    void updateOrInsert(@Param("data") Assessment assessment, @Param("cType") Integer cType);
}
