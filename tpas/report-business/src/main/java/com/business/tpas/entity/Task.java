package com.business.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 任务下发内容表
 * </p>
 *
 * @since 2021-03-30
 */
public class Task implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务创建/下发者用户id
     */
    private Long publisherId;

    /**
     * 任务接受者用户id
     */
    private Long receiverId;

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

    /**
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

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public Long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Long receiverId) {
        this.receiverId = receiverId;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public Date getCreateTime() {
        return createTime;
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

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Task{" +
        "id=" + id +
        ", publisherId=" + publisherId +
        ", receiverId=" + receiverId +
        ", title=" + title +
        ", content=" + content +
        ", pictureUrl=" + pictureUrl +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", score=" + score +
        ", state=" + state +
        ", completeDegree=" + completeDegree +
        ", feedbackContent=" + feedbackContent +
        ", isDeleted=" + isDeleted +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
