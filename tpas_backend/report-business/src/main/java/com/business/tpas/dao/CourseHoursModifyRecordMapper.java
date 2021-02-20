package com.business.tpas.dao;

import com.business.tpas.entity.CourseHoursModifyRecord;
import com.business.tpas.model.CourseHoursModifyRecordModel;
import com.business.tpas.model.CourseHoursModifyRecordSearchModel;
import com.management.common.base.BaseDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程课时修改记录表, 修改用户:教师/管理员 Mapper 接口
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@Repository
public interface CourseHoursModifyRecordMapper extends BaseDao<CourseHoursModifyRecord> {

    /**
     * 根据状态获取不同的课时修改信息记录
     * @param searchModel
     * @return
     */
    List<CourseHoursModifyRecordModel> selectModifyRecords(@Param("data") CourseHoursModifyRecordSearchModel searchModel);

    /**
     * 根据课时记录id查找对应的变更记录数目
     * @param id
     * @return
     */
    int countModifyRecordByHoursId(@Param("id") Long id);

    /**
     * 根据课时id批量删除课时变更申请记录
     * @param ids
     */
    void batchDeleteByHoursIds(@Param("ids")List<Long> ids);

}
