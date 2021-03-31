package com.business.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 其他教务工作内容
 * </p>
 *
 * @author xxxx
 * @since 2021-03-30
 */
public class OtherPerformance implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 教师id，绩效工作所属用户id
     */
    private Long userId;

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务内容描述
     */
    private String content;

    /**
     * 任务完成评分，百分制
     */
    private Integer score;

    /**
     * 学期, 默认1 第一学期, 2 第二学期
     */
    private Integer semester;

    /**
     * 学年
     */
    private String schoolYear;

    /**
     * 管理员编码
     */
    private Long adminId;

    /**
     * 备注
     */
    private String remark;

    /**
     * 账号逻辑删除标识, 0 无删除, 1 已删除
     */
    private Integer isDeleted;

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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
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
        return "OtherPerformance{" +
        "id=" + id +
        ", userId=" + userId +
        ", title=" + title +
        ", content=" + content +
        ", score=" + score +
        ", semester=" + semester +
        ", schoolYear=" + schoolYear +
        ", adminId=" + adminId +
        ", remark=" + remark +
        ", isDeleted=" + isDeleted +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
