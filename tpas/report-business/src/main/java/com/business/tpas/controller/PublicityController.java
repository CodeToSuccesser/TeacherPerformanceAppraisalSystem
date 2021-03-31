package com.business.tpas.controller;


import com.business.tpas.model.OtherPerformanceModel;
import com.business.tpas.model.PublicityModel;
import com.business.tpas.model.PublicitySearchModel;
import com.business.tpas.service.PublicityService;
import com.github.pagehelper.PageInfo;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 公示信息表 前端控制器
 * </p>
 *
 * @since 2021-03-30
 */
@RestController
@RequestMapping("/publicity")
@Api(tags = {"公示信息"})
public class PublicityController {

    @Autowired
    private PublicityService publicityService;

    @ApiOperation(value = "根据条件查询公示信息", notes = "根据条件查询公示信息")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = OtherPerformanceModel.class),
        @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/getPublicity")
    public BaseResponse<?> getPublicity(@RequestBody PublicitySearchModel searchModel) {
        PageInfo<PublicityModel> publicityModelPageInfo=
            publicityService.getPublicityByPage(searchModel);
        return new BaseResponse<>(publicityModelPageInfo);
    }

    @ApiOperation(value = "修改单条公示信息", notes = "修改单条公示信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{id}/modify")
    public BaseResponse<?> modifyPublicity(@PathVariable("id") Long id, @RequestBody PublicityModel publicityModel) {
        validateModifyInsertPublicityParam(publicityModel);
        publicityModel.setId(id);
        publicityService.modifyPublicity(publicityModel);
        return new BaseResponse<>();
    }


    @ApiOperation(value = "删除公示信息", notes = "删除公示信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deletePublicity")
    public BaseResponse<?> deletePublicity(@RequestBody List<Long> ids) {
        publicityService.deletePublicity(ids);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "插入单条公示信息" ,notes = "插入单条公示信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/insert")
    public BaseResponse<?> insertPublicity(@RequestBody PublicityModel publicityModel) {
        validateModifyInsertPublicityParam(publicityModel);
        publicityService.insertPublicity(publicityModel);
        return new BaseResponse<>();
    }

    /**
     * 校验修改插入参数
     * @param publicityModel
     */
    private void validateModifyInsertPublicityParam(PublicityModel publicityModel) {
        if (StringUtils.isBlank(publicityModel.getTitle())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "标题为空");
        }
        if (StringUtils.isEmpty(publicityModel.getContent())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "内容为空");
        }
    }

}

