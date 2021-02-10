package com.business.tpas.service.impl;

import com.business.tpas.dao.CourseBaseMapper;
import com.business.tpas.dao.CourseHoursMapper;
import com.business.tpas.entity.CourseBase;
import com.business.tpas.entity.CourseHours;
import com.business.tpas.model.CourseHoursModel;
import com.business.tpas.model.CourseHoursSearchModel;
import com.business.tpas.service.CourseHoursService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.tpas.model.TeacherMsgModel;
import com.management.tpas.service.TeacherMsgService;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程课时具体信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class CourseHoursServiceImpl extends BaseServiceImpl<CourseHoursMapper, CourseHours> implements CourseHoursService {

    @Autowired
    private CourseHoursMapper courseHoursMapper;

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    private TeacherMsgService teacherMsgService;

    @Transactional(readOnly = true)
    @Override
    public List<CourseHours> selectByCourseId(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("course_id", id);

        return courseHoursMapper.selectByMap(map);
    }

    @Transactional
    @Override
    public void insertBatchCourseHours(List<CourseHours> courseHoursList) {
        if(CollectionUtils.isEmpty(courseHoursList)){
            return;
        }
        courseHoursMapper.saveBatch(courseHoursList);
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<CourseHoursModel> getCourseHoursByPage(CourseHoursSearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        List<CourseHoursModel> courseHoursModels = courseHoursMapper.selectCourseHours(searchModel);
        return new PageInfo<>(courseHoursModels);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CourseHoursModel> getCourseHours(CourseHoursSearchModel searchModel) {
        return courseHoursMapper.selectCourseHours(searchModel);
    }

    @Transactional
    @Override
    public void modifyCourseHours(CourseHoursModel courseHoursModel) {
        if (courseHoursMapper.selectById(courseHoursModel.getId()) == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "课时信息不存在，无法修改");
        }
        CourseBase courseBase = courseBaseMapper.selectByCourseName(courseHoursModel.getCourseName());
        if (courseBase == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "课时信息对应的课程不存在，无法修改");
        }

        CourseHours courseHours = BeanMapper.map(courseHoursModel, CourseHours.class);
        courseHours.setCourseId(courseBase.getId());
        courseHoursMapper.updateById(courseHours);
    }

    @Transactional
    @Override
    public void deleteCourseHours(List<Long> ids) {
        // 逻辑删除课时记录
        courseHoursMapper.deleteCourseHoursByIds(ids);
    }

    @Transactional
    @Override
    public void insertCourseHours(CourseHoursModel courseHoursModel) {

        CourseBase courseBase = courseBaseMapper.selectByCourseCode(courseHoursModel.getCourseCode());
        TeacherMsgModel teacherMsgModel = teacherMsgService.getByTeacherLogName(courseHoursModel.getTeacherCode());

        if (courseBase == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "根据课程编号查找课程信息为空，插入失败");
        }

        if (teacherMsgModel == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "根据教师编号查找教师信息为空，插入失败");
        }

        courseHoursModel.setCourseId(courseBase.getId());
        courseHoursModel.setTeacherId(teacherMsgModel.getId());
        courseHoursModel.setAdminId(UserUtil.getUserId());
        courseHoursMapper.insertCourseHours(courseHoursModel);
    }
}
