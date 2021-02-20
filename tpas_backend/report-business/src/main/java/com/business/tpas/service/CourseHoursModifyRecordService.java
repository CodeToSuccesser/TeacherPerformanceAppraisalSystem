package com.business.tpas.service;

import com.business.tpas.entity.CourseHoursModifyRecord;
import com.business.tpas.model.CourseHoursModifyRecordModel;
import com.business.tpas.model.CourseHoursModifyRecordSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

/**
 * <p>
 * 课程课时修改记录表, 修改用户:教师/管理员 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface CourseHoursModifyRecordService extends BaseService<CourseHoursModifyRecord> {

    /**
     * 课时信息修改审批
     * @param id 课时信息修改记录id
     * @param result 审批结果，true为批准，false为驳回
     */
    void auditCourseHoursModify(Long id, Boolean result);

    /**
     * 获取不同状态的课时信息
     * @param searchModel
     * @return
     */
    PageInfo<CourseHoursModifyRecordModel> getModifyRecord(CourseHoursModifyRecordSearchModel searchModel);

}
