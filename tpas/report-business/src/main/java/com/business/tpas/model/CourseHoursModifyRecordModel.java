package com.business.tpas.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description 课时信息修改申请记录模型层
 **/
@ApiModel("课时信息修改申请记录模型层")
public class CourseHoursModifyRecordModel {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 课时信息编码
     */
    @ApiModelProperty(value = "课时信息编码")
    private Long hoursId;

    @ApiModelProperty(value = "课程名称")
    private String courseName;

    @ApiModelProperty(value = "课程编号")
    private String courseCode;

    @ApiModelProperty(value = "课程id")
    private Long courseId;

    @ApiModelProperty(value = "学期")
    private Integer semester;

    @ApiModelProperty(value = "学年")
    private String schoolYear;

    @ApiModelProperty(value = "开课学院")
    private String institute;

    /**
     * 提出修改用户类型, 默认0 教师, 1 管理员
     */
    @ApiModelProperty(value = "提出修改用户类型, 默认0 教师, 1 管理员")
    private Integer applyType;

    /**
     * 提出修改用户编码, teachar_id或者admin_id
     */
    @ApiModelProperty(value = "提出修改用户编码, teachar_id或者admin_id")
    private Long applyId;

    /**
     * 已选学生人数, 非空
     */
    @ApiModelProperty(value = "已选学生人数, 非空")
    private Integer selectedStudent;

    /**
     * 学时
     */
    @ApiModelProperty(value = "学时")
    private BigDecimal courseCridet;

    /**
     * 总学时数, 非空
     */
    @ApiModelProperty(value = "总学时数, 非空")
    private BigDecimal totalHours;

    /**
     * 起止周, 非空
     */
    @ApiModelProperty(value = "起止周, 非空")
    private String peroid;

    /**
     * 教学班组成, 非空
     */
    @ApiModelProperty(value = "教学班组成, 非空")
    private String classed;

    /**
     * 修改已选学生人数
     */
    @ApiModelProperty(value = "修改已选学生人数")
    private Integer modifySelectedStudent;

    /**
     * 修改学分
     */
    @ApiModelProperty(value = "修改学分")
    private BigDecimal modifyCourseCridet;

    /**
     * 修改总学时数
     */
    @ApiModelProperty(value = "修改总学时数")
    private BigDecimal modifyTotalHours;

    /**
     * 修改起止周
     */
    @ApiModelProperty(value = "修改起止周")
    private String modifyPeroid;

    /**
     * 修改教学班组成
     */
    @ApiModelProperty(value = "修改教学班组成")
    private String modifyClassed;

    /**
     * 处理的管理员编码
     */
    @ApiModelProperty(value = "处理的管理员编码")
    private Long adminId;

    /**
     * 管理员处理时间
     */
    @ApiModelProperty(value = "管理员处理时间")
    private Date checkTime;

    /**
     * 管理员处理结果, 默认0 未处理, 1 通过, 2 驳回
     */
    @ApiModelProperty(value = "管理员处理结果, 默认0 未处理, 1 通过, 2 驳回")
    private Integer checkResult;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 数据最新操作时间
     */
    @ApiModelProperty(value = "数据最新操作时间")
    private Date updateTime;

    @ApiModelProperty(value = "插入时间")
    private Date createTime;

    /**
     * 讲课学时, 默认0
     */
    @ApiModelProperty(value = "讲课学时, 默认0")
    private Integer teachingHours;

    /**
     * 修改后的讲课学时
     */
    @ApiModelProperty(value = "修改后的讲课学时")
    private Integer modifyTeachingHours;

    /**
     * 上机学时, 默认0
     */
    @ApiModelProperty(value = "上机学时, 默认0")
    private Integer computerHours;

    /**
     * 修改后的上机学时
     */
    @ApiModelProperty(value = "修改后的上机学时")
    private Integer modifyComputerHours;

    /**
     * 实验学时, 默认0
     */
    @ApiModelProperty(value = "实验学时, 默认0")
    private Integer experimentHours;

    /**
     * 修改后的实验学时
     */
    @ApiModelProperty(value = "修改后的实验学时")
    private Integer modifyExperimentHours;

    @ApiModelProperty(value = "逻辑删除标志")
    private Integer isDeleted;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getHoursId() {
        return hoursId;
    }

    public void setHoursId(Long hoursId) {
        this.hoursId = hoursId;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Integer getSelectedStudent() {
        return selectedStudent;
    }

    public void setSelectedStudent(Integer selectedStudent) {
        this.selectedStudent = selectedStudent;
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

    public Integer getModifySelectedStudent() {
        return modifySelectedStudent;
    }

    public void setModifySelectedStudent(Integer modifySelectedStudent) {
        this.modifySelectedStudent = modifySelectedStudent;
    }

    public BigDecimal getModifyCourseCridet() {
        return modifyCourseCridet;
    }

    public void setModifyCourseCridet(BigDecimal modifyCourseCridet) {
        this.modifyCourseCridet = modifyCourseCridet;
    }

    public BigDecimal getModifyTotalHours() {
        return modifyTotalHours;
    }

    public void setModifyTotalHours(BigDecimal modifyTotalHours) {
        this.modifyTotalHours = modifyTotalHours;
    }

    public String getModifyPeroid() {
        return modifyPeroid;
    }

    public void setModifyPeroid(String modifyPeroid) {
        this.modifyPeroid = modifyPeroid;
    }

    public String getModifyClassed() {
        return modifyClassed;
    }

    public void setModifyClassed(String modifyClassed) {
        this.modifyClassed = modifyClassed;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
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

    public Integer getTeachingHours() {
        return teachingHours;
    }

    public void setTeachingHours(Integer teachingHours) {
        this.teachingHours = teachingHours;
    }

    public Integer getModifyTeachingHours() {
        return modifyTeachingHours;
    }

    public void setModifyTeachingHours(Integer modifyTeachingHours) {
        this.modifyTeachingHours = modifyTeachingHours;
    }

    public Integer getComputerHours() {
        return computerHours;
    }

    public void setComputerHours(Integer computerHours) {
        this.computerHours = computerHours;
    }

    public Integer getModifyComputerHours() {
        return modifyComputerHours;
    }

    public void setModifyComputerHours(Integer modifyComputerHours) {
        this.modifyComputerHours = modifyComputerHours;
    }

    public Integer getExperimentHours() {
        return experimentHours;
    }

    public void setExperimentHours(Integer experimentHours) {
        this.experimentHours = experimentHours;
    }

    public Integer getModifyExperimentHours() {
        return modifyExperimentHours;
    }

    public void setModifyExperimentHours(Integer modifyExperimentHours) {
        this.modifyExperimentHours = modifyExperimentHours;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
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

    public String getInstitute() {
        return institute;
    }

    public void setInstitute(String institute) {
        this.institute = institute;
    }


}
