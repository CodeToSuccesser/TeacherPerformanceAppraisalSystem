package com.business.tpas.model;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @description 课程信息文件导入返回模型层
 **/
@ApiModel(value = "课程信息文件导入返回模型层", description = "课程信息文件导入返回模型层")
public class CourseBaseUploadResponseModel {

    /**
     * 导入失败或拒绝导入记录
     */
    private List<CourseBaseModel> courseBaseModels;

    /**
     * 导入成功数
     */
    private Integer successCount;

    /**
     * 导入失败数
     */
    private Integer failCount;

    /**
     * 总导入条数
     */
    private Integer totalCount;

    public CourseBaseUploadResponseModel(List<CourseBaseModel> courseBaseModels, Integer successCount,
        Integer failCount, Integer totalCount) {
        this.courseBaseModels = courseBaseModels;
        this.successCount = successCount;
        this.failCount = failCount;
        this.totalCount = totalCount;
    }

    public List<CourseBaseModel> getCourseBaseModels() {
        return courseBaseModels;
    }

    public void setCourseBaseModels(List<CourseBaseModel> courseBaseModels) {
        this.courseBaseModels = courseBaseModels;
    }

    public Integer getSuccessCount() {
        return successCount;
    }

    public void setSuccessCount(Integer successCount) {
        this.successCount = successCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        return "CourseBaseUploadResponseModel{" + "courseBaseModels=" + courseBaseModels + ", successCount="
            + successCount + ", failCount=" + failCount + ", totalCount=" + totalCount + '}';
    }
}
