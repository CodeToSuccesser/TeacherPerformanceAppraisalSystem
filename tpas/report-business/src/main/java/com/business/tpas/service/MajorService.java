package com.business.tpas.service;

import com.business.tpas.entity.Major;
import com.business.tpas.model.MajorModel;
import com.business.tpas.model.MajorSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

import java.util.List;

/**
 * <p>
 * 专业信息表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface MajorService extends BaseService<Major> {

    MajorModel selectByMajorCode(String majorCode);

    /**
     * 批量插入专业信息
     * @param majors
     */
    void insertBatchMajor(List<Major> majors);

    /**
     * 根据条件分页获取专业基本信息
     * @param searchModel 搜索条件
     * @return 分页
     */
    PageInfo<MajorModel> getMajorByPage(MajorSearchModel searchModel);

    /**
     * 获取所有专业信息
     * @param searchModel
     * @return
     */
    List<MajorModel> getMajor(MajorSearchModel searchModel);

    /**
     * 修改专业基本信息
     * @param majorModel
     */
    void modifyMajor(MajorModel majorModel);

    /**
     * 批量删除专业信息
     * @param ids
     */
    void deleteMajor(List<Long> ids);

    /**
     * 插入专业信息
     * @param majorModel
     */
    void insertMajor(MajorModel majorModel);


}
