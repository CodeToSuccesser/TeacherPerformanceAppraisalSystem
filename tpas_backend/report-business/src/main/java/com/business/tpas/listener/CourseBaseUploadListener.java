package com.business.tpas.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.business.tpas.constant.Constant;
import com.business.tpas.entity.CourseBase;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.service.CourseBaseService;
import com.management.common.utils.BeanMapper;
import com.management.tpas.utils.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @classname CourseBaseUploadListener
 * @description 导入课程基本信息文件读取类
 **/
public class CourseBaseUploadListener extends AnalysisEventListener<CourseBaseModel> {

    private static final Logger logger = LoggerFactory.getLogger(CourseBaseUploadListener.class);

    private CourseBaseService courseBaseService;

    /**
     * 每隔一定数目存储数据库，然后清理list，方便内存回收
     */
    private List<CourseBase> list = new ArrayList<CourseBase>();

    /**
     * 记录list中的courseCode，防止重复插入
     */
    private Map<String, Integer> courseCodeMap = new HashMap<>();

    /**
     * 数据解析成功记录数
     */
    private Integer successCount = 0;

    /**
     * 数据解析失败或拒绝插入记录信息
     */
    private List<CourseBaseModel> rejectInsertList;

    public CourseBaseUploadListener(CourseBaseService courseBaseService) {
        this.courseBaseService = courseBaseService;
        rejectInsertList = new ArrayList<>();
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

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public List<CourseBaseModel> getRejectInsertList() {
        return rejectInsertList;
    }

    public void setRejectInsertList(List<CourseBaseModel> rejectInsertList) {
        this.rejectInsertList = rejectInsertList;
    }

}
