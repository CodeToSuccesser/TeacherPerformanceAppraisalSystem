package com.business.tpas.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author dude
 * @version 1.0
 * @classname PaperScoreModel
 * @description 论文分数
 * @date 2021/4/3
 **/
@ApiModel(value = "论文分数model")
public class PaperScoreModel {

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

    @ExcelProperty(value = "指导人数")
    @ApiModelProperty(value = "指导人数")
    private Integer studentNumber;

    @ApiModelProperty(value = "论文指导信息编码")
    private Long paperId;

    @ApiModelProperty(value = "选用的绩效规则id")
    private Long assessRuleId;

    @ApiModelProperty(value = "选用的绩效规则")
    private String assessDetail;

    @ApiModelProperty(value = "统计得出绩效规则")
    private String assessFormat;

    @ApiModelProperty(value = "论文总分")
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

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
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

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }
}
