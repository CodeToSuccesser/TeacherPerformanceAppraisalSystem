package com.business.tpas.service;

import com.business.tpas.entity.Publicity;
import com.business.tpas.model.PublicityModel;
import com.business.tpas.model.PublicitySearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 公示信息表 服务类
 * </p>
 *
 * @since 2021-03-30
 */
public interface PublicityService extends BaseService<Publicity> {


    /**
     * 根据条件分页获取公示信息
     * @param searchModel 搜索条件
     * @return 分页
     */
    PageInfo<PublicityModel> getPublicityByPage(PublicitySearchModel searchModel);

    /**
     * 修改公示信息
     * @param publicityModel
     */
    void modifyPublicity(PublicityModel publicityModel);

    /**
     * 批量删除公示信息
     * @param ids
     */
    void deletePublicity(List<Long> ids);

    /**
     * 插入公示信息
     * @param publicityModel
     */
    void insertPublicity(PublicityModel publicityModel);

}
