package com.business.tpas.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 * @description 任务内容
 **/
@ApiModel(value = "任务内容", description = "任务内容")
public class TaskModel {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务创建/下发者用户编码
     */
    private String publisherCode;

    /**
     * 下发者名称
     */
    private String publisherName;

    /**
     * 任务接受者用户编码
     */
    private String receiverCode;

    /**
     * 接受者姓名
     */
    private String receiverName;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务内容描述
     */
    private String content;

    /**
     * 任务说明图片url, 可为空
     */
    private String pictureUrl;

    /**
     * 任务起始时间
     */
    private Date startTime;

    /**
     * 任务截止时间
     */
    private Date endTime;

    /**L
     * 任务完成评分，百分制
     */
    private Integer score;

    /**
     * 任务状态： 0-进行中，1-已截止/已到期
     */
    private Integer state;

    /**
     * 任务完成率反馈，百分制
     */
    private Integer completeDegree;

    /**
     * 任务完成情况反馈
     */
    private String feedbackContent;

    /**
     * 账号逻辑删除标识, 0 无删除, 1 已删除
     */
    private Integer isDeleted;

    /**
     * 数据最新操作时间
     */
    private Date updateTime;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPublisherCode() {
        return publisherCode;
    }

    public void setPublisherCode(String publisherCode) {
        this.publisherCode = publisherCode;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public String getReceiverCode() {
        return receiverCode;
    }

    public void setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getCompleteDegree() {
        return completeDegree;
    }

    public void setCompleteDegree(Integer completeDegree) {
        this.completeDegree = completeDegree;
    }

    public String getFeedbackContent() {
        return feedbackContent;
    }

    public void setFeedbackContent(String feedbackContent) {
        this.feedbackContent = feedbackContent;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
