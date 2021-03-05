package com.business.tpas.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 绩效权值参数表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class Params implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * Cn的n值, 1 表示C1, range[1, 13]
     */
    private Integer cNum;

    /**
     * m表示第m个Cn
     */
    private Integer cOpction;

    /**
     * 参数权值
     */
    private BigDecimal value;

    /**
     * 操作管理员编号
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

    public Integer getcNum() {
        return cNum;
    }

    public void setcNum(Integer cNum) {
        this.cNum = cNum;
    }

    public Integer getcOpction() {
        return cOpction;
    }

    public void setcOpction(Integer cOpction) {
        this.cOpction = cOpction;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
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
        return "Params{" +
        "id=" + id +
        ", cNum=" + cNum +
        ", cOpction=" + cOpction +
        ", value=" + value +
        ", adminId=" + adminId +
        ", remark=" + remark +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
