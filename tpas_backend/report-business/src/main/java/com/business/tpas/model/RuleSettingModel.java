package com.business.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname RuleSettingModel
 * @description 权值字段规则
 * @date 2021/3/2
 **/
@ApiModel(value = "权值字段规则", description = "权值字段规则")
public class RuleSettingModel {

    private Long id;

    @ApiModelProperty(value = "权值类型, 1 授课, 2 指导论文")
    private Integer cType;

    @ApiModelProperty(value = "规则限制字段名称")
    private String valueName;

    @ApiModelProperty(value = "规则类型, 1 值区分, 2 区间区分")
    private Integer ruleType;

    @ApiModelProperty(value = "规则类型为值区分时, 表示字段值, 规则类型为区间区分时, 表示左区间")
    private BigDecimal leftValue;

    @ApiModelProperty(value = "规则类型为值区分时, 表示字段值, 规则类型为区间区分时, 表示右区间")
    private BigDecimal rightValue;

    @ApiModelProperty(value = "备注")
    private String remark;

    private Date updateTime;

    private Date createTime;

    @Override
    public String toString() {
        return "RuleSettingModel{" +
                "id=" + id +
                ", cType=" + cType +
                ", valueName='" + valueName + '\'' +
                ", ruleType=" + ruleType +
                ", leftValue=" + leftValue +
                ", rightValue=" + rightValue +
                ", remark='" + remark + '\'' +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Integer getRuleType() {
        return ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public BigDecimal getLeftValue() {
        return leftValue;
    }

    public void setLeftValue(BigDecimal leftValue) {
        this.leftValue = leftValue;
    }

    public BigDecimal getRightValue() {
        return rightValue;
    }

    public void setRightValue(BigDecimal rightValue) {
        this.rightValue = rightValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
