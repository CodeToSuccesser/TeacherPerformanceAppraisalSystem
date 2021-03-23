package com.business.tpas.service.impl;

import com.business.tpas.dao.InternMapper;
import com.business.tpas.dao.InternModifyRecordMapper;
import com.business.tpas.entity.Intern;
import com.business.tpas.entity.InternModifyRecord;
import com.business.tpas.enums.InternModifyCheckResultEnum;
import com.business.tpas.enums.SemesterEnum;
import com.business.tpas.model.InternModel;
import com.business.tpas.model.InternSearchModel;
import com.business.tpas.service.InternService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.tpas.dao.UserMsgMapper;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.enums.UserRoleName;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 实习指导信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class InternServiceImpl extends BaseServiceImpl<InternMapper, Intern> implements InternService {

    @Autowired
    private InternMapper internMapper;

    @Autowired
    private UserMsgMapper userMsgMapper;

    @Autowired
    private InternModifyRecordMapper internModifyRecordMapper;

    @Transactional
    @Override
    public void insertBatchInternInfo(List<Intern> interns) {
        internMapper.saveBatch(interns);
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<InternModel> getInternInfoByPage(InternSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<InternModel> internModels = internMapper.selectInternInfo(searchModel);
        return new PageInfo<>(internModels);
    }

    @Transactional(readOnly = true)
    @Override
    public List<InternModel> getInternInfo(InternSearchModel searchModel) {
        return internMapper.selectInternInfo(searchModel);
    }

    @Transactional
    @Override
    public void modifyInternInfo(InternModel internModel) {
        Intern intern = internMapper.selectById(internModel.getId());
        UserMsg teacherMsg = userMsgMapper.selectById(internModel.getTeacherId());
        UserMsgModel userMsg = UserUtil.getUserMsg();

        if (intern == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到实习带队记录，修改失败");
        }
        if (teacherMsg == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到实习带队信息所属的教师，修改失败");
        }
        if (userMsg == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "请求用户信息缺失，修改失败");
        }

        InternModifyRecord record = buildModifyRecord(internModel, BeanMapper.map(intern, InternModel.class));
        record.setApplyId(userMsg.getId());
        record.setApplyType(UserRoleName.getEnumByValue(userMsg.getRolesName()).flag);

        // 管理员
        if (StringUtils.isNotBlank(UserUtil.getUserMsg().getRolesName()) && (
            UserUtil.getUserMsg().getRolesName().equals(UserRoleName.USER_TYPE_ADMIN.info) || UserUtil.getUserMsg()
                .getRolesName().equals(UserRoleName.USER_TYPE_SUPER.info))) {
            // 设置修改审核记录为通过
            record.setCheckTime(new Date());
            record.setCheckResult(InternModifyCheckResultEnum.PASS.getCode());
            record.setAdminId(userMsg.getId());
            modifyInternByModifyRecord(intern, record);
        }

        internModifyRecordMapper.insert(record);
    }

    @Transactional
    @Override
    public void deleteInternInfos(List<Long> ids) {
        // 逻辑删除实习带队信息
        deleteInternModifyRecord(ids);
        internMapper.deleteInternInfos(ids);
    }

    @Transactional
    @Override
    public void insertInternModel(InternModel internModel) {
        UserMsg userMsg = userMsgMapper.selectByLogName(internModel.getTeacherCode());
        if (userMsg == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到实习带队记录所属的教师信息，插入失败");
        }
        if (!SemesterEnum.isExistByCode(internModel.getSemester())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "实习带队信息的学期信息有误，插入失败");
        }
        internModel.setTeacherId(userMsg.getId());
        internMapper.insert(BeanMapper.map(internModel, Intern.class));
    }

    /**
     * 构造实习带队信息修改审核记录
     * @param newRecord
     * @param oldRecord
     * @return
     */
    private InternModifyRecord buildModifyRecord(InternModel newRecord, InternModel oldRecord) {
        InternModifyRecord record = new InternModifyRecord();
        record.setInternId(oldRecord.getId());
        record.setNonNormalPractice(oldRecord.getNonNormalPractice());
        record.setModifyNonNormalPractice(newRecord.getNonNormalPractice());
        record.setNormalPractice(oldRecord.getNormalPractice());
        record.setModifyNormalPractice(newRecord.getNormalPractice());
        record.setSchoolPractice(oldRecord.getSchoolPractice());
        record.setModifySchoolPractice(newRecord.getSchoolPractice());
        record.setSchoolYear(oldRecord.getSchoolYear());
        record.setModifySchoolYear(newRecord.getSchoolYear());
        record.setSemester(oldRecord.getSemester());
        record.setModifySemester(newRecord.getSemester());
        return record;
    }

    /**
     * 根据修改申请记录修改实习带队信息
     * @param intern
     * @param record
     */
    private void modifyInternByModifyRecord(Intern intern, InternModifyRecord record) {
        intern.setSemester(record.getModifySemester());
        intern.setSchoolYear(record.getModifySchoolYear());
        intern.setSchoolPractice(record.getModifySchoolPractice());
        intern.setNormalPractice(record.getModifyNormalPractice());
        intern.setNonNormalPractice(record.getModifyNonNormalPractice());
        internMapper.updateById(intern);
    }

    /**
     * 根据internId删除对应的internRecord记录
     * @param ids
     */
    private void deleteInternModifyRecord(List<Long> ids) {
        List<Long> idsToDelete = new ArrayList<>();
        for (Long id : ids) {
            if (internModifyRecordMapper.countModifyRecordByInternId(id) != 0) {
                idsToDelete.add(id);
            }
        }
        internModifyRecordMapper.batchDeleteByInternIds(idsToDelete);
    }
}
