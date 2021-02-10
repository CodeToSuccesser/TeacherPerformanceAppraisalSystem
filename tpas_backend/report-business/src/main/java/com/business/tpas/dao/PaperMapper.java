package com.business.tpas.dao;

import com.business.tpas.entity.Paper;
import com.business.tpas.model.PaperModel;
import com.business.tpas.model.PaperSearchModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 论文指导信息表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface PaperMapper extends BaseDao<Paper> {

    /**
     * 批量保存论文信息
     * @param papers
     */
    void saveBatch(@Param("list") List<Paper> papers);

    /**
     * 根据条件查找论文信息
     * @param searchModel 搜索条件
     * @return
     */
    List<PaperModel> selectPaperInfo(@Param("data") PaperSearchModel searchModel);

    /**
     * 插入单条论文信息
     * @param paper
     */
    void insertPaperInfo(@Param("data")Paper paper);

    /**
     * 逻辑删除论文指导记录
     * @param ids
     */
    void deletePaperInfos(@Param("ids")List<Long> ids);

}
