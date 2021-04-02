package com.business.tpas.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import com.business.tpas.utils.CourseHoursContentConverter;
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

    @ExcelIgnore
    @TableId(value = "id")
    private Long id;

    @ExcelIgnore
    @ApiModelProperty(value = "教师编码")
    private Long teacherId;

    @ExcelProperty(value = "教师编号")
    @ApiModelProperty(value = "教师编号")
    private String teacherCode;

    @ExcelProperty(value = "教师姓名")
    @ApiModelProperty(value = "教师姓名")
    private String teacherName;

    @ExcelIgnore
    @ApiModelProperty(value = "实习指信息编码")
    private Long internId;

    @ExcelProperty(value = "师范带队人数")
    @ApiModelProperty(value = "师范带队人数")
    private Integer normalPractice;

    /**
     * 非师范实习带队人数, 默认0
     */
    @ExcelProperty(value = "非师范带队人数")
    @ApiModelProperty(value = "非师范带队人数")
    private Integer nonNormalPractice;

    /**
     * 校内实习带队人数, 默认0
     */
    @ExcelProperty(value = "校内实习带队人数")
    @ApiModelProperty(value = "校内实习带队人数")
    private Integer schoolPractice;

    @ExcelIgnore
    @ApiModelProperty(value = "选用的绩效规则id")
    private Long assessRuleId;

    @ExcelProperty(value = "绩效规则")
    @ApiModelProperty(value = "选用的绩效规则")
    private String assessDetail;

    @ExcelProperty(value = "绩效公式")
    @ApiModelProperty(value = "统计得出绩效规则")
    private String assessFormat;

    @ExcelProperty(value = "得分")
    @ApiModelProperty(value = "授课总分")
    private BigDecimal total;

    @ExcelProperty(value = "学期", converter = CourseHoursContentConverter.class)
    @ApiModelProperty(value = "学期")
    private Integer semester;

    @ExcelProperty(value = "学年")
    @ApiModelProperty(value = "学年")
    private String schoolYear;

    @ExcelIgnore
    @ApiModelProperty(value = "处理的管理员编码")
    private Long adminId;

    @ExcelIgnore
    private Date updateTime;
    @ExcelIgnore
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

    public Integer getNormalPractice() {
        return normalPractice;
    }

    public void setNormalPractice(Integer normalPractice) {
        this.normalPractice = normalPractice;
    }

    public Integer getNonNormalPractice() {
        return nonNormalPractice;
    }

    public void setNonNormalPractice(Integer nonNormalPractice) {
        this.nonNormalPractice = nonNormalPractice;
    }

    public Integer getSchoolPractice() {
        return schoolPractice;
    }

    public void setSchoolPractice(Integer schoolPractice) {
        this.schoolPractice = schoolPractice;
    }
}
