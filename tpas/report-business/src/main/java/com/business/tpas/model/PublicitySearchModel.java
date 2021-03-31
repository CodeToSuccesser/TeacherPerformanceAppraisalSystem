package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description 公示信息查询model
 **/
public class PublicitySearchModel extends PageModel {

    @ApiModelProperty(value = "发布者编号")
    private Long publisherCode;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "发布日期")
    private Date publishTime;

    public Long getPublisherCode() {
        return publisherCode;
    }

    public void setPublisherCode(Long publisherCode) {
        this.publisherCode = publisherCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}
