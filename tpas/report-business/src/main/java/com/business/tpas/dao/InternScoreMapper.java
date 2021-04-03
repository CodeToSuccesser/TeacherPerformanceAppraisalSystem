package com.business.tpas.dao;

import com.business.tpas.entity.InternScore;
import com.business.tpas.model.InternScoreModel;
import com.business.tpas.model.ScoreSearchModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 实习带队工作量统计表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface InternScoreMapper extends BaseDao<InternScore> {

    void updateOrInsert(@Param("data") InternScore internScore);

    List<InternScoreModel> queryScoreList(@Param("data") ScoreSearchModel searchModel);

    Long countByDataId(@Param("dataId") Long dataId);

}
