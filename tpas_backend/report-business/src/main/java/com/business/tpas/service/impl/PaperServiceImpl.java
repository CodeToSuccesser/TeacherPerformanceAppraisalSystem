package com.business.tpas.service.impl;

import com.business.tpas.dao.MajorMapper;
import com.business.tpas.dao.PaperMapper;
import com.business.tpas.entity.Major;
import com.business.tpas.entity.Paper;
import com.business.tpas.model.PaperModel;
import com.business.tpas.model.PaperSearchModel;
import com.business.tpas.service.PaperService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.utils.BeanMapper;
import com.management.tpas.dao.TeacherMsgMapper;
import com.management.tpas.entity.TeacherMsg;
import com.management.tpas.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private TeacherMsgMapper teacherMsgMapper;

    @Autowired
    private MajorMapper majorMapper;

    @Override
    public void insertBatchPaperInfo(List<Paper> papers) {
        paperMapper.saveBatch(papers);
    }

    @Override
    public PageInfo<PaperModel> getPaperInfoByPage(PaperSearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        List<PaperModel> paperModels = paperMapper.selectPaperInfo(searchModel);
        return new PageInfo<>(paperModels);
    }

    @Override
    public List<PaperModel> getPaperInfo(PaperSearchModel searchModel) {
        return paperMapper.selectPaperInfo(searchModel);
    }

    @Override
    public void modifyPaperInfo(PaperModel paperModel) {

        TeacherMsg teacherMsg = teacherMsgMapper.selectById(paperModel.getTeacherId());
        Major major = majorMapper.selectById(paperModel.getMajorId());

        if (teacherMsg == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "论文信息所属的教师不存在，修改失败");
        }
        if (major == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "论文信息所属的专业不存在，修改失败");
        }

        paperMapper.updateById(BeanMapper.map(paperModel, Paper.class));
    }

    @Override
    public void deletePaperInfos(List<Long> ids) {
        paperMapper.deleteBatchIds(ids);
    }

    @Override
    public void insertPaperInfo(PaperModel paperModel) {
        TeacherMsg teacherMsg = teacherMsgMapper.selectByLogName(paperModel.getTeacherCode());
        Major major = majorMapper.selectByMajorCode(paperModel.getMajorCode());

        if (teacherMsg == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "论文信息所属的教师不存在，插入失败");
        }
        if (major == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "论文所属的专业不存在，插入失败");
        }

        Paper paper = BeanMapper.map(paperModel, Paper.class);
        paper.setAdminId(UserUtil.getUserId());
        paper.setMajorId(major.getId());
        paper.setTeacherId(teacherMsg.getId());
        paperMapper.insertPaperInfo(paper);
    }
}
