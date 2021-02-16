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
import com.management.tpas.dao.UserMsgMapper;
import com.management.tpas.entity.UserMsg;
import com.management.tpas.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        paperMapper.updateById(BeanMapper.map(paperModel, Paper.class));
    }

    @Transactional
    @Override
    public void deletePaperInfos(List<Long> ids) {
        // 逻辑删除论文指导记录
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
}
