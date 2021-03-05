package com.business.tpas.service.impl;

import com.business.tpas.dao.InternMapper;
import com.business.tpas.dao.InternModifyRecordMapper;
import com.business.tpas.entity.Intern;
import com.business.tpas.entity.InternModifyRecord;
import com.business.tpas.enums.InternModifyCheckResultEnum;
import com.business.tpas.model.InternModifyRecordModel;
import com.business.tpas.model.InternModifyRecordSearchModel;
import com.business.tpas.service.InternModifyRecordService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.tpas.enums.UserTypeEnum;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 实习指导修改记录表, 修改用户:教师/管理员 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class InternModifyRecordServiceImpl
    extends BaseServiceImpl<InternModifyRecordMapper, InternModifyRecord> implements InternModifyRecordService {

    @Autowired
    private InternModifyRecordMapper internModifyRecordMapper;

    @Autowired
    private InternMapper internMapper;

    @Override
    public void auditInternModify(Long id, Boolean result) {
        UserMsgModel userMsgModel = UserUtil.getUserMsg();
        if (userMsgModel == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "请求用户信息缺失，审核失败");
        }
        if (userMsgModel.getUserType() != UserTypeEnum.USER_TYPE_ADMIN.flag) {
            throw new BusinessException(ErrorCodeEnum.PERMISSION_DENIED.code, "用户权限不足，审核失败");
        }
        InternModifyRecord record = internModifyRecordMapper.selectById(id);
        if (record == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到实习带队修改记录，审核失败");
        }
        Intern intern = internMapper.selectById(record.getInternId());
        if (intern == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到实习带队信息，审核失败");
        }

        if (result) {
            // 更改操作记录为通过 更改实习带队信息表
            record.setCheckResult(InternModifyCheckResultEnum.PASS.getCode());
            intern.setNonNormalPractice(record.getModifyNonNormalPractice());
            intern.setNormalPractice(record.getModifyNormalPractice());
            intern.setSchoolPractice(record.getModifySchoolPractice());
            intern.setSchoolYear(record.getModifySchoolYear());
            intern.setSemester(record.getSemester());
            internMapper.updateById(intern);
        } else {
            record.setCheckResult(InternModifyCheckResultEnum.REJECT.getCode());
        }
        record.setCheckTime(new Date());
        internModifyRecordMapper.updateById(record);
    }

    @Override
    public PageInfo<InternModifyRecordModel> getModifyRecord(InternModifyRecordSearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        List<InternModifyRecordModel> recordModelList = internModifyRecordMapper.selectModifyRecords(searchModel);
        return new PageInfo<>(recordModelList);
    }
}
