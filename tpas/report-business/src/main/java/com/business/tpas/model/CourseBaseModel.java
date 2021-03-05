package com.business.tpas.model;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.business.tpas.utils.CourseBaseContentConverter;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Pietra
 * @version 1.0
 * @classname CourseBaseModel
 * @description 课程基本信息模型层，用于前端数据交互
 **/
@ApiModel(value = "课程基本信息模型层", description = "课程基本信息模型层")
public class CourseBaseModel {

    @ExcelIgnore
    @ApiModelProperty(value = "id")
    private Long id;

    @ExcelProperty("课程编号")
    @ApiModelProperty(value = "课程编号")
    private String courseCode;

    @ExcelProperty("课程名称")
    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ExcelProperty(value = "课程性质", converter = CourseBaseContentConverter.class)
    @ApiModelProperty(value = "课程性质, 0 必修, 1 选修")
    private Integer courseCharacter;

    @ExcelProperty(value = "课程类别", converter = CourseBaseContentConverter.class)
    @ApiModelProperty(value = "课程类别, 0专业方向, 1 专业核心, 2 教师教育, 3 实践教学, 4 大类教育'")
    private Integer courseType;

    @ExcelProperty("学分")
    @ApiModelProperty(value = "学分")
    private BigDecimal courseCridet;

    @ExcelProperty("总学时")
    @ApiModelProperty(value = "总学时数")
    private BigDecimal totalHours;

    @ExcelProperty("开课学院")
    @ApiModelProperty(value = "开课学院")
    private String institute;

    @ExcelProperty("首次开课时间")
    @ApiModelProperty(value = "首次开课时间, 默认为信息录入时间")
    private Date firstClassTime;

    @ExcelProperty(value = "是否双语授课", converter = CourseBaseContentConverter.class)
    @ApiModelProperty(value = "是否双语授课,0 非双语, 1 双语")
    private Integer isBilingual;

    @ExcelProperty("选用教材时间")
    @ApiModelProperty(value = "选用教材时间, 默认为信息录入时间")
    private Date newTextbookTime;

    @ExcelProperty(value = "软硬件课程", converter = CourseBaseContentConverter.class)
    @ApiModelProperty(value = "软硬件课程, 0 软件, 1 硬件")
    private Integer softHard;

    @ExcelProperty(value = "学生类型", converter = CourseBaseContentConverter.class)
    @ApiModelProperty(value = "学生类型, 0 本科生, 1 专科生, 2 研究生")
    private Integer studentType;

    @ExcelIgnore
    @ApiModelProperty(value = "管理员编号")
    private Long adminId;

    @ExcelProperty(value = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    @ExcelIgnore
    @ApiModelProperty(value = "逻辑删除标志")
    private Integer isDeleted;

    @ExcelIgnore
    @ApiModelProperty(value = "数据最新操作时间")
    private Date updateTime;

    @ExcelIgnore
    private Date createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getCourseCharacter() {
        return courseCharacter;
    }

    public void setCourseCharacter(Integer courseCharacter) {
        this.courseCharacter = courseCharacter;
    }

    public Integer getCourseType() {
        return courseType;
    }

    public void setCourseType(Integer courseType) {
        this.courseType = courseType;
    }

    public BigDecimal getCourseCridet() {
        return courseCridet;
    }

    public void setCourseCridet(BigDecimal courseCridet) {
        this.courseCridet = courseCridet;
    }

    public BigDecimal getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(BigDecimal totalHours) {
        this.totalHours = totalHours;
    }

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public Date getFirstClassTime() {
        return firstClassTime;
    }

    public void setFirstClassTime(Date firstClassTime) {
        this.firstClassTime = firstClassTime;
    }

    public Integer getIsBilingual() {
        return isBilingual;
    }

    public void setIsBilingual(Integer isBilingual) {
        this.isBilingual = isBilingual;
    }

    public Date getNewTextbookTime() {
        return newTextbookTime;
    }

    public void setNewTextbookTime(Date newTextbookTime) {
        this.newTextbookTime = newTextbookTime;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
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

    public Integer getSoftHard() {
        return softHard;
    }

    public void setSoftHard(Integer softHard) {
        this.softHard = softHard;
    }

    public Integer getStudentType() {
        return studentType;
    }

    public void setStudentType(Integer studentType) {
        this.studentType = studentType;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "CourseBaseModel{" + "id=" + id + ", courseCode='" + courseCode + '\'' + ", courseName='" + courseName
            + '\'' + ", courseCharacter=" + courseCharacter + ", courseType=" + courseType + ", courseCridet="
            + courseCridet + ", totalHours=" + totalHours + ", institute='" + institute + '\'' + ", firstClassTime="
            + firstClassTime + ", isBilingual=" + isBilingual + ", newTextbookTime=" + newTextbookTime + ", softHard="
            + softHard + ", studentType=" + studentType + ", adminId=" + adminId + ", remark='" + remark + '\''
            + ", isDeleted=" + isDeleted + ", updateTime=" + updateTime + ", createTime=" + createTime + '}';
    }
}
