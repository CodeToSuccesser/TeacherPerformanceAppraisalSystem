package com.business.tpas.service.impl;

import com.business.tpas.dao.CourseBaseMapper;
import com.business.tpas.entity.CourseBase;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.model.CourseInfoSearchModel;
import com.business.tpas.service.CourseBaseService;
import com.business.tpas.service.CourseHoursService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Autowired
    private CourseHoursService courseHoursService;

    @Transactional
    @Override
    public void insertBatchCourseBaseInfo(List<CourseBase> courseBases) {
        if(CollectionUtils.isEmpty(courseBases))
            return;
        courseBaseMapper.saveBatch(courseBases);
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<CourseBaseModel> getCourseBaseInfoByPage(CourseInfoSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<CourseBaseModel> courseBases = courseBaseMapper.selectCourseBase(searchModel);
        return new PageInfo<>(courseBases);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CourseBaseModel> getCourseBaseInfo(CourseInfoSearchModel searchModel) {
        return courseBaseMapper.selectCourseBase(searchModel);
    }

    @Transactional
    @Override
    public void modifyCourseBaseInfo(CourseBaseModel courseBaseModel) {
        CourseBase courseBase = courseBaseMapper.selectById(courseBaseModel.getId());
        if (courseBase == null)
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, ErrorCodeEnum.OBJECT_NOT_FOUND.msg);
        courseBaseMapper.updateById(BeanMapper.map(courseBaseModel, CourseBase.class));
    }

    @Transactional
    @Override
    public void deleteCourseBaseInfos(List<Long> ids) {
        filterIdsCanBeDeleted(ids);
        courseBaseMapper.deleteBatchIds(ids);
    }

    @Override
    public void insertCourseBaseInfo(CourseBaseModel courseBaseModel) {
        if (courseBaseMapper.countByCourseCode(courseBaseModel.getCourseCode()) != 0) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_EXISTED.code, "课程记录已存在");
        }
        courseBaseMapper.insertCourseBase(BeanMapper.map(courseBaseModel, CourseBase.class));
    }

    @Override
    public int countCourseBaseByCourseName(String courseName) {
        return courseBaseMapper.countByCourseName(courseName);
    }

    @Override
    public int countCourseBaseByCourseCode(String courseCode) {
        return courseBaseMapper.countByCourseCode(courseCode);
    }

    @Override
    public CourseBaseModel getByCourseCode(String courseCode) {
        return BeanMapper.map(courseBaseMapper.selectByCourseCode(courseCode), CourseBaseModel.class);
    }

    /**
     * 过滤除课时信息中关联的课程记录，不做删除
     *
     * @param ids
     */
    private void filterIdsCanBeDeleted(List<Long> ids) {
        ids.removeIf(id -> !CollectionUtils.isEmpty(courseHoursService.selectByCourseId(id)));
    }

}
