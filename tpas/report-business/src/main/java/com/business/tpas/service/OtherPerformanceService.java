package com.business.tpas.service;

import com.business.tpas.entity.OtherPerformance;
import com.business.tpas.model.OtherPerformanceModel;
import com.business.tpas.model.OtherPerformanceSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 其他教务工作内容 服务类
 * </p>
 *
 * @since 2021-03-30
 */
public interface OtherPerformanceService extends BaseService<OtherPerformance> {

    /**
     * 批量插入其他教务工作内容
     * @param otherPerformances
     */
    void insertBatchOtherPerformance(List<OtherPerformance> otherPerformances);

    /**
     * 根据条件分页获取其他教务工作内容
     * @param searchModel 搜索条件
     * @return 分页
     */
    PageInfo<OtherPerformanceModel> getOtherPerformanceByPage(OtherPerformanceSearchModel searchModel);

    /**
     * 根据条件获取所有其他教务工作内容
     * @param searchModel
     * @return
     */
    List<OtherPerformanceModel> getOtherPerformance(OtherPerformanceSearchModel searchModel);

    /**
     * 修改其他教务工作内容
     * @param otherPerformanceModel
     */
    void modifyOtherPerformance(OtherPerformanceModel otherPerformanceModel);

    /**
     * 批量删除其他教务工作内容
     * @param ids
     */
    void deleteOtherPerformance(List<Long> ids);

    /**
     * 插入其他教务工作内容
     * @param otherPerformanceModel
     */
    void insertOtherPerformance(OtherPerformanceModel otherPerformanceModel);

}
