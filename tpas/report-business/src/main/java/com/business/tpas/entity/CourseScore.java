package com.business.tpas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 课程工作量统计表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
@ApiModel(value = "授课绩效")
public class CourseScore implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "教师编码")
    private Long teacherId;

    @ApiModelProperty(value = "课程课时具体信息编码")
    private Long courseHoursId;

    @ApiModelProperty(value = "选用的绩效规则id")
    private Long assessRuleId;

    @ApiModelProperty(value = "选用的绩效规则")
    private String assessDetail;

    @ApiModelProperty(value = "统计得出绩效规则")
    private String assessFormat;

    @ApiModelProperty(value = "授课总分")
    private BigDecimal total;

    @ApiModelProperty(value = "学期, 默认1 第一学期, 2 第二学期")
    private Integer semester;

    @ApiModelProperty(value = "学年")
    private String schoolYear;

    @ApiModelProperty(value = "处理的管理员编码")
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

    public Long getCourseHoursId() {
        return courseHoursId;
    }

    public void setCourseHoursId(Long courseHoursId) {
        this.courseHoursId = courseHoursId;
    }

    public Long getAssessRuleId() {
        return assessRuleId;
    }

    public void setAssessRuleId(Long assessRuleId) {
        this.assessRuleId = assessRuleId;
    }

    public String getAssessDetail() {
        return assessDetail;
    }

    public void setAssessDetail(String assessDetail) {
        this.assessDetail = assessDetail;
    }

    public String getAssessFormat() {
        return assessFormat;
    }

    public void setAssessFormat(String assessFormat) {
        this.assessFormat = assessFormat;
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

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
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
        return "CourseScore{" +
                "id=" + id +
                ", teacherId=" + teacherId +
                ", courseHoursId=" + courseHoursId +
                ", assessRuleId=" + assessRuleId +
                ", assessDetail='" + assessDetail + '\'' +
                ", assessFormat='" + assessFormat + '\'' +
                ", total=" + total +
                ", semester=" + semester +
                ", schoolYear=" + schoolYear +
                ", adminId=" + adminId +
                ", updateTime=" + updateTime +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
