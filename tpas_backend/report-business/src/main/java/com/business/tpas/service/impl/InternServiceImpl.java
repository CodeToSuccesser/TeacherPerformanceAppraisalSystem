package com.business.tpas.service.impl;

import com.business.tpas.dao.InternMapper;
import com.business.tpas.entity.Intern;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        UserMsg userMsg = userMsgMapper.selectById(internModel.getTeacherId());

        if (intern == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到实习带队记录，修改失败");
        }
        if (userMsg == null) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "找不到实习带队信息所属的教师，修改失败");
        }
        internModel.setTeacherId(userMsg.getId());
        internMapper.updateById(BeanMapper.map(internModel, Intern.class));
    }

    @Transactional
    @Override
    public void deleteInternInfos(List<Long> ids) {
        // 逻辑删除实习带队信息
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
}
