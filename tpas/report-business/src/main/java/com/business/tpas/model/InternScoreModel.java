package com.business.tpas.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname InternScoreModel
 * @description 实习指导分数model
 * @date 2021/4/3
 **/
@ApiModel(value = "实习指导分数model")
public class InternScoreModel {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "教师编码")
    private Long teacherId;

    @ApiModelProperty(value = "实习指信息编码")
    private Long internId;

    @ApiModelProperty(value = "选用的绩效规则id")
    private Long assessRuleId;

    @ApiModelProperty(value = "选用的绩效规则")
    private String assessDetail;

    @ApiModelProperty(value = "统计得出绩效规则")
    private String assessFormat;

    @ApiModelProperty(value = "授课总分")
    private BigDecimal total;

    @ApiModelProperty(value = "学期, 默认1 第一学期, 2 第二学期")
    private Integer semester;

    @ApiModelProperty(value = "学年")
    private String schoolYear;

    @ApiModelProperty(value = "处理的管理员编码")
    private Long adminId;

    /**
     * 数据最新操作时间
     */
    private Date updateTime;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public Long getInternId() {
        return internId;
    }

    public void setInternId(Long internId) {
        this.internId = internId;
    }

    public Long getAssessRuleId() {
        return assessRuleId;
    }

    public void setAssessRuleId(Long assessRuleId) {
        this.assessRuleId = assessRuleId;
    }

    public String getAssessDetail() {
        return assessDetail;
    }

    public void setAssessDetail(String assessDetail) {
        this.assessDetail = assessDetail;
    }

    public String getAssessFormat() {
        return assessFormat;
    }

    public void setAssessFormat(String assessFormat) {
        this.assessFormat = assessFormat;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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
