package com.business.tpas.service;

import com.business.tpas.entity.PaperScore;
import com.business.tpas.model.PaperScoreModel;
import com.business.tpas.model.ScoreSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 论文指导工作量统计表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface PaperScoreService extends BaseService<PaperScore> {

    PageInfo<PaperScoreModel> queryScoreList(ScoreSearchModel searchModel);

    List<PaperScoreModel> getScoreList(ScoreSearchModel searchModel);
}
