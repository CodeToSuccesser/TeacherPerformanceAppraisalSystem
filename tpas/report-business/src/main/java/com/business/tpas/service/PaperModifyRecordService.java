package com.business.tpas.service;

import com.business.tpas.entity.PaperModifyRecord;
import com.business.tpas.model.PaperModifyRecordModel;
import com.business.tpas.model.PaperModifyRecordSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;

/**
 * <p>
 * 论文指导修改记录表,修改用户:教师/管理员 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface PaperModifyRecordService extends BaseService<PaperModifyRecord> {

    /**
     * 论文指导信息修改审批
     * @param id 论文指导信息修改记录id
     * @param result 审批结果，true为批准，false为驳回
     */
    void auditPaperModify(Long id, Boolean result);

    /**
     * 获取不同状态的论文信息
     * @param searchModel
     * @return
     */
    PageInfo<PaperModifyRecordModel> getModifyRecord(PaperModifyRecordSearchModel searchModel);
}
