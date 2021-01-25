package com.business.tpas.service.impl;

import com.business.tpas.constant.CourseBaseEnum;
import com.business.tpas.constant.CourseBaseMap;
import com.business.tpas.dao.CourseBaseMapper;
import com.business.tpas.entity.CourseBase;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.service.CourseBaseService;
import com.management.common.base.BaseServiceImpl;
import com.management.common.utils.BeanMapper;
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

    @Override
    public CourseBase convertImportCourseInfo(CourseBaseModel courseBaseModel) {
        // 课程性质映射
        courseBaseModel.setCourseCharacter(String.valueOf(CourseBaseMap.courseBaseEnumMap
            .getOrDefault(courseBaseModel.getCourseCharacter(), CourseBaseEnum.DEFAULT).value));
        // 课程类别映射
        courseBaseModel.setCourseType(String.valueOf(CourseBaseMap.courseBaseEnumMap
            .getOrDefault(courseBaseModel.getCourseType(), CourseBaseEnum.DEFAULT).value));
        // 是否双语映射
        courseBaseModel.setIsBilingual(String.valueOf(CourseBaseMap.courseBaseEnumMap
            .getOrDefault(courseBaseModel.getIsBilingual(), CourseBaseEnum.DEFAULT).value));
        // 软硬件课程映射
        courseBaseModel.setSoftHard(String.valueOf(
            CourseBaseMap.courseBaseEnumMap.getOrDefault(courseBaseModel.getSoftHard(), CourseBaseEnum.DEFAULT).value));
        // 学生类型映射
        courseBaseModel.setStudentType(String.valueOf(CourseBaseMap.courseBaseEnumMap
            .getOrDefault(courseBaseModel.getStudentType(), CourseBaseEnum.DEFAULT).value));
        // 临时设置adminId
        courseBaseModel.setAdminId(0L);
        return BeanMapper.map(courseBaseModel, CourseBase.class);
    }

    @Transactional
    @Override
    public void insertBatchCourseBaseInfo(List<CourseBase> courseBases) {
        courseBaseMapper.saveBatch(courseBases);
    }

}
