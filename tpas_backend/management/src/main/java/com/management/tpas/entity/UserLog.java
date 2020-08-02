package com.management.tpas.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;

/**
 * <p>
 * 用户访问记录表
 * </p>
 *
 * @author peihua.wu
 * @since 2020-08-01
 */
public class UserLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 远程IP地址
     */
    private String ip;

    /**
     * 用户编码
     */
    private Long userId;

    /**
     * 用户类型, 默认0 教师, 1 管理员
     */
    private Integer userType;

    /**
     * 请求路由
     */
    private String url;

    /**
     * 请求参数
     */
    private String param;

    /**
     * 操作结果码
     */
    private String resultCode;

    /**
     * 操作结果响应信息
     */
    private String resultMsg;

    private LocalDateTime createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultMsg() {
        return resultMsg;
    }

    public void setResultMsg(String resultMsg) {
        this.resultMsg = resultMsg;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserLog{" +
        "id=" + id +
        ", ip=" + ip +
        ", userId=" + userId +
        ", userType=" + userType +
        ", url=" + url +
        ", param=" + param +
        ", resultCode=" + resultCode +
        ", resultMsg=" + resultMsg +
        ", createTime=" + createTime +
        "}";
    }
}
