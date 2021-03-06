package com.business.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname AssessRule
 * @description 绩效核算规则表
 * @date 2021/3/3
 **/
@ApiModel(value = "绩效核算规则表", description = "绩效核算规则表")
public class AssessRule implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "权值类型, 1 授课, 2 指导论文, 3 其他")
    private Integer cType;

    @ApiModelProperty(value = "绩效计算公式, 如研究生、本、专科理论课: {1}*{8}*(1+{2}+{3}+{4})")
    private String assessDetail;

    @ApiModelProperty(value = "进行绩效核算前提规则id")
    private String ruleSettingIds;

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

    public Integer getcType() {
        return cType;
    }

    public void setcType(Integer cType) {
        this.cType = cType;
    }

    public String getAssessDetail() {
        return assessDetail;
    }

    public void setAssessDetail(String assessDetail) {
        this.assessDetail = assessDetail;
    }

    public String getRuleSettingIds() {
        return ruleSettingIds;
    }

    public void setRuleSettingIds(String ruleSettingIds) {
        this.ruleSettingIds = ruleSettingIds;
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
        return "AssessRule{" +
                "id=" + id +
                ", cType=" + cType +
                ", assessDetail='" + assessDetail + '\'' +
                ", ruleSettingIds='" + ruleSettingIds + '\'' +
                ", remark='" + remark + '\'' +
                ", isDeleted=" + isDeleted +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                '}';
    }
}
