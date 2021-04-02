package com.business.tpas.service;

import com.business.tpas.entity.InternScore;
import com.business.tpas.model.InternScoreModel;
import com.business.tpas.model.ScoreSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 实习带队工作量统计表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface InternScoreService extends BaseService<InternScore> {

    PageInfo<InternScoreModel> queryScoreList(ScoreSearchModel searchModel);

    List<InternScoreModel> getScoreList(ScoreSearchModel searchModel);
}
