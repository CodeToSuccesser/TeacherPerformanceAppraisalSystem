package com.business.tpas.dao;

import com.business.tpas.entity.PaperModifyRecord;
import com.business.tpas.model.PaperModifyRecordModel;
import com.business.tpas.model.PaperModifyRecordSearchModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 论文指导修改记录表,修改用户:教师/管理员 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface PaperModifyRecordMapper extends BaseDao<PaperModifyRecord> {

    /**
     * 查找论文指导修改记录信息
     * @param searchModel
     * @return
     */
    List<PaperModifyRecordModel> selectModifyRecords(@Param("data")PaperModifyRecordSearchModel searchModel);

    /**
     * 根据论文指导记录id查找对应的变更记录数目
     * @param id
     * @return
     */
    int countModifyRecordByPaperId(@Param("id") Long id);

    /**
     * 根据论文指导记录id批量删除论文指导变更申请记录
     * @param ids
     */
    void batchDeleteByPaperIds(@Param("ids")List<Long> ids);

}
