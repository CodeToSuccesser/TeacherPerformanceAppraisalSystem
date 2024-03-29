package com.business.tpas.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.management.common.constant.Constant;
import com.business.tpas.listener.CourseHoursUploadListener;
import com.business.tpas.model.*;
import com.business.tpas.service.CourseBaseService;
import com.business.tpas.service.CourseHoursModifyRecordService;
import com.business.tpas.service.CourseHoursService;
import com.github.pagehelper.PageInfo;
import com.management.common.config.FileConfig;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import com.management.common.model.UploadResponseModel;
import com.management.common.utils.FileUtil;
import com.management.tpas.service.UserMsgService;
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
 * @description 课时记录相关接口
 **/

@RestController
@RequestMapping("/course/hours")
@Api(tags = {"课时记录相关接口"})
public class CourseHoursController {

    private static final Logger logger = LoggerFactory.getLogger(CourseHoursController.class);

    @Autowired
    private CourseHoursService courseHoursService;

    @Autowired
    private CourseBaseService courseBaseService;

    @Autowired
    private UserMsgService userMsgService;

    @Autowired
    private CourseHoursModifyRecordService courseHoursModifyRecordService;

    @Autowired
    private FileConfig fileConfig;

    private static final String fileName = "课时信息模板.xls";

    /**
     * 下载课时信息模板
     * @param response
     */
    @ApiOperation(value = "下载课时导入模板", notes = "下载课时导入模板")
    @GetMapping("/download/template")
    public void downloadTemplate(HttpServletResponse response) {
        try {
            Map<String, String> headerMap = new HashMap<>();
            headerMap.put("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode(fileName, Constant.EASYEXCEL_ENCODING));
            FileUtil.downloadFileByPath(response, fileConfig.SampleFileMenu + "/" + fileName,
                Constant.EASYEXCEL_CONTENT_TYPE, headerMap);
        } catch (IOException e) {
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg, e);
        }
    }


    @ApiOperation(value = "导入课时信息文件", notes = "导入课时信息文件")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok"),
        @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/import")
    public BaseResponse<?> uploadCourseInfo(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.error("upload file is empty");
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "上传文件为空");
        }
        final CourseHoursUploadListener courseHoursUploadListener =
            new CourseHoursUploadListener(courseHoursService, courseBaseService, userMsgService);
        try {
            EasyExcel.read(file.getInputStream(), CourseHoursModel.class, courseHoursUploadListener).sheet().doRead();
        } catch (IOException e) {
            logger.error("fail to upload course hours file");
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg, e);
        }

        Integer successCount = courseHoursUploadListener.getSuccessCount();
        List<CourseHoursModel> courseHoursModels = courseHoursUploadListener.getRejectInsertList();
        Integer failCount = courseHoursModels.size();

        return new BaseResponse<>(new UploadResponseModel<CourseHoursModel>(courseHoursModels, successCount, failCount,
            successCount + failCount));
    }

    @ApiOperation(value = "导出课程信息文件", notes = "导出课程信息文件")
    @PostMapping("/export")
    public void exportCourseInfo(HttpServletResponse response, @RequestBody CourseHoursSearchModel searchModel)
        throws IOException {
        List<CourseHoursModel> courseHoursModelList = courseHoursService.getCourseHours(searchModel);

        response.setContentType(Constant.EASYEXCEL_CONTENT_TYPE);
        response.setCharacterEncoding(Constant.EASYEXCEL_ENCODING);
        String fileName = URLEncoder.encode("课时信息导出文件", Constant.EASYEXCEL_ENCODING);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), CourseHoursModel.class).sheet("课时信息")
            // 设置字段宽度为自动调整
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(courseHoursModelList);
    }

    @ApiOperation(value = "根据条件查询课时信息", notes = "根据条件查询课时信息")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = CourseHoursModel .class),
        @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/getCourseHours")
    public BaseResponse<?> getCourseHours(@RequestBody CourseHoursSearchModel searchModel) {
        PageInfo<CourseHoursModel> courseHoursByPage = courseHoursService.getCourseHoursByPage(searchModel);
        return new BaseResponse<>(courseHoursByPage);
    }

    @ApiOperation(value = "修改单条课时信息", notes = "修改单条课时信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{id}/modify")
    public BaseResponse<?> modifyCourseHours(@PathVariable("id") Long id, @RequestBody CourseHoursModel courseHoursModel) {
        validateModifyInsertCourseHoursParam(courseHoursModel);
        courseHoursModel.setId(id);
        courseHoursService.modifyCourseHours(courseHoursModel);
        return new BaseResponse<>();
    }


    @ApiOperation(value = "删除课时信息", notes = "删除课时信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteCourseHours")
    public BaseResponse<?> deleteCourseHours(@RequestBody List<Long> ids) {
        courseHoursService.deleteCourseHours(ids);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "插入单条课时信息记录" ,notes = "插入单条课时信息记录")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/insert")
    public BaseResponse<?> insertCourseHours(@RequestBody CourseHoursModel courseHoursModel) {
        validateModifyInsertCourseHoursParam(courseHoursModel);
        courseHoursService.insertCourseHours(courseHoursModel);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "课时修改审批",notes = "课时修改审批")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/{id}/audit")
    public BaseResponse<?> auditCourseHoursModify(@PathVariable Long id, @RequestParam("result") Boolean result){
        if (result == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "审批结果参数为空");
        }
        courseHoursModifyRecordService.auditCourseHoursModify(id, result);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "获取修改申请信息记录", notes = "获取修改申请信息记录")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = CourseHoursModifyRecordModel.class),
        @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/getModifyRecord")
    public BaseResponse<?> getModifyRecord(@RequestBody CourseHoursModifyRecordSearchModel searchModel) {
        return new BaseResponse<>(courseHoursModifyRecordService.getModifyRecord(searchModel));
    }


    private void validateModifyInsertCourseHoursParam(CourseHoursModel courseHoursModel) {
        if (StringUtils.isBlank(courseHoursModel.getTeacherCode())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "教师编号为空");
        }
        if (StringUtils.isBlank(courseHoursModel.getCourseCode())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "课程编号为空");
        }
        if (StringUtils.isBlank(courseHoursModel.getCourseName())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "课程名为空");
        }
        if (courseHoursModel.getSemester() == null) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "学期参数为空");
        }
        if (StringUtils.isBlank(courseHoursModel.getSchoolYear())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "学年参数为空");
        }
    }


}
