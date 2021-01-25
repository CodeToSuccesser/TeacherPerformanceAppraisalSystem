package com.business.tpas.dao;

import com.business.tpas.entity.CourseBase;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程基本信息表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface CourseBaseMapper extends BaseDao<CourseBase> {

    void saveBatch(@Param("list") List<CourseBase> courseBases);
}
