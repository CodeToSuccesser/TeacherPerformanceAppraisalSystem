package com.business.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 论文指导修改记录表,修改用户:教师/管理员
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class PaperModifyRecord implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 论文指导信息编码
     */
    private Long paperId;

    /**
     * 提出修改用户类型, 默认0 教师, 1 管理员
     */
    private Integer applyType;

    /**
     * 提出修改用户编码, teachar_id或者admin_id
     */
    private Long applyId;

    /**
     * 专业信息编码
     */
    private Long majorId;

    /**
     * 指导学生人数, 默认0
     */
    private Integer studentNumber;

    /**
     * 学期, 默认1 第一学期, 2 第二学期
     */
    private Integer semester;

    /**
     * 学年
     */
    private String schoolYear;

    /**
     * 备注
     */
    private String remark;

    /**
     * 逻辑删除标识, 0 无删除, 1 已删除
     */
    private Integer isDeleted;

    /**
     * 修改专业信息编码
     */
    private Long modifyMajorId;

    /**
     * 修改指导学生人数
     */
    private Integer modifyStudentNumber;

    /**
     * 修改学期
     */
    private Integer modifySemester;

    /**
     * 修改学年
     */
    private String modifySchoolYear;

    /**
     * 修改备注
     */
    private String modifyRemark;

    /**
     * 处理的管理员编码
     */
    private Long adminId;

    /**
     * 管理员处理时间
     */
    private Date checkTime;

    /**
     * 管理员处理结果, 默认0 未处理, 1 通过, 2 驳回
     */
    private Integer checkResult;

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

    public Long getPaperId() {
        return paperId;
    }

    public void setPaperId(Long paperId) {
        this.paperId = paperId;
    }

    public Integer getApplyType() {
        return applyType;
    }

    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }

    public Long getApplyId() {
        return applyId;
    }

    public void setApplyId(Long applyId) {
        this.applyId = applyId;
    }

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
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

    public Long getModifyMajorId() {
        return modifyMajorId;
    }

    public void setModifyMajorId(Long modifyMajorId) {
        this.modifyMajorId = modifyMajorId;
    }

    public Integer getModifyStudentNumber() {
        return modifyStudentNumber;
    }

    public void setModifyStudentNumber(Integer modifyStudentNumber) {
        this.modifyStudentNumber = modifyStudentNumber;
    }

    public Integer getModifySemester() {
        return modifySemester;
    }

    public void setModifySemester(Integer modifySemester) {
        this.modifySemester = modifySemester;
    }

    public String getModifySchoolYear() {
        return modifySchoolYear;
    }

    public void setModifySchoolYear(String modifySchoolYear) {
        this.modifySchoolYear = modifySchoolYear;
    }

    public String getModifyRemark() {
        return modifyRemark;
    }

    public void setModifyRemark(String modifyRemark) {
        this.modifyRemark = modifyRemark;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Date getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(Date checkTime) {
        this.checkTime = checkTime;
    }

    public Integer getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Integer checkResult) {
        this.checkResult = checkResult;
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
        return "PaperModifyRecord{" +
        "id=" + id +
        ", paperId=" + paperId +
        ", applyType=" + applyType +
        ", applyId=" + applyId +
        ", majorId=" + majorId +
        ", studentNumber=" + studentNumber +
        ", semester=" + semester +
        ", schoolYear=" + schoolYear +
        ", remark=" + remark +
        ", isDeleted=" + isDeleted +
        ", modifyMajorId=" + modifyMajorId +
        ", modifyStudentNumber=" + modifyStudentNumber +
        ", modifySemester=" + modifySemester +
        ", modifySchoolYear=" + modifySchoolYear +
        ", modifyRemark=" + modifyRemark +
        ", adminId=" + adminId +
        ", checkTime=" + checkTime +
        ", checkResult=" + checkResult +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
