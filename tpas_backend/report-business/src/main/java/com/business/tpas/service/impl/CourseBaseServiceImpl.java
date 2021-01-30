package com.business.tpas.service.impl;

import com.business.tpas.constant.*;
import com.business.tpas.dao.CourseBaseMapper;
import com.business.tpas.entity.CourseBase;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.model.CourseInfoSearchModel;
import com.business.tpas.service.CourseBaseService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.utils.BeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程基本信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class CourseBaseServiceImpl extends BaseServiceImpl<CourseBaseMapper, CourseBase> implements CourseBaseService {

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Transactional
    @Override
    public void insertBatchCourseBaseInfo(List<CourseBase> courseBases) {
        courseBaseMapper.saveBatch(courseBases);
    }

    @Override
    public PageInfo<CourseBaseModel> getCourseBaseInfo(CourseInfoSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<CourseBaseModel> courseBases = courseBaseMapper.selectCourseBase(searchModel);
        return new PageInfo<>(courseBases);
    }

}
