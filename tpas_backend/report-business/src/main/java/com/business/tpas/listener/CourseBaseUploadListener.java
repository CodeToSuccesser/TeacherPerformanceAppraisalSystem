package com.business.tpas.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.business.tpas.entity.CourseBase;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.service.CourseBaseService;
import com.management.common.utils.BeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @classname CourseBaseUploadListener
 * @description 导入课程基本信息文件读取类
 **/
public class CourseBaseUploadListener extends AnalysisEventListener<CourseBaseModel> {

    private static final Logger logger = LoggerFactory.getLogger(CourseBaseUploadListener.class);

    private CourseBaseService courseBaseService;

    /**
     * 每隔50条存储数据库，然后清理list，方便内存回收
     */
    private static final int BATCH_COUNT = 50;
    List<CourseBase> list = new ArrayList<CourseBase>();

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
        list.add(BeanMapper.map(courseBaseModel, CourseBase.class));

        // TODO 获取用户ID
        Long adminId = 0L;
        list.forEach(item -> item.setAdminId(adminId));
        // 达到BATCH_COUNT，存储一次数据库，防止OOM
        if (list.size() >= BATCH_COUNT) {
            courseBaseService.insertBatchCourseBaseInfo(list);
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        courseBaseService.insertBatchCourseBaseInfo(list);
        logger.info("successfully upload course base file");
    }

}
