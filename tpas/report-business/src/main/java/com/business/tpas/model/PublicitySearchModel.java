package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description 公示信息查询model
 **/
public class PublicitySearchModel extends PageModel {

    @ApiModelProperty(value = "发布者编号")
    private String publisherCode;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "开始日期")
    private Date startTime;

    @ApiModelProperty(value = "结束日期")
    private Date endTime;

    public String getPublisherCode() {
        return publisherCode;
    }

    public void setPublisherCode(String publisherCode) {
        this.publisherCode = publisherCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
