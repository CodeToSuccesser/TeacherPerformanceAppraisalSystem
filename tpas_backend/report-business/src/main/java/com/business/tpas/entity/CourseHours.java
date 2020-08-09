package com.business.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 课程课时具体信息表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class CourseHours implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程基本信息编码
     */
    private Long courseId;

    /**
     * 教师编码
     */
    private Long teacherId;

    /**
     * 课程人数, 默认0
     */
    private Integer studentNumber;

    /**
     * 课程总容量, 默认0
     */
    private Integer totalCapacity;

    /**
     * 已选学生数, 默认0
     */
    private Integer selectedStudent;

    /**
     * 周学时
     */
    private String weekHours;

    /**
     * 起止周
     */
    private String peroid;

    /**
     * 教学班组成
     */
    private String classed;

    /**
     * 讲课学时, 默认0
     */
    private Integer teachingHours;

    /**
     * 上机学时, 默认0
     */
    private Integer computerHours;

    /**
     * 实验学时, 默认0
     */
    private Integer experimentHours;

    /**
     * 选课编号
     */
    private String selectionNumber;

    /**
     * 学生学院
     */
    private String studentsInstitute;

    /**
     * 学期, 默认1 第一学期, 2 第二学期
     */
    private Integer semester;

    /**
     * 学年
     */
    private Integer schoolYear;

    /**
     * 管理员编码
     */
    private Long adminId;

    /**
     * 主讲辅讲标记, 默认0 主讲, 1 辅讲
     */
    private Integer primarySecondary;

    /**
     * 实验批数, 默认1
     */
    private Integer expNumber;

    /**
     * 实验每批次人数, 默认0
     */
    private Integer expPerNumber;

    /**
     * 上课时间
     */
    private String courseTime;

    /**
     * 备注
     */
    private String remark;

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

    public Integer getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(Integer schoolYear) {
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
        return "CourseHours{" +
        "id=" + id +
        ", courseId=" + courseId +
        ", teacherId=" + teacherId +
        ", studentNumber=" + studentNumber +
        ", totalCapacity=" + totalCapacity +
        ", selectedStudent=" + selectedStudent +
        ", weekHours=" + weekHours +
        ", peroid=" + peroid +
        ", classed=" + classed +
        ", teachingHours=" + teachingHours +
        ", computerHours=" + computerHours +
        ", experimentHours=" + experimentHours +
        ", selectionNumber=" + selectionNumber +
        ", studentsInstitute=" + studentsInstitute +
        ", semester=" + semester +
        ", schoolYear=" + schoolYear +
        ", adminId=" + adminId +
        ", primarySecondary=" + primarySecondary +
        ", expNumber=" + expNumber +
        ", expPerNumber=" + expPerNumber +
        ", courseTime=" + courseTime +
        ", remark=" + remark +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
