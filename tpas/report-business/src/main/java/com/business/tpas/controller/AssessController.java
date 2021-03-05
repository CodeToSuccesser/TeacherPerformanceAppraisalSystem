package com.business.tpas.controller;

import com.management.common.model.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dude
 * @version 1.0
 * @classname AssessController
 * @description 绩效计算相关接口
 * @date 2021/3/1
 **/
@RestController
@RequestMapping("/params/info")
@Api(tags = {"绩效计算相关接口"})
public class AssessController {
    @ApiOperation(value = "编辑权值绩效规则", notes = "编辑权值规则")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/editAssessRule")
    public BaseResponse<?> editAssessRule() {
        return new BaseResponse<>();
    }

    @ApiOperation(value = "删除权值绩效规则", notes = "编辑权值规则")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteAssessRule")
    public BaseResponse<?> deleteAssessRule() {
        return new BaseResponse<>();
    }

    @ApiOperation(value = "绩效统计", notes = "绩效统计")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/calculateAssess")
    public BaseResponse<?> calculateAssess() {
        return new BaseResponse<>();
    }
}
