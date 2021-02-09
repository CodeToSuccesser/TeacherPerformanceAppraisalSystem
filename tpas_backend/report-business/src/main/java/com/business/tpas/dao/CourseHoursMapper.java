package com.business.tpas.dao;

import com.business.tpas.entity.CourseHours;
import com.business.tpas.model.CourseHoursModel;
import com.business.tpas.model.CourseHoursSearchModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程课时具体信息表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface CourseHoursMapper extends BaseDao<CourseHours> {

    /**
     * 批量保存课时基本信息
     * @param courseHours
     */
    void saveBatch(@Param("list") List<CourseHours> courseHours);

    /**
     * 根据条件查找课时基本信息
     * @param searchModel 搜索条件
     * @return
     */
    List<CourseHoursModel> selectCourseHours(@Param("data") CourseHoursSearchModel searchModel);

    /**
     * 插入单条课时信息
     * @param courseHoursModel
     */
    void insertCourseHours(@Param("data")CourseHoursModel courseHoursModel);

}
