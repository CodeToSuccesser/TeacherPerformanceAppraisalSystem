package com.management.tpas.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dude
 * @version 1.0
 * @classname UserInfoSearchModel
 * @description 用户信息查询类
 * @date 2021/3/29
 **/
@ApiModel(value = "用户信息查询类")
public class UserInfoSearchModel extends PageModel {

    @JsonSerialize(using = com.fasterxml.jackson.databind.ser.std.ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "用户姓名")
    private String userName;

    @ApiModelProperty(value = "唯一登录名, 默认教务员工号")
    private String logName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }
}
