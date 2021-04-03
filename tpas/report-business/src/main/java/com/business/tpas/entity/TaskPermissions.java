package com.business.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 任务下发权限表
 * </p>
 *
 * @author xxxx
 * @since 2021-03-30
 */
public class TaskPermissions implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 发布者角色
     */
    private String publisherRole;

    /**
     * 任务接受者角色，如有多个，逗号分隔
     */
    private String receiverRoles;

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

    public String getPublisherRole() {
        return publisherRole;
    }

    public void setPublisherRole(String publisherRole) {
        this.publisherRole = publisherRole;
    }

    public String getReceiverRoles() {
        return receiverRoles;
    }

    public void setReceiverRoles(String receiverRoles) {
        this.receiverRoles = receiverRoles;
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
        return "TaskPermissions{" +
        "id=" + id +
        ", publisherRole=" + publisherRole +
        ", receiverRoles=" + receiverRoles +
        ", isDeleted=" + isDeleted +
        ", updateTime=" + updateTime +
        ", createTime=" + createTime +
        "}";
    }
}
