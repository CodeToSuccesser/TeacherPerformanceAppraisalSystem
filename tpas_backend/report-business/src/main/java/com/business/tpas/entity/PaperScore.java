package com.business.tpas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 论文指导工作量统计表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class PaperScore implements Serializable {

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
     * 论文指导信息编码
     */
    private Long paperId;

    /**
     * c9权值
     */
    private BigDecimal c9Value;

    /**
     * c10权值
     */
    private BigDecimal c10Value;

    /**
     * c11权值
     */
    private BigDecimal c11Value;

    /**
     * c12权值
     */
    private BigDecimal c12Value;

    /**
     * c13权值
     */
    private BigDecimal c13Value;

    /**
     * 论文指导总分
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

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public BigDecimal getC9Value() {
        return c9Value;
    }

    public void setC9Value(BigDecimal c9Value) {
        this.c9Value = c9Value;
    }

    public BigDecimal getC10Value() {
        return c10Value;
    }

    public void setC10Value(BigDecimal c10Value) {
        this.c10Value = c10Value;
    }

    public BigDecimal getC11Value() {
        return c11Value;
    }

    public void setC11Value(BigDecimal c11Value) {
        this.c11Value = c11Value;
    }

    public BigDecimal getC12Value() {
        return c12Value;
    }

    public void setC12Value(BigDecimal c12Value) {
        this.c12Value = c12Value;
    }

    public BigDecimal getC13Value() {
        return c13Value;
    }

    public void setC13Value(BigDecimal c13Value) {
        this.c13Value = c13Value;
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
        return "PaperScore{" +
        "id=" + id +
        ", teacherId=" + teacherId +
        ", paperId=" + paperId +
        ", c9Value=" + c9Value +
        ", c10Value=" + c10Value +
        ", c11Value=" + c11Value +
        ", c12Value=" + c12Value +
        ", c13Value=" + c13Value +
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
