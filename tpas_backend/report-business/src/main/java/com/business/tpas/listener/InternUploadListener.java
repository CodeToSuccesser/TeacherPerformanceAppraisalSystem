package com.business.tpas.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.business.tpas.constant.Constant;
import com.business.tpas.entity.Intern;
import com.business.tpas.model.InternModel;
import com.business.tpas.service.InternService;
import com.management.common.utils.BeanMapper;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.UserMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description 实习带队信息文件导入读取类
 **/
public class InternUploadListener extends EasyExcelUploadListener<InternModel, Intern>{

    private static final Logger logger = LoggerFactory.getLogger(InternUploadListener.class);

    private InternService internService;

    private UserMsgService userMsgService;

    /**
     * 学年，文件上传时插入的数据均为该参数
     */
    private String schoolYear;

    /**
     * 学期，文件上传插入均为该参数
     */
    private Integer semester;

    public InternUploadListener(InternService internService, UserMsgService userMsgService, String schoolYear,
        Integer semester) {
        this.internService = internService;
        this.userMsgService = userMsgService;
        this.schoolYear = schoolYear;
        this.semester = semester;
    }

    @Override
    public void invoke(InternModel data, AnalysisContext context) {
        filterInternInfoModel(data);

        // 达到BATCH_COUNT，存储一次数据库，防止OOM
        if (list.size() >= Constant.EASYEXCEL_UPLOAD_BATCH_SIZE) {
            internService.insertBatchInternInfo(list);
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        internService.insertBatchInternInfo(list);
        logger.info("successfully upload intern info file");
    }

    /**
     * 校验文件内容，若实习带队信息所属教师查找不到，则忽略插入
     * @param internModel
     */
    private void filterInternInfoModel(InternModel internModel) {
        UserMsgModel userMsgModel = userMsgService.getByLoginName(internModel.getTeacherCode());
        if (userMsgModel != null) {
            internModel.setTeacherId(userMsgModel.getId());
            internModel.setSemester(semester);
            internModel.setSchoolYear(schoolYear);
            list.add(BeanMapper.map(internModel, Intern.class));
            ++successCount;
        } else {
            rejectInsertList.add(internModel);
        }
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }
}
