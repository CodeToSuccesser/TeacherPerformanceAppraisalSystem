package com.business.tpas.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.business.tpas.utils.CourseHoursContentConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * @description 课时信息模型层
 **/
@ApiModel(value = "课时信息模型层", description = "课时信息模型层")
public class CourseHoursModel {

    @ExcelIgnore
    @ApiModelProperty(value = "id")
    private Long id;

    @ExcelIgnore
    @ApiModelProperty(value = "课程信息id")
    private Long courseId;

    @ExcelIgnore
    @ApiModelProperty(value = "教师id")
    private Long teacherId;

    @ExcelProperty(value = "课程名称")
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ExcelProperty(value = "教师编号")
    @ApiModelProperty(value = "教师编号")
    private String teacherCode;

    @ExcelProperty(value = "教师姓名")
    @ApiModelProperty(value = "教师姓名")
    private String teacherName;

    @ExcelProperty(value = "课程编号")
    @ApiModelProperty(value = "课程编号")
    private String courseCode;

    @ExcelProperty(value = "人数")
    @ApiModelProperty(value = "课程人数")
    private Integer studentNumber;

    @ExcelProperty(value = "总容量")
    @ApiModelProperty(value = "课程总容量")
    private Integer totalCapacity;

    @ExcelProperty(value = "已选")
    @ApiModelProperty(value = "已选学生")
    private Integer selectedStudent;

    @ExcelProperty(value = "周学时")
    @ApiModelProperty(value = "周学时")
    private String weekHours;

    @ExcelProperty(value = "起止周")
    @ApiModelProperty(value = "起止周")
    private String peroid;

    @ExcelProperty(value = "教学班组成")
    @ApiModelProperty(value = "教学班组成")
    private String classed;

    @ExcelProperty(value = "讲课学时")
    @ApiModelProperty(value = "讲课学时")
    private Integer teachingHours;

    @ExcelProperty(value = "上机学时")
    @ApiModelProperty(value = "上机学时")
    private Integer computerHours;

    @ExcelProperty(value = "实验学时")
    @ApiModelProperty(value = "实验学时")
    private Integer experimentHours;

    @ExcelProperty(value = "选课课号")
    @ApiModelProperty(value = "选课编号")
    private String selectionNumber;

    @ExcelProperty(value = "学生学院")
    @ApiModelProperty(value = "学生学院")
    private String studentsInstitute;

    @ExcelProperty(value = "学期", converter = CourseHoursContentConverter.class)
    @ApiModelProperty(value = "学期")
    private Integer semester;

    @ExcelProperty(value = "学年")
    @ApiModelProperty(value = "学年")
    private String schoolYear;

    @ExcelIgnore
    @ApiModelProperty(value = "管理员编号")
    private Long adminId;

    @ExcelProperty(value = "主讲辅讲", converter = CourseHoursContentConverter.class)
    @ApiModelProperty(value = "主讲辅讲")
    private Integer primarySecondary;

    @ExcelProperty(value = "实验批数")
    @ApiModelProperty(value = "实验批数")
    private Integer expNumber;

    @ExcelProperty(value = "实验每批人数")
    @ApiModelProperty(value = "实验每批人数")
    private Integer expPerNumber;

    @ExcelProperty(value = "上课时间")
    @ApiModelProperty(value = "上课时间")
    private String courseTime;

    @ExcelProperty(value = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    @ExcelIgnore
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ExcelIgnore
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherCode() {
        return teacherCode;
    }

    public void setTeacherCode(String teacherCode) {
        this.teacherCode = teacherCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Integer getTotalCapacity() {
        return totalCapacity;
    }

    public void setTotalCapacity(Integer totalCapacity) {
        this.totalCapacity = totalCapacity;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Integer getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Integer selectedStudent) {
        this.selectedStudent = selectedStudent;
    }

    public String getWeekHours() {
        return weekHours;
    }

    public void setWeekHours(String weekHours) {
        this.weekHours = weekHours;
    }

    public String getPeroid() {
        return peroid;
    }

    public void setPeroid(String peroid) {
        this.peroid = peroid;
    }

    public String getClassed() {
        return classed;
    }

    public void setClassed(String classed) {
        this.classed = classed;
    }

    public Integer getTeachingHours() {
        return teachingHours;
    }

    public void setTeachingHours(Integer teachingHours) {
        this.teachingHours = teachingHours;
    }

    public Integer getComputerHours() {
        return computerHours;
    }

    public void setComputerHours(Integer computerHours) {
        this.computerHours = computerHours;
    }

    public Integer getExperimentHours() {
        return experimentHours;
    }

    public void setExperimentHours(Integer experimentHours) {
        this.experimentHours = experimentHours;
    }

    public String getSelectionNumber() {
        return selectionNumber;
    }

    public void setSelectionNumber(String selectionNumber) {
        this.selectionNumber = selectionNumber;
    }

    public String getStudentsInstitute() {
        return studentsInstitute;
    }

    public void setStudentsInstitute(String studentsInstitute) {
        this.studentsInstitute = studentsInstitute;
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

    public Integer getPrimarySecondary() {
        return primarySecondary;
    }

    public void setPrimarySecondary(Integer primarySecondary) {
        this.primarySecondary = primarySecondary;
    }

    public Integer getExpNumber() {
        return expNumber;
    }

    public void setExpNumber(Integer expNumber) {
        this.expNumber = expNumber;
    }

    public Integer getExpPerNumber() {
        return expPerNumber;
    }

    public void setExpPerNumber(Integer expPerNumber) {
        this.expPerNumber = expPerNumber;
    }

    public String getCourseTime() {
        return courseTime;
    }

    public void setCourseTime(String courseTime) {
        this.courseTime = courseTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "CourseHoursModel{" + "id=" + id + ", courseId=" + courseId + ", teacherId=" + teacherId
            + ", courseName='" + courseName + '\'' + ", teacherCode='" + teacherCode + '\'' + ", teacherName='"
            + teacherName + '\'' + ", courseCode='" + courseCode + '\'' + ", studentNumber=" + studentNumber
            + ", totalCapacity=" + totalCapacity + ", selectedStudent=" + selectedStudent + ", weekHours='" + weekHours
            + '\'' + ", peroid='" + peroid + '\'' + ", classed='" + classed + '\'' + ", teachingHours=" + teachingHours
            + ", computerHours=" + computerHours + ", experimentHours=" + experimentHours + ", selectionNumber='"
            + selectionNumber + '\'' + ", studentsInstitute='" + studentsInstitute + '\'' + ", semester=" + semester
            + ", schoolYear='" + schoolYear + '\'' + ", adminId=" + adminId + ", primarySecondary=" + primarySecondary
            + ", expNumber=" + expNumber + ", expPerNumber=" + expPerNumber + ", courseTime='" + courseTime + '\''
            + ", remark='" + remark + '\'' + ", createTime=" + createTime + ", updateTime=" + updateTime + '}';
    }
}
