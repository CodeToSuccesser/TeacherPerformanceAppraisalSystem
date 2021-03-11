package com.business.tpas.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.business.tpas.constant.Constant;
import com.business.tpas.enums.CourseCharacterEnum;
import com.business.tpas.enums.CourseTypeEnum;
import com.business.tpas.enums.IsBilingualEnum;
import com.business.tpas.enums.StudentTypeEnum;
import com.business.tpas.listener.CourseBaseUploadListener;
import com.business.tpas.model.CourseBaseModel;
import com.business.tpas.model.CourseInfoSearchModel;
import com.business.tpas.model.UploadResponseModel;
import com.business.tpas.service.CourseBaseService;
import com.github.pagehelper.PageInfo;
import com.management.common.config.FileConfig;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import com.management.common.utils.FileUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 管理员课程管理相关API
 * @date 2021/1/24
 **/
@RestController
@RequestMapping("/course/info")
@Api(tags = {"管理员课程管理相关接口"})
public class CourseInfoController {

    private static final Logger logger = LoggerFactory.getLogger(CourseInfoController.class);

    private static final String fileName = "课程信息模板.xls";

    @Autowired
    private CourseBaseService courseBaseService;

    @Autowired
    private FileConfig fileConfig;

    /**
     * @description 导入课程管理文件模板
     */
    @ApiOperation(value = "下载课程管理文件模板", notes = "下载课程管理文件模板")
    @GetMapping("/download/template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, Constant.EASYEXCEL_ENCODING));
            FileUtil.downloadFileByPath(response, fileConfig.SampleFileMenu + "/" + fileName,
                Constant.EASYEXCEL_CONTENT_TYPE, headerMap);
        } catch (IOException e) {
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
        }
    }

    /**
     * @description 导入课程信息文件
     * @param file
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "导入课程信息文件", notes = "导入课程信息文件")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = UploadResponseModel.class),
        @ApiResponse(code = 500, message = "系统错误")})
    public BaseResponse<?> uploadCourseInfo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.error("upload file is empty");
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
        }
        final CourseBaseUploadListener courseBaseUploadListener = new CourseBaseUploadListener(courseBaseService);
        try {
            EasyExcel.read(file.getInputStream(), CourseBaseModel.class, courseBaseUploadListener).sheet().doRead();
        } catch (IOException e) {
            logger.error("fail to upload course info file");
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
        }

        List<CourseBaseModel> rejectList = courseBaseUploadListener.getRejectInsertList();
        Integer rejectCount = rejectList.size();
        Integer successCount = courseBaseUploadListener.getSuccessCount();

        return new BaseResponse<>(
            new UploadResponseModel<>(rejectList, successCount, rejectCount, successCount + rejectCount));
    }

    @ApiOperation(value = "导出课程信息文件", notes = "导出课程信息文件")
    @PostMapping("/export")
    public void exportCourseInfo(HttpServletResponse response, @RequestBody CourseInfoSearchModel searchModel)
        throws IOException {
        List<CourseBaseModel> courseBaseModels = courseBaseService.getCourseBaseInfo(searchModel);

        response.setContentType(Constant.EASYEXCEL_CONTENT_TYPE);
        response.setCharacterEncoding(Constant.EASYEXCEL_ENCODING);
        String fileName = URLEncoder.encode("课程信息导出文件", Constant.EASYEXCEL_ENCODING);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), CourseBaseModel.class).sheet("课程信息")
            // 设置字段宽度为自动调整
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(courseBaseModels);

    }

    @ApiOperation(value = "根据条件查询课程信息", notes = "根据条件查询课程信息")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = CourseBaseModel.class),
        @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/getCourseInfo")
    public BaseResponse<?> getCourseInfo(@RequestBody CourseInfoSearchModel searchModel) {
        PageInfo<CourseBaseModel> courseBaseModelPage = courseBaseService.getCourseBaseInfoByPage(searchModel);
        return new BaseResponse<>(courseBaseModelPage);
    }

    @ApiOperation(value = "修改单条课程信息", notes = "修改单条课程信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{id}/modify")
    public BaseResponse<?> modifyCourseBaseInfo(@PathVariable("id") Long id, @RequestBody CourseBaseModel courseBaseModel) {
        validateCourseBaseModelParam(courseBaseModel);
        courseBaseModel.setId(id);
        courseBaseService.modifyCourseBaseInfo(courseBaseModel);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "删除课程信息", notes = "删除课程信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteCourseInfos")
    public BaseResponse<?> deleteCourseInfos(@RequestBody List<Long> ids) {
        courseBaseService.deleteCourseBaseInfos(ids);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "插入单条课程信息记录" ,notes = "插入单条课程信息记录")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/insert")
    public BaseResponse<?> insertCourseInfo(@RequestBody CourseBaseModel courseBaseModel) {
        validateCourseBaseModelParam(courseBaseModel);
        courseBaseService.insertCourseBaseInfo(courseBaseModel);
        return new BaseResponse<>();
    }


    /**
     * 校验请求参数
     *
     * @param courseBaseModel
     */
    private void validateCourseBaseModelParam(CourseBaseModel courseBaseModel) {
        if (StringUtils.isBlank(courseBaseModel.getCourseName())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "课程名为空");
        }
        if (StringUtils.isBlank(courseBaseModel.getCourseCode())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "课程编号为空");
        }
        if (courseBaseModel.getCourseType() != null && !CourseTypeEnum.isExistByCode(courseBaseModel.getCourseType())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "不存在该课程类型");
        }
        if (courseBaseModel.getCourseCharacter() != null && !CourseCharacterEnum
            .isExistByCode(courseBaseModel.getCourseCharacter())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "不存在该课程性质");
        }
        if (courseBaseModel.getIsBilingual() != null && !IsBilingualEnum
            .isExistByCode(courseBaseModel.getIsBilingual())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "是否双语类型错误");
        }
        if (courseBaseModel.getStudentType() != null && !StudentTypeEnum
            .isExistByCode(courseBaseModel.getStudentType())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_WRONG.code, "不存在该学生类型");
        }
    }

}