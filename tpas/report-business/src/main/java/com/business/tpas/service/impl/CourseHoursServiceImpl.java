package com.business.tpas.service.impl;

import com.business.tpas.dao.CourseBaseMapper;
import com.business.tpas.dao.CourseHoursMapper;
import com.business.tpas.dao.CourseHoursModifyRecordMapper;
import com.business.tpas.entity.CourseBase;
import com.business.tpas.entity.CourseHours;
import com.business.tpas.entity.CourseHoursModifyRecord;
import com.business.tpas.enums.CourseHoursModifyCheckResultEnum;
import com.business.tpas.model.CourseHoursModel;
import com.business.tpas.model.CourseHoursSearchModel;
import com.business.tpas.service.CourseHoursService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.tpas.enums.UserRoleName;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.UserMsgService;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 课程课时具体信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class CourseHoursServiceImpl extends BaseServiceImpl<CourseHoursMapper, CourseHours> implements CourseHoursService {

    @Autowired
    private CourseHoursMapper courseHoursMapper;

    @Autowired
    private CourseBaseMapper courseBaseMapper;

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private CourseHoursModifyRecordMapper courseHoursModifyRecordMapper;

    @Transactional(readOnly = true)
    @Override
    public List<CourseHours> selectByCourseId(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("course_id", id);
        map.put("is_deleted", 0);

        return courseHoursMapper.selectByMap(map);
    }

    @Transactional
    @Override
    public void insertBatchCourseHours(List<CourseHours> courseHoursList) {
        if(CollectionUtils.isEmpty(courseHoursList)){
            return;
        }
        courseHoursMapper.saveBatch(courseHoursList);
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<CourseHoursModel> getCourseHoursByPage(CourseHoursSearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        List<CourseHoursModel> courseHoursModels = courseHoursMapper.selectCourseHours(searchModel);
        return new PageInfo<>(courseHoursModels);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CourseHoursModel> getCourseHours(CourseHoursSearchModel searchModel) {
        return courseHoursMapper.selectCourseHours(searchModel);
    }

    @Transactional
    @Override
    public void modifyCourseHours(CourseHoursModel courseHoursModel) {
        CourseHours oldRecord = courseHoursMapper.selectById(courseHoursModel.getId());
        if (oldRecord == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "课时信息不存在，无法修改");
        }
        CourseBase courseBase = courseBaseMapper.selectByCourseCode(courseHoursModel.getCourseCode());
        if (courseBase == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "课时信息对应的课程不存在，无法修改");
        }

        /**
         * 管理员 则直接增加修改记录并设置为通过
         * 非管理员 增加修改记录 不直接修改课时信息表
         */
        UserMsgModel userMsgModel = UserUtil.getUserMsg();
        if (userMsgModel == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "请求用户信息缺失，修改课时记录失败");
        }

        CourseHoursModifyRecord record =
            buildModifyRecord(BeanMapper.map(oldRecord, CourseHoursModel.class), courseHoursModel);
        record.setApplyId(userMsgModel.getId());
        record.setApplyType(UserRoleName.getEnumByValue(userMsgModel.getRolesName()).flag);

        // 管理员角色
        if (StringUtils.isNotBlank(UserUtil.getUserMsg().getRolesName()) && (
            UserUtil.getUserMsg().getRolesName().equals(UserRoleName.USER_TYPE_ADMIN.info) || UserUtil.getUserMsg()
                .getRolesName().equals(UserRoleName.USER_TYPE_SUPER.info))) {
            record.setAdminId(userMsgModel.getId());
            record.setCheckTime(new Date());
            record.setCheckResult(CourseHoursModifyCheckResultEnum.PASS.getCode());
            // 修改课时记录
            modifyCourseHoursByRecord(oldRecord, record);
        }

        // 插入修改申请记录
        courseHoursModifyRecordMapper.insert(record);
    }

    @Transactional
    @Override
    public void deleteCourseHours(List<Long> ids) {
        validateCourseHoursCanBeDeleted(ids);
        // 逻辑删除课时记录
        courseHoursMapper.deleteCourseHoursByIds(ids);
    }

    @Transactional
    @Override
    public void insertCourseHours(CourseHoursModel courseHoursModel) {

        CourseBase courseBase = courseBaseMapper.selectByCourseCode(courseHoursModel.getCourseCode());
        UserMsgModel userMsgModel = userMsgService.getByLoginName(courseHoursModel.getTeacherCode());

        if (courseBase == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "根据课程编号查找课程信息为空，插入失败");
        }

        if (userMsgModel == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "根据教师编号查找教师信息为空，插入失败");
        }

        courseHoursModel.setCourseId(courseBase.getId());
        courseHoursModel.setTeacherId(userMsgModel.getId());
        courseHoursModel.setAdminId(UserUtil.getUserId());
        courseHoursMapper.insertCourseHours(courseHoursModel);
    }

    /**
     * 构建课时信息修改申请记录
     * @param oldRecord
     * @param newRecord
     * @return
     */
    private CourseHoursModifyRecord buildModifyRecord(CourseHoursModel oldRecord, CourseHoursModel newRecord) {
        CourseHoursModifyRecord record = new CourseHoursModifyRecord();
        record.setHoursId(newRecord.getId());
        record.setSelectedStudent(oldRecord.getSelectedStudent());
        record.setModifySelectedStudent(newRecord.getSelectedStudent());
        // TODO 是否允许修改学分待确定
        record.setCourseCridet(new BigDecimal(0));
        record.setTeachingHours(oldRecord.getTeachingHours());
        record.setModifyTeachingHours(newRecord.getTeachingHours());
        record.setComputerHours(oldRecord.getComputerHours());
        record.setModifyComputerHours(newRecord.getComputerHours());
        record.setExperimentHours(oldRecord.getExperimentHours());
        record.setModifyExperimentHours(newRecord.getExperimentHours());
        record.setTotalHours(new BigDecimal(
            oldRecord.getTeachingHours() + oldRecord.getComputerHours() + oldRecord.getExperimentHours()));
        record.setModifyTotalHours(new BigDecimal(
            newRecord.getTeachingHours() + newRecord.getComputerHours() + newRecord.getExperimentHours()));
        record.setPeroid(oldRecord.getPeroid());
        record.setModifyPeroid(newRecord.getPeroid());
        record.setClassed(oldRecord.getClassed());
        record.setModifyClassed(newRecord.getClassed());
        return record;
    }

    /**
     * 修改课时记录
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

    /**
     * 检验课时信息是否能被删除
     * 若该课时信息正在变更中，则连带删除课时信息变更记录
     * 否则不做处理
     * @param ids 课时信息id
     */
    private void validateCourseHoursCanBeDeleted(List<Long> ids) {
        List<Long> hoursIdsToDelete = new ArrayList<>();
        for (Long id : ids) {
            int count = courseHoursModifyRecordMapper.countModifyRecordByHoursId(id);
            if (count != 0) {
                hoursIdsToDelete.add(id);
            }
        }
        // 删除课时修改记录
        courseHoursModifyRecordMapper.batchDeleteByHoursIds(hoursIdsToDelete);
    }
}
