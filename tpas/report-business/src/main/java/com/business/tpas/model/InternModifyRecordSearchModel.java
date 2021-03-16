package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * @description 实习带队信息修改记录查询条件
 **/
public class InternModifyRecordSearchModel extends PageModel {

    @ApiModelProperty("提出修改用户的登录名/用户名")
    private String applyName;

    @ApiModelProperty("提出修改范围起始时间")
    private Date beginTime;

    @ApiModelProperty("提出修改范围结束时间")
    private Date endTime;

    @ApiModelProperty("申请状态")
    private List<Integer> checkResult;

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

    public List<Integer> getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(List<Integer> checkResult) {
        this.checkResult = checkResult;
    }
}
