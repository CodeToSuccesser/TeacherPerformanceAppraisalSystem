package com.management.common.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dude
 * @version 1.0
 * @classname PageModel
 * @description 分页类
 * @date 2021/1/28
 **/
@ApiModel(value = "分页类", description = "分页类")
public class PageModel {

    @ApiModelProperty(value = "页码")
    public Integer pageSize;

    @ApiModelProperty(value = "页长")
    public Integer pageNum;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public PageModel() {
        this.pageNum = 0;
        this.pageSize = 100;
    }
}
