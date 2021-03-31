package com.business.tpas.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.business.tpas.entity.Publicity;
import com.business.tpas.model.PublicityModel;
import com.business.tpas.model.PublicitySearchModel;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 公示信息表 Mapper 接口
 * </p>
 *
 * @since 2021-03-30
 */
@Repository
public interface PublicityMapper extends BaseMapper<Publicity> {

    /**
     * 根据条件查找公示信息
     *
     * @param searchModel 搜索条件
     * @return
     */
    List<PublicityModel> selectPublicity(@Param("data") PublicitySearchModel searchModel);

    /**
     * 根据课时id批量删除公示信息
     *
     * @param ids
     */
    void deletePublicityByIds(@Param("ids") List<Long> ids);

}
