package com.business.tpas.service;

import com.business.tpas.entity.CourseHours;
import com.business.tpas.model.CourseHoursModel;
import com.business.tpas.model.CourseHoursSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 课程课时具体信息表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface CourseHoursService extends BaseService<CourseHours> {

    List<CourseHours> selectByCourseId(Long id);

    /**
     * 批量插入课时信息记录
     * @param courseHoursList
     */
    void insertBatchCourseHours(List<CourseHours> courseHoursList);

    /**
     * 根据条件分页获取课时基本信息
     * @param searchModel 搜索条件
     * @return 分页
     */
    PageInfo<CourseHoursModel> getCourseHoursByPage(CourseHoursSearchModel searchModel);

    /**
     * 根据条件获取所有课时基本信息
     * @param searchModel
     * @return
     */
    List<CourseHoursModel> getCourseHours(CourseHoursSearchModel searchModel);

    /**
     * 修改课时基本信息
     * @param courseHoursModel
     */
    void modifyCourseHours(CourseHoursModel courseHoursModel);

    /**
     * 批量删除课时信息
     * @param ids
     */
    void deleteCourseHours(List<Long> ids);

    /**
     * 插入课时信息
     * @param courseHoursModel
     */
    void insertCourseHours(CourseHoursModel courseHoursModel);

}
