package com.business.tpas.dao;

import com.business.tpas.entity.PaperScore;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 论文指导工作量统计表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface PaperScoreMapper extends BaseDao<PaperScore> {

    void updateOrInsert(@Param("data") PaperScore paperScore);
}
