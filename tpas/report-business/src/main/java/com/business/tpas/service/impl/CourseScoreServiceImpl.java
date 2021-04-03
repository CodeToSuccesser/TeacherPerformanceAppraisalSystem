package com.business.tpas.service.impl;

import com.business.tpas.dao.CourseScoreMapper;
import com.business.tpas.entity.CourseScore;
import com.business.tpas.model.CourseScoreModel;
import com.business.tpas.model.ScoreSearchModel;
import com.business.tpas.service.CourseScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 * 课程工作量统计表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class CourseScoreServiceImpl extends BaseServiceImpl<CourseScoreMapper, CourseScore> implements CourseScoreService {

    @Autowired
    private CourseScoreMapper scoreMapper;

    @Override
    @Transactional(readOnly = true)
    public PageInfo<CourseScoreModel> queryScoreList(ScoreSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<CourseScoreModel> data = scoreMapper.queryScoreList(searchModel);
        return new PageInfo<>(data);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CourseScoreModel> getScoreList(ScoreSearchModel searchModel) {
        return scoreMapper.queryScoreList(searchModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean hasScoreChecked(Long dataId) {
        if (dataId == null) {
            return false;
        }
        return scoreMapper.countByDataId(dataId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrInsert(CourseScore courseScore) {
        scoreMapper.updateOrInsert(courseScore);
    }
}
