package com.business.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 课程基本信息表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class CourseBase implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程编码，唯一，格式 21G39280-2938
     */
    private String courseCode;

    /**
     * 课程性质, 默认0 必修, 1 选修
     */
    private Integer courseCharacter;

    /**
     * 课程类别, 默认0 专业方向, 1 专业核心, 2 教师教育, 3 实践教学, 4 大类教育
     */
    private Integer courseType;

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
     * 是否双语授课, 默认0 非双语, 1 双语
     */
    private Integer isBilingual;

    /**
     * 选用教材时间, 默认为信息录入时间
     */
    private Date newTextbookTime;

    /**
     * 软硬件课程, 默认0 软件, 1 硬件
     */
    private Integer softHard;

    /**
     * 学生类型, 默认0 本科生, 1 专科生, 2 研究生
     */
    private Integer studentType;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    @Override
    public String toString() {
        return "CourseBase{" + "id=" + id + ", courseName='" + courseName + '\'' + ", courseCode='" + courseCode + '\''
            + ", courseCharacter=" + courseCharacter + ", courseType=" + courseType + ", courseCridet=" + courseCridet
            + ", totalHours=" + totalHours + ", institute='" + institute + '\'' + ", firstClassTime=" + firstClassTime
            + ", isBilingual=" + isBilingual + ", newTextbookTime=" + newTextbookTime + ", softHard=" + softHard
            + ", studentType=" + studentType + ", adminId=" + adminId + ", remark='" + remark + '\'' + ", updateTime="
            + updateTime + ", createTime=" + createTime + '}';
    }
}
