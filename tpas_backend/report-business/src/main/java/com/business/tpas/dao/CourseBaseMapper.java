package com.business.tpas.dao;

import com.business.tpas.entity.CourseBase;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.model.CourseInfoSearchModel;
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

    /**
     * 批量保存课程基本信息
     * @param courseBases
     */
    void saveBatch(@Param("list") List<CourseBase> courseBases);

    /**
     * 根据条件查找课程基本信息
     * @param searchModel 搜索条件
     * @return
     */
    List<CourseBaseModel> selectCourseBase(@Param("data") CourseInfoSearchModel searchModel);

    /**
     * 根据课程名查找已存在的数量
     * @param courseName
     * @return
     */
    int countByCourseName(@Param("courseName") String courseName);

    /**
     * 根据课程编号查找已存在的数量
     * @param courseCode
     * @return
     */
    int countByCourseCode(@Param("courseCode") String courseCode);

    /**
     * 插入课程信息
     * @param courseBase
     */
    void insertCourseBase(@Param("data") CourseBase courseBase);

    /**
     * 根据课程名查找最后一条课程信息，默认课程名唯一
     * @param courseName
     * @return
     */
    CourseBase selectByCourseName(@Param("courseName")String courseName);
}
