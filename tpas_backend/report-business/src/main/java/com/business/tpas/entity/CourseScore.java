package com.business.tpas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 课程工作量统计表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class CourseScore implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 教师编码
     */
    private Long teacherId;

    /**
     * 课程课时具体信息编码
     */
    private Long courseHoursId;

    /**
     * c1权值
     */
    private BigDecimal c1Value;

    /**
     * c2权值
     */
    private BigDecimal c2Value;

    /**
     * c3权值
     */
    private BigDecimal c3Value;

    /**
     * c4权值
     */
    private BigDecimal c4Value;

    /**
     * c5权值
     */
    private BigDecimal c5Value;

    /**
     * c6权值
     */
    private BigDecimal c6Value;

    /**
     * c7权值
     */
    private BigDecimal c7Value;

    /**
     * c8权值
     */
    private BigDecimal c8Value;

    /**
     * 授课总分
     */
    private BigDecimal total;

    /**
     * 学期, 默认1 第一学期, 2 第二学期
     */
    private Integer semester;

    /**
     * 学年
     */
    private Integer schoolYear;

    /**
     * 处理的管理员编码
     */
    private Long adminId;

    /**
     * 数据最新操作时间
     */
    private LocalDateTime updateTime;

    private LocalDateTime createTime;

    /**
     * 逻辑删除标识
     */
    private Integer isDeleted;


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

    public BigDecimal getC1Value() {
        return c1Value;
    }

    public void setC1Value(BigDecimal c1Value) {
        this.c1Value = c1Value;
    }

    public BigDecimal getC2Value() {
        return c2Value;
    }

    public void setC2Value(BigDecimal c2Value) {
        this.c2Value = c2Value;
    }

    public BigDecimal getC3Value() {
        return c3Value;
    }

    public void setC3Value(BigDecimal c3Value) {
        this.c3Value = c3Value;
    }

    public BigDecimal getC4Value() {
        return c4Value;
    }

    public void setC4Value(BigDecimal c4Value) {
        this.c4Value = c4Value;
    }

    public BigDecimal getC5Value() {
        return c5Value;
    }

    public void setC5Value(BigDecimal c5Value) {
        this.c5Value = c5Value;
    }

    public BigDecimal getC6Value() {
        return c6Value;
    }

    public void setC6Value(BigDecimal c6Value) {
        this.c6Value = c6Value;
    }

    public BigDecimal getC7Value() {
        return c7Value;
    }

    public void setC7Value(BigDecimal c7Value) {
        this.c7Value = c7Value;
    }

    public BigDecimal getC8Value() {
        return c8Value;
    }

    public void setC8Value(BigDecimal c8Value) {
        this.c8Value = c8Value;
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

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "CourseScore{" +
        "id=" + id +
        ", teacherId=" + teacherId +
        ", courseHoursId=" + courseHoursId +
        ", c1Value=" + c1Value +
        ", c2Value=" + c2Value +
        ", c3Value=" + c3Value +
        ", c4Value=" + c4Value +
        ", c5Value=" + c5Value +
        ", c6Value=" + c6Value +
        ", c7Value=" + c7Value +
        ", c8Value=" + c8Value +
        ", total=" + total +
        ", semester=" + semester +
        ", schoolYear=" + schoolYear +
        ", adminId=" + adminId +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        ", isDeleted=" + isDeleted +
        "}";
    }
}
