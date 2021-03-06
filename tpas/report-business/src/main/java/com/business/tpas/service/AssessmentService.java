package com.business.tpas.service;

import com.business.tpas.entity.Assessment;
import com.business.tpas.model.AssessCalculateSearchModel;
import com.management.common.base.BaseService;

/**
 * <p>
 * 教师绩效核算记录表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface AssessmentService extends BaseService<Assessment> {

    void calculateAssessment(AssessCalculateSearchModel model);

}
