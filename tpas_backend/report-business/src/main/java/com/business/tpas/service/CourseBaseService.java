package com.business.tpas.service;

import com.business.tpas.entity.CourseBase;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.model.CourseInfoSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 课程基本信息表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface CourseBaseService extends BaseService<CourseBase> {

    /**
     * 批量插入课程基本信息数据
     *
     * @param courseBases
     */
    void insertBatchCourseBaseInfo(List<CourseBase> courseBases);

    /**
     * 根据条件分页获取课程基本信息
     * @param searchModel 搜索条件
     * @return 分页
     */
    PageInfo<CourseBaseModel> getCourseBaseInfoByPage(CourseInfoSearchModel searchModel);

    /**
     * 根据条件获取所有课程基本信息
     * @param searchModel
     * @return
     */
    List<CourseBaseModel> getCourseBaseInfo(CourseInfoSearchModel searchModel);

    /**
     * 修改课程基本信息
     * @param courseBaseModel
     */
    void modifyCourseBaseInfo(CourseBaseModel courseBaseModel);

    /**
     * 批量删除课程信息
     * @param ids
     */
    void deleteCourseBaseInfos(List<Long> ids);

    /**
     * 插入课程信息
     * @param courseBaseModel
     */
    void insertCourseBaseInfo(CourseBaseModel courseBaseModel);

    /**
     * 根据课程名查找同名课程的数量
     * @param courseName
     * @return
     */
    int countCourseBaseByCourseName(String courseName);

    /**
     * 根据课程编号查找课程记录数量
     * @param courseCode
     * @return
     */
    int countCourseBaseByCourseCode(String courseCode);

    CourseBaseModel getByCourseCode(String courseCode);

}
