package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description 任务查找模型层
 **/
public class TaskSearchModel extends PageModel {

    /**
     * 任务状态
     */
    @ApiModelProperty(value = "任务状态")
    private Integer state;

    /**
     * 任务标题
     */
    @ApiModelProperty(value = "任务标题")
    private String title;

    /**
     * 接受者编号
     */
    @ApiModelProperty(value = "接受者编号")
    private String receiverCode;

    /**
     * 接受者姓名
     */
    @ApiModelProperty(value = "接受者姓名")
    private String receiverName;

    /**
     * 下发者编码
     */
    @ApiModelProperty(value = "下发者编码")
    private String publisherCode;

    /**
     * 下发者姓名
     */
    @ApiModelProperty(value = "下发者姓名")
    private String publisherName;

    @ApiModelProperty(value = "当前时间")
    private Date currentTime;

    public Date getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Date currentTime) {
        this.currentTime = currentTime;
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

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
