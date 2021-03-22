package com.management.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dude
 * @version 1.0
 * @classname RoleSearchModel
 * @description 角色查询类
 * @date 2021/3/22
 **/
@ApiModel(value = "角色查询类")
public class RoleSearchModel extends PageModel {

    @ApiModelProperty(value = "角色名称")
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
