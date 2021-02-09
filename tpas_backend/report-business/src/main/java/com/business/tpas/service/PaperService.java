package com.business.tpas.service;

import com.business.tpas.entity.Paper;
import com.business.tpas.model.PaperModel;
import com.business.tpas.model.PaperSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 论文指导信息表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface PaperService extends BaseService<Paper> {

    /**
     * 批量插入论文指导信息
     * @param papers
     */
    void insertBatchPaperInfo(List<Paper> papers);

    /**
     * 根据条件分页获取论文指导信息
     * @param searchModel 搜索条件
     * @return 分页
     */
    PageInfo<PaperModel> getPaperInfoByPage(PaperSearchModel searchModel);

    /**
     * 根据条件获取所有论文信息
     * @param searchModel
     * @return
     */
    List<PaperModel> getPaperInfo(PaperSearchModel searchModel);

    /**
     * 修改论文信息
     * @param paperModel
     */
    void modifyPaperInfo(PaperModel paperModel);

    /**
     * 批量删除论文信息
     * @param ids
     */
    void deletePaperInfos(List<Long> ids);

    /**
     * 插入论文信息
     * @param paperModel
     */
    void insertPaperInfo(PaperModel paperModel);

}
