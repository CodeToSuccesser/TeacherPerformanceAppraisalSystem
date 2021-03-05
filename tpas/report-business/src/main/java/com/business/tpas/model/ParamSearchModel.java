package com.business.tpas.model;

import com.management.common.model.PageModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author dude
 * @version 1.0
 * @classname ParamSearchModel
 * @description 权值参数查询类
 * @date 2021/2/27
 **/
@ApiModel(value = "权值参数查询类", description = "权值参数查询类")
public class ParamSearchModel extends PageModel {

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "Cn的n值, 1 表示C1")
    private Integer cNum;

    @ApiModelProperty(value = "m表示第m个Cn")
    private Integer cOption;

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

    public Integer getcOption() {
        return cOption;
    }

    public void setcOption(Integer cOption) {
        this.cOption = cOption;
    }
}
