package com.business.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.business.tpas.entity.OtherPerformance;
import com.business.tpas.model.OtherPerformanceModel;
import com.business.tpas.model.OtherPerformanceSearchModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 其他教务工作内容 Mapper 接口
 * </p>
 *
 * @since 2021-03-30
 */
@Repository
public interface OtherPerformanceMapper extends BaseMapper<OtherPerformance> {

    /**
     * 批量保存其他教务工作内容
     * @param otherPerformances
     */
    void saveBatch(@Param("list") List<OtherPerformance> otherPerformances);

    /**
     * 根据条件查找其他教务工作内容
     * @param searchModel 搜索条件
     * @return
     */
    List<OtherPerformanceModel> selectOtherPerformance(@Param("data") OtherPerformanceSearchModel searchModel);

    /**
     * 插入单条其他教务工作内容
     * @param otherPerformanceModel
     */
    void insertOtherPerformance(@Param("data")OtherPerformanceModel otherPerformanceModel);

    /**
     * 根据课时id批量删除其他教务工作内容
     * @param ids
     */
    void deleteOtherPerformanceByIds(@Param("ids")List<Long> ids);

}
