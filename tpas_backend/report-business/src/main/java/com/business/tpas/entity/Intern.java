package com.business.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 实习指导信息表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class Intern implements Serializable {

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
     * 师范实习带队人数, 默认0
     */
    private Integer normalPractice;

    /**
     * 非师范实习带队人数, 默认0
     */
    private Integer nonNormalPractice;

    /**
     * 校内实习带队人数, 默认0
     */
    private Integer schoolPractice;

    /**
     * 学期, 默认1 第一学期, 2 第二学期
     */
    private Integer semester;

    /**
     * 学年
     */
    private Integer schoolYear;

    /**
     * 逻辑删除标识, 0 无删除, 1 已删除
     */
    private Integer isDeleted;

    /**
     * 备注
     */
    private String remark;

    /**
     * 数据最新操作时间
     */
    private LocalDateTime updateTime;

    private LocalDateTime createTime;


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

    public Integer getNormalPractice() {
        return normalPractice;
    }

    public void setNormalPractice(Integer normalPractice) {
        this.normalPractice = normalPractice;
    }

    public Integer getNonNormalPractice() {
        return nonNormalPractice;
    }

    public void setNonNormalPractice(Integer nonNormalPractice) {
        this.nonNormalPractice = nonNormalPractice;
    }

    public Integer getSchoolPractice() {
        return schoolPractice;
    }

    public void setSchoolPractice(Integer schoolPractice) {
        this.schoolPractice = schoolPractice;
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

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    @Override
    public String toString() {
        return "Intern{" +
        "id=" + id +
        ", teacherId=" + teacherId +
        ", normalPractice=" + normalPractice +
        ", nonNormalPractice=" + nonNormalPractice +
        ", schoolPractice=" + schoolPractice +
        ", semester=" + semester +
        ", schoolYear=" + schoolYear +
        ", isDeleted=" + isDeleted +
        ", remark=" + remark +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
