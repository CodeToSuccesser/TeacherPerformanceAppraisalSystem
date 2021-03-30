package com.management.common.model;

import io.swagger.annotations.ApiModel;

import java.util.List;

/**
 * @description 文件导入返回模型层
 **/
@ApiModel(value = "文件导入返回模型层", description = "文件导入返回模型层")
public class UploadResponseModel<T> {

    /**
     * 导入失败或拒绝导入记录
     */
    private List<T> rejectList;

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

    public UploadResponseModel(List<T> rejectList, Integer successCount,
        Integer failCount, Integer totalCount) {
        this.rejectList = rejectList;
        this.successCount = successCount;
        this.failCount = failCount;
        this.totalCount = totalCount;
    }

    public List<T> getRejectList() {
        return rejectList;
    }

    public void setRejectList(List<T> rejectList) {
        this.rejectList = rejectList;
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
        return "UploadResponseModel{" + "rejectList=" + rejectList + ", successCount=" + successCount + ", failCount="
            + failCount + ", totalCount=" + totalCount + '}';
    }
}
