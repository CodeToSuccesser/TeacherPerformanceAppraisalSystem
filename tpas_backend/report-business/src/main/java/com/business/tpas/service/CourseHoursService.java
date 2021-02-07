package com.business.tpas.service;

import com.business.tpas.entity.CourseHours;
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

}
