package com.business.tpas.service.impl;

import com.business.tpas.dao.PaperScoreMapper;
import com.business.tpas.entity.PaperScore;
import com.business.tpas.model.PaperScoreModel;
import com.business.tpas.model.ScoreSearchModel;
import com.business.tpas.service.PaperScoreService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 论文指导工作量统计表 服务实现类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Service
public class PaperScoreServiceImpl extends BaseServiceImpl<PaperScoreMapper, PaperScore> implements PaperScoreService {

    @Autowired
    private PaperScoreMapper scoreMapper;

    @Override
    @Transactional
    public PageInfo<PaperScoreModel> queryScoreList(ScoreSearchModel searchModel) {
        PageHelper.startPage(searchModel.pageNum, searchModel.pageSize);
        List<PaperScoreModel> data = scoreMapper.queryScoreList(searchModel);
        return new PageInfo<>(data);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PaperScoreModel> getScoreList(ScoreSearchModel searchModel) {
        return scoreMapper.queryScoreList(searchModel);
    }
}
