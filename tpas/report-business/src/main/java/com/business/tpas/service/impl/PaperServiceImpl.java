package com.business.tpas.service.impl;

import com.business.tpas.dao.MajorMapper;
import com.business.tpas.dao.PaperMapper;
import com.business.tpas.dao.PaperModifyRecordMapper;
import com.business.tpas.entity.Major;
import com.business.tpas.entity.Paper;
import com.business.tpas.entity.PaperModifyRecord;
import com.business.tpas.enums.PaperModifyCheckResultEnum;
import com.business.tpas.model.PaperModel;
import com.business.tpas.model.PaperSearchModel;
import com.business.tpas.service.PaperService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.tpas.dao.UserMsgMapper;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.enums.UserTypeEnum;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 论文指导信息表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class PaperServiceImpl extends BaseServiceImpl<PaperMapper, Paper> implements PaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Autowired
    private UserMsgMapper userMsgMapper;

    @Autowired
    private MajorMapper majorMapper;

    @Autowired
    private PaperModifyRecordMapper paperModifyRecordMapper;

    @Transactional
    @Override
    public void insertBatchPaperInfo(List<Paper> papers) {
        paperMapper.saveBatch(papers);
    }

    @Transactional(readOnly = true)
    @Override
    public PageInfo<PaperModel> getPaperInfoByPage(PaperSearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        List<PaperModel> paperModels = paperMapper.selectPaperInfo(searchModel);
        return new PageInfo<>(paperModels);
    }

    @Transactional(readOnly = true)
    @Override
    public List<PaperModel> getPaperInfo(PaperSearchModel searchModel) {
        return paperMapper.selectPaperInfo(searchModel);
    }

    @Transactional
    @Override
    public void modifyPaperInfo(PaperModel paperModel) {
        Paper paper = paperMapper.selectById(paperModel.getId());
        UserMsg userMsg= userMsgMapper.selectById(paperModel.getTeacherId());
        Major major = majorMapper.selectById(paperModel.getMajorId());

        if(paper == null){
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到论文信息记录，修改失败");
        }
        if (userMsg == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "论文信息所属的教师不存在，修改失败");
        }
        if (major == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "论文信息所属的专业不存在，修改失败");
        }

        /**
         * 管理员修改，新增修改记录，直接修改论文信息表
         * 非管理员修改，新增修改记录
         */
        UserMsgModel userMsgModel = UserUtil.getUserMsg();
        if (userMsgModel == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "请求用户信息缺失，修改论文信息失败");
        }

        PaperModifyRecord record = buildModifyRecord(BeanMapper.map(paper, PaperModel.class), paperModel);
        record.setApplyId(userMsgModel.getId());
        record.setApplyType(userMsgModel.getUserType());

        // 管理员
        if (userMsgModel.getUserType() == UserTypeEnum.USER_TYPE_ADMIN.flag) {
            record.setAdminId(userMsgModel.getId());
            record.setCheckTime(new Date());
            record.setCheckResult(PaperModifyCheckResultEnum.PASS.getCode());

            // 修改论文信息记录表
            modifyPaperByRecord(record, paper);
        }
        // 新增修改申请记录
        paperModifyRecordMapper.insert(record);
    }

    @Transactional
    @Override
    public void deletePaperInfos(List<Long> ids) {
        // 逻辑删除论文指导记录
        deletePaperModifyRecord(ids);
        paperMapper.deletePaperInfos(ids);
    }

    @Transactional
    @Override
    public void insertPaperInfo(PaperModel paperModel) {
        UserMsg userMsg = userMsgMapper.selectByLogName(paperModel.getTeacherCode());
        Major major = majorMapper.selectByMajorCode(paperModel.getMajorCode());

        if (userMsg == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "论文信息所属的教师不存在，插入失败");
        }
        if (major == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "论文所属的专业不存在，插入失败");
        }

        Paper paper = BeanMapper.map(paperModel, Paper.class);
        paper.setAdminId(UserUtil.getUserId());
        paper.setMajorId(major.getId());
        paper.setTeacherId(userMsg.getId());
        paperMapper.insertPaperInfo(paper);
    }

    private PaperModifyRecord buildModifyRecord(PaperModel oldRecord, PaperModel newRecord) {
        PaperModifyRecord record = new PaperModifyRecord();
        record.setPaperId(oldRecord.getId());
        record.setMajorId(oldRecord.getMajorId());
        record.setModifyMajorId(newRecord.getMajorId());
        record.setRemark(oldRecord.getRemark());
        record.setModifyRemark(newRecord.getRemark());
        record.setSchoolYear(oldRecord.getSchoolYear());
        record.setModifySchoolYear(newRecord.getSchoolYear());
        record.setSemester(oldRecord.getSemester());
        record.setModifySemester(newRecord.getSemester());
        record.setStudentNumber(oldRecord.getStudentNumber());
        record.setModifyStudentNumber(newRecord.getStudentNumber());
        return record;
    }

    private void modifyPaperByRecord(PaperModifyRecord record, Paper paper) {
        paper.setSemester(record.getModifySemester());
        paper.setSchoolYear(record.getModifySchoolYear());
        paper.setMajorId(record.getModifyMajorId());
        paper.setRemark(record.getModifyRemark());
        paper.setStudentNumber(record.getModifyStudentNumber());
        paperMapper.updateById(paper);
    }

    /**
     * 根据paperId删除对应的paperRecord记录
     * @param ids
     */
    private void deletePaperModifyRecord(List<Long> ids) {
        List<Long> idsToDelete = new ArrayList<>();
        for (Long id : ids) {
            if(paperModifyRecordMapper.countModifyRecordByPaperId(id) != 0){
                idsToDelete.add(id);
            }
        }
        paperModifyRecordMapper.batchDeleteByPaperIds(idsToDelete);
    }
}
