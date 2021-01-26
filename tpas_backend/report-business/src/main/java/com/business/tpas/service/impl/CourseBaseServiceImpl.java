package com.business.tpas.service.impl;

import com.business.tpas.constant.*;
import com.business.tpas.dao.CourseBaseMapper;
import com.business.tpas.entity.CourseBase;
import com.business.tpas.model.CourseBaseModel;
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

    @Override
    public CourseBase convertImportCourseInfo(CourseBaseModel courseBaseModel) {
        // 课程性质映射
        courseBaseModel.setCourseCharacter(String.valueOf(CourseBaseMap.courseBaseMap
            .getOrDefault(courseBaseModel.getCourseCharacter(), CourseBaseEnum.DEFAULT.value)));
        // 课程类别映射
        courseBaseModel.setCourseType(String.valueOf(CourseBaseMap.courseBaseMap
            .getOrDefault(courseBaseModel.getCourseType(), CourseBaseEnum.DEFAULT.value)));
        // 是否双语映射
        courseBaseModel.setIsBilingual(String.valueOf(CourseBaseMap.courseBaseMap
            .getOrDefault(courseBaseModel.getIsBilingual(), CourseBaseEnum.DEFAULT.value)));
        // 软硬件课程映射
        courseBaseModel.setSoftHard(String.valueOf(
            CourseBaseMap.courseBaseMap.getOrDefault(courseBaseModel.getSoftHard(), CourseBaseEnum.DEFAULT.value)));
        // 学生类型映射
        courseBaseModel.setStudentType(String.valueOf(CourseBaseMap.courseBaseMap
            .getOrDefault(courseBaseModel.getStudentType(), CourseBaseEnum.DEFAULT.value)));
        // 临时设置adminId
        courseBaseModel.setAdminId(0L);
        return BeanMapper.map(courseBaseModel, CourseBase.class);
    }

    @Transactional
    @Override
    public void insertBatchCourseBaseInfo(List<CourseBase> courseBases) {
        courseBaseMapper.saveBatch(courseBases);
    }

    @Override
    public PageInfo<CourseBaseModel> getCourseBaseInfo(String courseName, Integer courseCharacter, Integer courseType,
        String institute, Integer softHard, Integer studentType, Integer isBilingual, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);

        List<CourseBase> courseBases = courseBaseMapper
            .selectCourseBase(courseName, courseCharacter, courseType, institute, softHard, studentType, isBilingual);
        List<CourseBaseModel> courseBaseModels = convertCourseBaseToCourseBaseModel(courseBases);
        return new PageInfo<>(courseBaseModels);
    }

    /**
     * 将courseBase实体映射为model
     * @param courseBases
     * @return
     */
    private List<CourseBaseModel> convertCourseBaseToCourseBaseModel(List<CourseBase> courseBases) {
        List<CourseBaseModel> courseBaseModels = new ArrayList<>();
        for (CourseBase courseBase : courseBases) {
            CourseBaseModel courseBaseModel = BeanMapper.map(courseBase, CourseBaseModel.class);
            String courseCharacter, courseType, isBilingual, softHard, studentType;

            // 课程性质
            courseCharacter = courseBase.getCourseCharacter() == CourseCharacterEnum.ELECTIVE.value ?
                CourseCharacterEnum.ELECTIVE.key : CourseCharacterEnum.COMPULSORY.key;

            // 课程类型
            if (courseBase.getCourseType() == CourseTypeEnum.GENERAL_EDUCATION.value) {
                courseType = CourseTypeEnum.GENERAL_EDUCATION.key;
            } else if (courseBase.getCourseType() == CourseTypeEnum.PRACTICE_TEACHING.value) {
                courseType = CourseTypeEnum.PRACTICE_TEACHING.key;
            } else if (courseBase.getCourseType() == CourseTypeEnum.PROFESSIONAL_CORE.value) {
                courseType = CourseTypeEnum.PROFESSIONAL_CORE.key;
            } else if (courseBase.getCourseType() == CourseTypeEnum.TEACHER_EDUCATION.value) {
                courseType = CourseTypeEnum.TEACHER_EDUCATION.key;
            } else {
                courseType = CourseTypeEnum.PROFESSIONAL_DIRECTION.key;
            }

            // 是否双语
            isBilingual =
                courseBase.getIsBilingual() == IsBilingualEnum.NOT_BILINGUAL.value ? IsBilingualEnum.NOT_BILINGUAL.key :
                    IsBilingualEnum.IS_BILINGUAL.key;

            // 软硬件
            softHard = courseBase.getSoftHard() == SoftHardEnum.HARD_COURSE.value ? SoftHardEnum.HARD_COURSE.key :
                SoftHardEnum.SOFT_COURSE.key;

            // 学生类型
            if (courseBase.getStudentType() == StudentTypeEnum.GRADUATE_STUDENT.value) {
                studentType = StudentTypeEnum.GRADUATE_STUDENT.key;
            } else if (courseBase.getStudentType() == StudentTypeEnum.JUNIOR_COLLEGE_STUDENT.value) {
                studentType = StudentTypeEnum.JUNIOR_COLLEGE_STUDENT.key;
            } else {
                studentType = StudentTypeEnum.UNDERGRADUATES.key;
            }

            courseBaseModel.setCourseCharacter(courseCharacter);
            courseBaseModel.setCourseType(courseType);
            courseBaseModel.setIsBilingual(isBilingual);
            courseBaseModel.setSoftHard(softHard);
            courseBaseModel.setSoftHard(studentType);
            courseBaseModels.add(courseBaseModel);
        }
        return courseBaseModels;
    }

}
