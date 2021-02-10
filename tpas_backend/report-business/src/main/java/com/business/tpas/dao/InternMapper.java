package com.business.tpas.dao;

import com.business.tpas.entity.Intern;
import com.business.tpas.model.InternModel;
import com.business.tpas.model.InternSearchModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 实习指导信息表 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface InternMapper extends BaseDao<Intern> {

    /**
     * 批量保存实习指导信息
     *
     * @param interns
     */
    void saveBatch(@Param("list") List<Intern> interns);

    /**
     * 根据条件查找实习指导信息
     *
     * @param searchModel 搜索条件
     * @return
     */
    List<InternModel> selectInternInfo(@Param("data") InternSearchModel searchModel);

    /**
     * 插入单条实习指导信息
     *
     * @param intern
     */
    void insertInternInfo(@Param("data") Intern intern);

    /**
     * 逻辑删除实习指导信息
     *
     * @param ids
     */
    void deleteInternInfos(@Param("ids") List<Long> ids);

}
