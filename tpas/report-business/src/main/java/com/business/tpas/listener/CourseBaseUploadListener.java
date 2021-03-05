package com.business.tpas.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.business.tpas.constant.Constant;
import com.business.tpas.entity.CourseBase;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.service.CourseBaseService;
import com.management.common.utils.BeanMapper;
import com.management.tpas.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @classname CourseBaseUploadListener
 * @description 导入课程基本信息文件读取类
 **/
public class CourseBaseUploadListener extends EasyExcelUploadListener<CourseBaseModel, CourseBase> {

    private static final Logger logger = LoggerFactory.getLogger(CourseBaseUploadListener.class);

    private CourseBaseService courseBaseService;

    /**
     * 记录list中的courseCode，防止重复插入
     */
    private Map<String, Integer> courseCodeMap = new HashMap<>();

    public CourseBaseUploadListener(CourseBaseService courseBaseService) {
        this.courseBaseService = courseBaseService;
    }

    /**
     * @decription 每一条数据解析都会来调用
     * @param courseBaseModel
     * @param analysisContext
     */
    @Override
    public void invoke(CourseBaseModel courseBaseModel, AnalysisContext analysisContext) {
        // 查找课程编号，若存在，则忽略插入该信息
        if (courseBaseService.countCourseBaseByCourseCode(courseBaseModel.getCourseCode()) == 0 && !courseCodeMap
            .containsKey(courseBaseModel.getCourseCode())) {

            courseBaseModel.setAdminId(UserUtil.getUserId());

            list.add(BeanMapper.map(courseBaseModel, CourseBase.class));
            courseCodeMap.put(courseBaseModel.getCourseCode(), 0);
            ++successCount;
        } else {
            rejectInsertList.add(courseBaseModel);
        }

        // 达到BATCH_COUNT，存储一次数据库，防止OOM
        if (list.size() >= Constant.EASYEXCEL_UPLOAD_BATCH_SIZE) {
            courseBaseService.insertBatchCourseBaseInfo(list);
            // 存储完成清理 list
            list.clear();
            courseCodeMap.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        courseBaseService.insertBatchCourseBaseInfo(list);
        logger.info("successfully upload course base file");
    }

}
