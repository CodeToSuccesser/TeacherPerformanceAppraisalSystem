package com.business.tpas.dao;

import com.business.tpas.entity.CourseScore;
import com.business.tpas.model.CourseScoreModel;
import com.business.tpas.model.ScoreSearchModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程工作量统计表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface CourseScoreMapper extends BaseDao<CourseScore> {

    void updateOrInsert(@Param("data") CourseScore courseScore);

    List<CourseScoreModel> queryScoreList(@Param("data")ScoreSearchModel searchModel);

}
