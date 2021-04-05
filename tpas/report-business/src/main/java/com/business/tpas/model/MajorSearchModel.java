package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description 专业信息查询数据模型
 **/
@ApiModel(value = "专业信息查询数据模型")
public class MajorSearchModel extends PageModel {

    @ApiModelProperty(value = "专业编码")
    private String majorCode;

    @ApiModelProperty(value = "专业名称")
    private String majorName;

    public String getMajorCode() {
        return majorCode;
    }

    public void setMajorCode(String majorCode) {
        this.majorCode = majorCode;
    }

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }
}
