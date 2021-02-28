package com.business.tpas.dao;

import com.business.tpas.entity.InternModifyRecord;
import com.business.tpas.model.InternModifyRecordModel;
import com.business.tpas.model.InternModifyRecordSearchModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 实习指导修改记录表, 修改用户:教师/管理员 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface InternModifyRecordMapper extends BaseDao<InternModifyRecord> {

    /**
     * 查找实习带队修改记录信息
     * @param searchModel
     * @return
     */
    List<InternModifyRecordModel> selectModifyRecords(@Param("data") InternModifyRecordSearchModel searchModel);

    /**
     * 根据实习带队记录id查找对应的变更记录数目
     * @param id
     * @return
     */
    int countModifyRecordByInternId(@Param("id") Long id);

    /**
     * 根据实习带队记录id批量删除论文指导变更申请记录
     * @param ids
     */
    void batchDeleteByInternIds(@Param("ids")List<Long> ids);


}
