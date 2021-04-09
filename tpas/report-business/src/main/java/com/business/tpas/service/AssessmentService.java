package com.business.tpas.service;

import com.business.tpas.entity.Assessment;
import com.business.tpas.model.AssessCalculateSearchModel;
import com.business.tpas.model.AssessmentModel;
import com.business.tpas.model.ScoreAnalysisModel;
import com.business.tpas.model.ScoreSearchModel;
import com.github.pagehelper.PageInfo;
import com.management.common.base.BaseService;
import com.management.common.model.UploadResponseModel;

/**
 * <p>
 * 教师绩效核算记录表 服务类
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public interface AssessmentService extends BaseService<Assessment> {

    /**
     * 计算绩效
     *
     * @author dude
     * @date 2021/4/1
     **/
    UploadResponseModel<?> calculateAssessment(AssessCalculateSearchModel model);

    PageInfo<AssessmentModel> queryAssessList(ScoreSearchModel searchModel);

    void updateOrInsert(Assessment assessment, Integer cType);

    ScoreAnalysisModel getScoreAnalysis(ScoreSearchModel searchModel);

}
