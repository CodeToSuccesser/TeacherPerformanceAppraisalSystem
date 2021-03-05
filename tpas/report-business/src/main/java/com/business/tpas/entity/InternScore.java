package com.business.tpas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 实习带队工作量统计表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class InternScore implements Serializable {

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
     * 实习指信息编码
     */
    private Long internId;

    /**
     * c14权值
     */
    private BigDecimal c14Value;

    /**
     * c15权值
     */
    private BigDecimal c15Value;

    /**
     * 实习指导总分
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
    private Date updateTime;

    private Date createTime;

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

    public Long getInternId() {
        return internId;
    }

    public void setInternId(Long internId) {
        this.internId = internId;
    }

    public BigDecimal getC14Value() {
        return c14Value;
    }

    public void setC14Value(BigDecimal c14Value) {
        this.c14Value = c14Value;
    }

    public BigDecimal getC15Value() {
        return c15Value;
    }

    public void setC15Value(BigDecimal c15Value) {
        this.c15Value = c15Value;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "InternScore{" +
        "id=" + id +
        ", teacherId=" + teacherId +
        ", internId=" + internId +
        ", c14Value=" + c14Value +
        ", c15Value=" + c15Value +
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
