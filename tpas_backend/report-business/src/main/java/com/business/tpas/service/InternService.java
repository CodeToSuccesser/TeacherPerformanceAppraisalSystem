package com.business.tpas.service;

import com.business.tpas.entity.Intern;
import com.business.tpas.model.InternModel;
import com.business.tpas.model.InternSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 实习指导信息表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface InternService extends BaseService<Intern> {

    /**
     * 批量插入实习指导信息
     *
     * @param interns
     */
    void insertBatchInternInfo(List<Intern> interns);

    /**
     * 根据条件分页获取实习指导信息
     *
     * @param searchModel 搜索条件
     * @return 分页
     */
    PageInfo<InternModel> getInternInfoByPage(InternSearchModel searchModel);

    /**
     * 根据条件获取所有实习指导信息
     *
     * @param searchModel
     * @return
     */
    List<InternModel> getInternInfo(InternSearchModel searchModel);

    /**
     * 修改实习指导信息
     *
     * @param internModel
     */
    void modifyInternInfo(InternModel internModel);

    /**
     * 批量删除实习指导信息
     *
     * @param ids
     */
    void deleteInternInfos(List<Long> ids);

    /**
     * 插入实习指导信息
     *
     * @param internModel
     */
    void insertInternModel(InternModel internModel);

}
