package com.business.tpas.service;

import com.business.tpas.entity.CourseBase;
import com.business.tpas.model.CourseBaseModel;
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
     * 将上传的文件数据转化为课程基本信息实体数据
     *
     * @param courseBaseModel
     * @return
     */
    public CourseBase convertImportCourseInfo(CourseBaseModel courseBaseModel);

    /**
     * 批量插入课程基本信息数据
     *
     * @param courseBases
     */
    public void insertBatchCourseBaseInfo(List<CourseBase> courseBases);

}
