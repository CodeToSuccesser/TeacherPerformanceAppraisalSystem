package com.business.tpas.dao;

import com.business.tpas.entity.Major;
import com.business.tpas.model.MajorModel;
import com.business.tpas.model.MajorSearchModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 专业信息表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface MajorMapper extends BaseDao<Major> {

    Major selectByMajorCode(@Param("majorCode") String majorCode);

    /**
     * 批量保存专业信息
     * @param majors
     */
    void saveBatch(@Param("list") List<Major> majors);


    /**
     * 根据条件查找专业基本信息
     * @param searchModel 搜索条件
     * @return
     */
    List<MajorModel> selectMajor(@Param("data") MajorSearchModel searchModel);


    /**
     * 根据专业id批量删除专业记录
     * @param ids
     */
    void deleteMajorByIds(@Param("ids")List<Long> ids);


}
