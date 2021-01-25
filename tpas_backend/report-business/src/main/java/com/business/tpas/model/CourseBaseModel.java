package com.business.tpas.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Pietra
 * @version 1.0
 * @classname CourseBaseModel
 * @description 课程基本信息模型层，用于前端数据交互
 **/
public class CourseBaseModel {

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程性质, 必修课, 选修课
     */
    private String courseCharacter;

    /**
     * 课程类别,  专业方向,专业核心,教师教育,实践教学,大类教育
     */
    private String courseType;

    /**
     * 学分
     */
    private BigDecimal courseCridet;

    /**
     * 总学时数
     */
    private BigDecimal totalHours;

    /**
     * 开课学院
     */
    private String institute;

    /**
     * 首次开课时间, 默认为信息录入时间
     */
    private Date firstClassTime;

    /**
     * 是否双语授课, 是, 否
     */
    private String isBilingual;

    /**
     * 选用教材时间, 默认为信息录入时间
     */
    private Date newTextbookTime;

    /**
     * 软硬件课程, 软件, 硬件
     */
    private String softHard;

    /**
     * 学生类型, 本科生,专科生,研究生
     */
    private String studentType;

    /**
     * 管理员编号
     */
    private Long adminId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据最新操作时间
     */
    private Date updateTime;

    private Date createTime;

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCharacter() {
        return courseCharacter;
    }

    public void setCourseCharacter(String courseCharacter) {
        this.courseCharacter = courseCharacter;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
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

    public String getIsBilingual() {
        return isBilingual;
    }

    public void setIsBilingual(String isBilingual) {
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


    public String getSoftHard() {
        return softHard;
    }

    public void setSoftHard(String softHard) {
        this.softHard = softHard;
    }

    public String getStudentType() {
        return studentType;
    }

    public void setStudentType(String studentType) {
        this.studentType = studentType;
    }

    @Override
    public String toString() {
        return "CourseBaseModel{" + "courseName='" + courseName + '\'' + ", courseCharacter='" + courseCharacter + '\''
            + ", courseType='" + courseType + '\'' + ", courseCridet=" + courseCridet + ", totalHours=" + totalHours
            + ", institute='" + institute + '\'' + ", firstClassTime=" + firstClassTime + ", isBilingual=" + isBilingual
            + ", newTextbookTime=" + newTextbookTime + ", softHard=" + softHard + ", studentType=" + studentType
            + ", adminId=" + adminId + ", remark='" + remark + '\'' + ", updateTime=" + updateTime + ", createTime="
            + createTime + '}';
    }
}
