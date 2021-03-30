package com.business.tpas.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.management.common.constant.Constant;
import com.business.tpas.entity.CourseHours;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.model.CourseHoursModel;
import com.business.tpas.service.CourseBaseService;
import com.business.tpas.service.CourseHoursService;
import com.management.common.listener.EasyExcelUploadListener;
import com.management.common.utils.BeanMapper;
import com.management.tpas.model.UserMsgModel;
import com.management.tpas.service.UserMsgService;
import com.management.tpas.utils.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description 导入课时信息文件读取类
 **/
public class CourseHoursUploadListener extends EasyExcelUploadListener<CourseHoursModel,CourseHours> {

    private static final Logger logger = LoggerFactory.getLogger(CourseHoursUploadListener.class);

    private CourseHoursService courseHoursService;

    private CourseBaseService courseBaseService;

    private UserMsgService userMsgService;

    public CourseHoursUploadListener(CourseHoursService courseHoursService, CourseBaseService courseBaseService,
        UserMsgService userMsgService) {
        this.courseHoursService = courseHoursService;
        this.courseBaseService = courseBaseService;
        this.userMsgService = userMsgService;
    }

    @Override
    public void invoke(CourseHoursModel data, AnalysisContext context) {
        filterCourseHoursModel(data);

        // 达到BATCH_COUNT，存储一次数据库，防止OOM
        if (list.size() >= Constant.EASYEXCEL_UPLOAD_BATCH_SIZE) {
            courseHoursService.insertBatchCourseHours(list);
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        courseHoursService.insertBatchCourseHours(list);
        logger.info("successfully upload course hours file");
    }

    /**
     * 校验选课编号格式是否正确
     * @param selectionNumber
     */
    private boolean validateSelectionNumber(String selectionNumber){
        if (StringUtils.isBlank(selectionNumber) || selectionNumber.length() != Constant.SELECTION_NUMBER_LENGTH) {
            return false;
        }
        return true;
    }

    /**
     * 根据选课编号解析学期
     * @param selectionNumber
     * @return
     */
    private Integer getSemesterBySelectionNumber(String selectionNumber) {
        return Integer.parseInt(selectionNumber
            .substring(Constant.SELECTION_NUMBER_SEMESTER_BEGIN, Constant.SELECTION_NUMBER_SEMESTER_BEGIN + 1)) - 1;
    }

    /**
     * 根据选课编号解析学年
     * @param selectionNumber
     * @return
     */
    private String getSchoolYearBySelectionNumber(String selectionNumber) {
        return selectionNumber.substring(Constant.SELECTION_NUMBER_SCHOOL_YEAR_INDEX_BEGIN,
            Constant.SELECTION_NUMBER_SCHOOL_YEAR_INDEX_END);
    }

    /**
     * 根据选课编号解析课程编号
     * @param selectionNumber
     * @return
     */
    private String getCourseCodeBySelectionNumber(String selectionNumber) {
        return selectionNumber
            .substring(Constant.SELECTION_NUMBER_COURSE_CODE_BEGIN, Constant.SELECTION_NUMBER_COURSE_CODE_END);
    }

    /**
     * 解析转换单条课时记录，并根据数据格式合法与否选择插入或是忽略
     * @param data
     */
    private void filterCourseHoursModel(CourseHoursModel data) {
        if (validateSelectionNumber(data.getSelectionNumber())) {
            // 根据选课编号解析学期、学年以及课程编号
            String selectionNumber = data.getSelectionNumber();
            Integer semester = getSemesterBySelectionNumber(selectionNumber);
            String schoolYear = getSchoolYearBySelectionNumber(selectionNumber);
            String courseCode = getCourseCodeBySelectionNumber(selectionNumber);

            data.setSemester(semester);
            data.setSchoolYear(schoolYear);
            data.setCourseCode(courseCode);
            data.setAdminId(UserUtil.getUserId());

            // 判断是否存在课程信息及教师信息
            CourseBaseModel courseBaseModel = courseBaseService.getByCourseCode(courseCode);
            UserMsgModel userMsgModel = userMsgService.getByLoginName(data.getTeacherCode());
            if (courseBaseModel != null && userMsgModel != null) {
                data.setTeacherId(userMsgModel.getId());
                data.setCourseId(courseBaseModel.getId());
                list.add(BeanMapper.map(data, CourseHours.class));
                ++successCount;
            } else {
                rejectInsertList.add(data);
            }
        } else {
            rejectInsertList.add(data);
        }
    }

}
