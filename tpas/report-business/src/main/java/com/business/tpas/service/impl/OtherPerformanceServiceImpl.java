package com.business.tpas.service.impl;

import com.business.tpas.dao.OtherPerformanceMapper;
import com.business.tpas.entity.OtherPerformance;
import com.business.tpas.model.OtherPerformanceModel;
import com.business.tpas.model.OtherPerformanceSearchModel;
import com.business.tpas.service.OtherPerformanceService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.UserMsgService;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 其他教务工作内容 服务实现类
 * </p>
 *
 * @since 2021-03-30
 */
@Service
public class OtherPerformanceServiceImpl extends BaseServiceImpl<OtherPerformanceMapper, OtherPerformance>
    implements OtherPerformanceService {

    @Autowired
    private OtherPerformanceMapper otherPerformanceMapper;

    @Autowired
    private UserMsgService userMsgService;

    @Override
    @Transactional
    public void insertBatchOtherPerformance(List<OtherPerformance> otherPerformances) {
        if(CollectionUtils.isEmpty(otherPerformances)){
            return;
        }
        otherPerformanceMapper.saveBatch(otherPerformances);
    }

    @Override
    public PageInfo<OtherPerformanceModel> getOtherPerformanceByPage(OtherPerformanceSearchModel searchModel) {
        PageHelper.startPage(searchModel.getPageNum(), searchModel.getPageSize());
        List<OtherPerformanceModel> otherPerformanceModels = otherPerformanceMapper.selectOtherPerformance(searchModel);
        return new PageInfo<>(otherPerformanceModels);
    }

    @Override
    public List<OtherPerformanceModel> getOtherPerformance(OtherPerformanceSearchModel searchModel) {
        return otherPerformanceMapper.selectOtherPerformance(searchModel);
    }

    @Override
    @Transactional
    public void modifyOtherPerformance(OtherPerformanceModel otherPerformanceModel) {
        // 直接修改 无需审核过程
        OtherPerformance oldOtherPerformance = otherPerformanceMapper.selectById(otherPerformanceModel.getId());
        oldOtherPerformance.setTitle(otherPerformanceModel.getTitle());
        oldOtherPerformance.setContent(otherPerformanceModel.getContent());
        oldOtherPerformance.setScore(otherPerformanceModel.getScore());
        oldOtherPerformance.setRemark(otherPerformanceModel.getRemark());

        otherPerformanceMapper.updateById(oldOtherPerformance);
    }

    @Override
    @Transactional
    public void deleteOtherPerformance(List<Long> ids) {
        if(CollectionUtils.isEmpty(ids)){
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "删除id列表为空，删除失败");
        }
        otherPerformanceMapper.deleteOtherPerformanceByIds(ids);
    }

    @Override
    @Transactional
    public void insertOtherPerformance(OtherPerformanceModel otherPerformanceModel) {
        UserMsgModel userMsgModel = userMsgService.getByLoginName(otherPerformanceModel.getTeacherCode());
        if (userMsgModel == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "教师编码不存在或找不到该教师");
        }

        OtherPerformance otherPerformance = new OtherPerformance();
        otherPerformance.setUserId(userMsgModel.getId());
        otherPerformance.setAdminId(UserUtil.getUserId());
        otherPerformance.setTitle(otherPerformanceModel.getTitle());
        otherPerformance.setContent(otherPerformanceModel.getContent());
        otherPerformance.setSemester(otherPerformanceModel.getSemester());
        otherPerformance.setSchoolYear(otherPerformanceModel.getSchoolYear());
        otherPerformance.setScore(otherPerformanceModel.getScore());
        otherPerformance.setRemark(otherPerformanceModel.getRemark());

        otherPerformanceMapper.insert(otherPerformance);
    }

}
