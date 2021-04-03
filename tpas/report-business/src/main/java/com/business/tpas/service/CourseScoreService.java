package com.business.tpas.service;

import com.business.tpas.entity.CourseScore;
import com.business.tpas.model.CourseScoreModel;
import com.business.tpas.model.ScoreSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 课程工作量统计表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface CourseScoreService extends BaseService<CourseScore> {

    PageInfo<CourseScoreModel> queryScoreList(ScoreSearchModel searchModel);

    List<CourseScoreModel> getScoreList(ScoreSearchModel searchModel);

    Boolean hasScoreChecked(Long dataId);

    void updateOrInsert(CourseScore courseScore);
}
