package com.business.tpas.service.impl;

import com.business.tpas.dao.PaperMapper;
import com.business.tpas.dao.PaperModifyRecordMapper;
import com.business.tpas.entity.Paper;
import com.business.tpas.entity.PaperModifyRecord;
import com.business.tpas.enums.PaperModifyCheckResultEnum;
import com.business.tpas.model.PaperModifyRecordModel;
import com.business.tpas.model.PaperModifyRecordSearchModel;
import com.business.tpas.service.PaperModifyRecordService;
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
 * 论文指导修改记录表,修改用户:教师/管理员 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class PaperModifyRecordServiceImpl extends BaseServiceImpl<PaperModifyRecordMapper, PaperModifyRecord> implements PaperModifyRecordService {

    @Autowired
    private PaperModifyRecordMapper paperModifyRecordMapper;

    @Autowired
    private PaperMapper paperMapper;

    @Override
    public void auditPaperModify(Long id, Boolean result) {
        //  校验是否为管理员身份
        UserMsgModel userMsgModel = UserUtil.getUserMsg();
        if (userMsgModel == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "请求用户信息缺失，审批失败");
        }

        if (userMsgModel.getUserType() != UserTypeEnum.USER_TYPE_ADMIN.flag) {
            throw new BusinessException(ErrorCodeEnum.PERMISSION_DENIED.code, "用户权限不足，审批失败");
        }

        PaperModifyRecord paperModifyRecord = paperModifyRecordMapper.selectById(id);
        if (paperModifyRecord == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "论文审批申请记录不存在");
        }
        Paper paper = paperMapper.selectById(paperModifyRecord.getPaperId());
        if (paper == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "论文指导记录不存在");
        }

        if (result) {
            // 批准 修改申请状态及论文信息表
            paper.setMajorId(paperModifyRecord.getModifyMajorId());
            paper.setRemark(paperModifyRecord.getModifyRemark());
            paper.setSchoolYear(paperModifyRecord.getModifySchoolYear());
            paper.setSemester(paperModifyRecord.getModifySemester());
            paper.setStudentNumber(paperModifyRecord.getStudentNumber());
            paperMapper.updateById(paper);
            paperModifyRecord.setCheckResult(PaperModifyCheckResultEnum.PASS.getCode());
        } else {
            paperModifyRecord.setCheckResult(PaperModifyCheckResultEnum.REJECT.getCode());
        }
        paperModifyRecord.setCheckTime(new Date());
        paperModifyRecordMapper.updateById(paperModifyRecord);
    }

    @Override
    public PageInfo<PaperModifyRecordModel> getModifyRecord(PaperModifyRecordSearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        List<PaperModifyRecordModel> recordModelList = paperModifyRecordMapper.selectModifyRecords(searchModel);
        return new PageInfo<>(recordModelList);
    }
}
