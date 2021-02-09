package com.business.tpas.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.business.tpas.constant.Constant;
import com.business.tpas.entity.Paper;
import com.business.tpas.model.MajorModel;
import com.business.tpas.model.PaperModel;
import com.business.tpas.service.MajorService;
import com.business.tpas.service.PaperService;
import com.management.common.utils.BeanMapper;
import com.management.tpas.model.TeacherMsgModel;
import com.management.tpas.service.TeacherMsgService;
import com.management.tpas.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description 论文指导信息导入读取类
 **/
public class PaperUploadListener extends EasyExcelUploadListener<PaperModel, Paper> {

    private static final Logger logger = LoggerFactory.getLogger(PaperUploadListener.class);

    private MajorService majorService;

    private TeacherMsgService teacherMsgService;

    private PaperService paperService;

    /**
     * 学年，文件上传时插入的数据均为该参数
     */
    private String schoolYear;

    /**
     * 学期，文件上传插入均为该参数
     */
    private Integer semester;

    public PaperUploadListener(MajorService majorService, TeacherMsgService teacherMsgService,
        PaperService paperService, String schoolYear, Integer semester) {
        this.majorService = majorService;
        this.teacherMsgService = teacherMsgService;
        this.paperService = paperService;
        this.schoolYear = schoolYear;
        this.semester = semester;
    }

    @Override
    public void invoke(PaperModel data, AnalysisContext context) {
        filterPaperModel(data);

        // 达到BATCH_COUNT，存储一次数据库，防止OOM
        if (list.size() >= Constant.EASYEXCEL_UPLOAD_BATCH_SIZE) {
            paperService.insertBatchPaperInfo(list);
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        paperService.insertBatchPaperInfo(list);
        logger.info("successfully upload paper info file");
    }

    /**
     * 逐条解析论文指导信息中教师编码及专业编码是否合法
     * 分别加入到待插入列表及忽略插入列表中
     * @param paperModel
     */
    private void filterPaperModel(PaperModel paperModel) {
        TeacherMsgModel teacherMsgModel = teacherMsgService.getByTeacherLogName(paperModel.getTeacherCode());
        MajorModel majorModel = majorService.selectByMajorCode(paperModel.getMajorCode());
        if (teacherMsgModel != null && majorModel != null) {
            paperModel.setMajorId(majorModel.getId());
            paperModel.setTeacherId(teacherMsgModel.getId());
            paperModel.setSchoolYear(schoolYear);
            paperModel.setSemester(semester);
            paperModel.setAdminId(UserUtil.getUserId());
            list.add(BeanMapper.map(paperModel, Paper.class));
            ++successCount;
        } else {
            rejectInsertList.add(paperModel);
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
