package com.business.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname paramsRulesSetting
 * @description 权值字段取值表
 * @date 2021/2/27
 **/
@ApiModel(value = "权值字段取值表", description = "权值字段取值表")
public class ParamsRulesSetting implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "Cn的n值, 1 表示C1")
    private Integer cNum;

    @ApiModelProperty(value = "m表示第m个Cn")
    private Integer cOpction;

    @ApiModelProperty(value = "权值字段规则, 由逗号拼接, 如: 1,3")
    private String rulesSettingIds;

    @ApiModelProperty(value = "取值类型, 1 取字段值, 2 取参数权值")
    private Integer valueType;

    @ApiModelProperty(value = "字段值, 如student_number, 取值类型为1时有效")
    private String columnName;

    @ApiModelProperty(value = "参数权值, 取值类型为2时有效")
    private BigDecimal paramValue;

    @ApiModelProperty(value = "备注")
    private String remark;

    private Integer isDeleted;

    private Date updateTime;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getcNum() {
        return cNum;
    }

    public void setcNum(Integer cNum) {
        this.cNum = cNum;
    }

    public Integer getcOpction() {
        return cOpction;
    }

    public void setcOpction(Integer cOpction) {
        this.cOpction = cOpction;
    }

    public String getRulesSettingIds() {
        return rulesSettingIds;
    }

    public void setRulesSettingIds(String rulesSettingIds) {
        this.rulesSettingIds = rulesSettingIds;
    }

    public Integer getValueType() {
        return valueType;
    }

    public void setValueType(Integer valueType) {
        this.valueType = valueType;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public BigDecimal getParamValue() {
        return paramValue;
    }

    public void setParamValue(BigDecimal paramValue) {
        this.paramValue = paramValue;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "ParamsRulesSetting{" +
                "id=" + id +
                ", cNum=" + cNum +
                ", cOpction=" + cOpction +
                ", rulesSettingIds='" + rulesSettingIds + '\'' +
                ", valueType=" + valueType +
                ", columnName='" + columnName + '\'' +
                ", paramValue=" + paramValue +
                ", remark='" + remark + '\'' +
                ", isDeleted=" + isDeleted +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}
