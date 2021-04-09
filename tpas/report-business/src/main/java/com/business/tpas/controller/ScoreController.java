package com.business.tpas.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.business.tpas.enums.RuleSettingCTypeEnum;
import com.business.tpas.model.*;
import com.business.tpas.service.AssessmentService;
import com.business.tpas.service.CourseScoreService;
import com.business.tpas.service.InternScoreService;
import com.business.tpas.service.PaperScoreService;
import com.github.pagehelper.PageInfo;
import com.management.common.constant.Constant;
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

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * @author dude
 * @version 1.0
 * @classname ScoreController
 * @description 绩效分数接口
 * @date 2021/4/3
 **/
@RestController
@RequestMapping("/score")
@Api(tags = {"绩效分数相关接口"})
public class ScoreController {

    @Autowired
    private CourseScoreService courseScoreService;

    @Autowired
    private InternScoreService internScoreService;

    @Autowired
    private PaperScoreService paperScoreService;

    @Autowired
    private AssessmentService assessmentService;

    @ApiOperation(value = "查询考核明细", notes = "查询考核明细")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{path}/queryScoreList")
    public BaseResponse<?> queryScoreList(@PathVariable("path") String path, @RequestBody ScoreSearchModel searchModel) {
        RuleSettingCTypeEnum cTypeEnum = RuleSettingCTypeEnum.getEnumByPath(path);
        if (cTypeEnum == null) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        PageInfo<?> data = null;
        switch (cTypeEnum) {
            case COURSE: {
                data = courseScoreService.queryScoreList(searchModel);
                break;
            }
            case PAPER: {
                data = paperScoreService.queryScoreList(searchModel);
                break;
            }
            case INTERN: {
                data = internScoreService.queryScoreList(searchModel);
                break;
            }
        }
        return new BaseResponse<>(data);
    }


    @ApiOperation(value = "查询考核汇总", notes = "查询考核汇总")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/queryScoreAnalysis")
    public BaseResponse<?> queryScoreAnalysis(@RequestBody ScoreSearchModel searchModel) {
        if ((StringUtils.isBlank(searchModel.getSchoolYear()) && searchModel.getSemester() != null) ||
                (StringUtils.isNotBlank(searchModel.getSchoolYear()) && searchModel.getSemester() == null)) {
            return new BaseResponse<>(ErrorCodeEnum.PARAM_IS_EMPTY);
        }
        ScoreAnalysisModel model = assessmentService.getScoreAnalysis(searchModel);
        return new BaseResponse<>(model);
    }


    @ApiOperation(value = "查询考核明细", notes = "查询考核明细")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{path}/export")
    public void exportScoreFile(@PathVariable("path") String path,
                                           HttpServletResponse response,
                                           @RequestBody ScoreSearchModel searchModel)
            throws IOException {
        RuleSettingCTypeEnum cTypeEnum = RuleSettingCTypeEnum.getEnumByPath(path);
        if (cTypeEnum == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG);
        }
        List<?> data = null;
        response.setContentType(Constant.EASYEXCEL_CONTENT_TYPE);
        response.setCharacterEncoding(Constant.EASYEXCEL_ENCODING);
        String fileName = URLEncoder.encode("绩效明细导出文件", Constant.EASYEXCEL_ENCODING);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        switch (cTypeEnum) {
            case COURSE: {
                data = courseScoreService.getScoreList(searchModel);
                EasyExcel.write(response.getOutputStream(), CourseScoreModel.class).sheet("授课绩效")
                        // 设置字段宽度为自动调整
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(data);
                break;
            }
            case PAPER: {
                data = paperScoreService.getScoreList(searchModel);
                EasyExcel.write(response.getOutputStream(), PaperScoreModel.class).sheet("授课绩效")
                        // 设置字段宽度为自动调整
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(data);
                break;
            }
            case INTERN: {
                data = internScoreService.getScoreList(searchModel);
                EasyExcel.write(response.getOutputStream(), InternScoreModel.class).sheet("授课绩效")
                        // 设置字段宽度为自动调整
                        .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(data);
                break;
            }
        }
    }


    @ApiOperation(value = "查询考核总分", notes = "查询考核总分")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"),
            @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/queryAssessList")
    public BaseResponse<?> queryAssessList(@RequestBody ScoreSearchModel searchModel) {
        PageInfo<?> data = assessmentService.queryAssessList(searchModel);
        return new BaseResponse<>(data);
    }

}
