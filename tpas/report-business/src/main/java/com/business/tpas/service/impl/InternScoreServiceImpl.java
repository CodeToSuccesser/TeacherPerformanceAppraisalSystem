package com.business.tpas.service.impl;

import com.business.tpas.dao.InternScoreMapper;
import com.business.tpas.entity.InternScore;
import com.business.tpas.model.InternScoreModel;
import com.business.tpas.model.ScoreSearchModel;
import com.business.tpas.service.InternScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 实习带队工作量统计表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class InternScoreServiceImpl extends BaseServiceImpl<InternScoreMapper, InternScore> implements InternScoreService {

    @Autowired
    private InternScoreMapper scoreMapper;

    @Override
    @Transactional
    public PageInfo<InternScoreModel> queryScoreList(ScoreSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<InternScoreModel> data = scoreMapper.queryScoreList(searchModel);
        return new PageInfo<>(data);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InternScoreModel> getScoreList(ScoreSearchModel searchModel) {
        return scoreMapper.queryScoreList(searchModel);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean hasScoreChecked(Long dataId) {
        if (dataId == null) {
            return false;
        }
        return scoreMapper.countByDataId(dataId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateOrInsert(InternScore internScore) {
        scoreMapper.updateOrInsert(internScore);
    }
}
