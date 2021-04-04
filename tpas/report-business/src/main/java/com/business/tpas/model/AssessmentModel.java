package com.business.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname AssessmentModel
 * @description 绩效总分
 * @date 2021/4/4
 **/
@ApiModel(value = "绩效总分")
public class AssessmentModel {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "教师编码")
    private String teacherCode;

    @ApiModelProperty(value = "教师姓名")
    private String teacherName;

    @ApiModelProperty(value = "学期, 默认1 第一学期, 2 第二学期")
    private Integer semester;

    @ApiModelProperty(value = "学年")
    private String schoolYear;

    @ApiModelProperty(value = "授课总分")
    private BigDecimal courseQuality;

    @ApiModelProperty(value = "论文指导总分")
    private BigDecimal paperQuality;

    @ApiModelProperty(value = "实习指导总分")
    private BigDecimal internQuality;

    @ApiModelProperty(value = "其他总分")
    private BigDecimal otherQuality;

    @ApiModelProperty(value = "总分")
    private BigDecimal totalQuality;

    private Date updateTime;

    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public BigDecimal getCourseQuality() {
        return courseQuality;
    }

    public void setCourseQuality(BigDecimal courseQuality) {
        this.courseQuality = courseQuality;
    }

    public BigDecimal getPaperQuality() {
        return paperQuality;
    }

    public void setPaperQuality(BigDecimal paperQuality) {
        this.paperQuality = paperQuality;
    }

    public BigDecimal getInternQuality() {
        return internQuality;
    }

    public void setInternQuality(BigDecimal internQuality) {
        this.internQuality = internQuality;
    }

    public BigDecimal getOtherQuality() {
        return otherQuality;
    }

    public void setOtherQuality(BigDecimal otherQuality) {
        this.otherQuality = otherQuality;
    }

    public BigDecimal getTotalQuality() {
        return totalQuality;
    }

    public void setTotalQuality(BigDecimal totalQuality) {
        this.totalQuality = totalQuality;
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
