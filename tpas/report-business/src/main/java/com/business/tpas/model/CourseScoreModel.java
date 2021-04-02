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
 * @classname CourseScoreModel
 * @description 授课绩效model
 * @date 2021/4/3
 **/

@ApiModel(value = "授课绩效model")
public class CourseScoreModel {

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


    @ExcelProperty(value = "课程编码")
    @ApiModelProperty(value = "课程编码")
    private String courseCode;

    @ExcelProperty(value = "课程名称")
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ExcelIgnore
    @ApiModelProperty(value = "课程课时具体信息编码")
    private Long courseHoursId;

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

    /**
     * 数据最新操作时间
     */
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

    public Long getCourseHoursId() {
        return courseHoursId;
    }

    public void setCourseHoursId(Long courseHoursId) {
        this.courseHoursId = courseHoursId;
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

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }
}
