package com.business.tpas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 教师绩效核算记录表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class Assessment implements Serializable {

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
     * 学期, 默认1 第一学期, 2 第二学期
     */
    private Integer semester;

    /**
     * 学年
     */
    private Integer schoolYear;

    /**
     * 授课总分
     */
    private BigDecimal courseQuality;

    /**
     * 论文指导总分
     */
    private BigDecimal paperQuality;

    /**
     * 实习指导总分
     */
    private BigDecimal internQuality;

    /**
     * 其他总分
     */
    private BigDecimal otherQuality;

    /**
     * 总分
     */
    private BigDecimal totalQuality;

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

    public BigDecimal getCourseQuality() {
        return courseQuality;
    }

    public void setCourseQuality(BigDecimal courseQuality) {
        this.courseQuality = courseQuality;
    }

    public BigDecimal getPaperQuality() {
        return paperQuality;
    }

    public void setPaperQuality(BigDecimal paperQuality) {
        this.paperQuality = paperQuality;
    }

    public BigDecimal getInternQuality() {
        return internQuality;
    }

    public void setInternQuality(BigDecimal internQuality) {
        this.internQuality = internQuality;
    }

    public BigDecimal getOtherQuality() {
        return otherQuality;
    }

    public void setOtherQuality(BigDecimal otherQuality) {
        this.otherQuality = otherQuality;
    }

    public BigDecimal getTotalQuality() {
        return totalQuality;
    }

    public void setTotalQuality(BigDecimal totalQuality) {
        this.totalQuality = totalQuality;
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
        return "Assessment{" +
        "id=" + id +
        ", teacherId=" + teacherId +
        ", semester=" + semester +
        ", schoolYear=" + schoolYear +
        ", courseQuality=" + courseQuality +
        ", paperQuality=" + paperQuality +
        ", internQuality=" + internQuality +
        ", otherQuality=" + otherQuality +
        ", totalQuality=" + totalQuality +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
