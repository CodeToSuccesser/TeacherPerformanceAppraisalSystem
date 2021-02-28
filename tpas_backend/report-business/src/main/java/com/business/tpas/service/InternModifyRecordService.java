package com.business.tpas.service;

import com.business.tpas.entity.InternModifyRecord;
import com.business.tpas.model.InternModifyRecordModel;
import com.business.tpas.model.InternModifyRecordSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

/**
 * <p>
 * 实习指导修改记录表, 修改用户:教师/管理员 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface InternModifyRecordService extends BaseService<InternModifyRecord> {

    /**
     * 实习带队信息修改审批
     * @param id 实习带队信息修改记录id
     * @param result 审批结果，true为批准，false为驳回
     */
    void auditInternModify(Long id, Boolean result);

    /**
     * 获取不同状态的实习带队信息
     * @param searchModel
     * @return
     */
    PageInfo<InternModifyRecordModel> getModifyRecord(InternModifyRecordSearchModel searchModel);

}
