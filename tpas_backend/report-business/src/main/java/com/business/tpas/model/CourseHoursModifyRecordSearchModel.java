package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description 课时修改记录申请查询模型层
 **/
@ApiModel("课时修改记录申请查询模型层")
public class CourseHoursModifyRecordSearchModel extends PageModel {

    @ApiModelProperty("提出修改用户的登录名/用户名")
    private String applyName;

    @ApiModelProperty("提出修改范围起始时间")
    private Date beginTime;

    @ApiModelProperty("提出修改范围结束时间")
    private Date endTime;

    @ApiModelProperty("申请状态")
    private Integer checkResult;

    public String getApplyName() {
        return applyName;
    }

    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
    }
}
