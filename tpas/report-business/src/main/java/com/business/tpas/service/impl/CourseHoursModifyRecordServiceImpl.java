package com.business.tpas.service.impl;

import com.business.tpas.dao.CourseHoursMapper;
import com.business.tpas.dao.CourseHoursModifyRecordMapper;
import com.business.tpas.entity.CourseHours;
import com.business.tpas.entity.CourseHoursModifyRecord;
import com.business.tpas.enums.CourseHoursModifyCheckResultEnum;
import com.business.tpas.model.CourseHoursModifyRecordModel;
import com.business.tpas.model.CourseHoursModifyRecordSearchModel;
import com.business.tpas.service.CourseHoursModifyRecordService;
import com.business.tpas.service.CourseHoursService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.tpas.enums.UserRoleName;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 课程课时修改记录表, 修改用户:教师/管理员 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service public class CourseHoursModifyRecordServiceImpl
    extends BaseServiceImpl<CourseHoursModifyRecordMapper, CourseHoursModifyRecord>
    implements CourseHoursModifyRecordService {

    @Autowired private CourseHoursService courseHoursService;

    @Autowired private CourseHoursMapper courseHoursMapper;

    @Autowired private CourseHoursModifyRecordMapper courseHoursModifyRecordMapper;

    @Override public void auditCourseHoursModify(Long id, Boolean result) {
        // 校验是否为管理员身份
        UserMsgModel userMsgModel = UserUtil.getUserMsg();
        if (userMsgModel == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "请求用户信息缺失，审批课时信息记录失败");
        }
        if (!userMsgModel.getUserType().equals(UserRoleName.USER_TYPE_ADMIN.flag) && userMsgModel.getUserType()
            .equals(UserRoleName.USER_TYPE_SUPER.flag)) {
            throw new BusinessException(ErrorCodeEnum.PERMISSION_DENIED);
        }

        CourseHoursModifyRecord record = courseHoursModifyRecordMapper.selectById(id);
        validateRecord(record);
        record.setCheckTime(new Date());

        // 根据请求处理结果选择是否更改课时表
        if (result) {
            // 修改课时修改记录为通过及课时记录
            CourseHours courseHours = courseHoursService.getById(record.getHoursId());
            if (courseHours == null) {
                throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到课时修改记录对应的课时记录，审批失败");
            }
            record.setCheckResult(CourseHoursModifyCheckResultEnum.PASS.getCode());
            modifyCourseHoursByRecord(courseHours, record);
        } else {
            // 修改课时修改记录状态为驳回
            record.setCheckResult(CourseHoursModifyCheckResultEnum.REJECT.getCode());
        }
        courseHoursModifyRecordMapper.updateById(record);
    }

    @Override
    public PageInfo<CourseHoursModifyRecordModel> getModifyRecord(CourseHoursModifyRecordSearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        List<CourseHoursModifyRecordModel> recordModels =
            courseHoursModifyRecordMapper.selectModifyRecords(searchModel);
        return new PageInfo<>(recordModels);
    }

    /**
     * 校验课时修改记录参数是否正确
     *
     * @param record
     */
    void validateRecord(CourseHoursModifyRecord record) {
        if (record == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到课时修改记录，审批失败");
        }
        if (record.getHoursId() == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到课时修改记录对应的课时记录，审批失败");
        }
    }

    /**
     * 修改课时记录
     *
     * @param oldRecord
     * @param record
     */
    private void modifyCourseHoursByRecord(CourseHours oldRecord, CourseHoursModifyRecord record) {
        oldRecord.setSelectedStudent(record.getModifySelectedStudent());
        oldRecord.setClassed(record.getModifyClassed());
        oldRecord.setTeachingHours(record.getModifyTeachingHours());
        oldRecord.setComputerHours(record.getModifyComputerHours());
        oldRecord.setExperimentHours(record.getModifyExperimentHours());
        oldRecord.setPeroid(record.getModifyPeroid());
        courseHoursMapper.updateById(oldRecord);
    }
}
