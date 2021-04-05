package com.business.tpas.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.business.tpas.listener.MajorUploadListener;
import com.business.tpas.model.MajorModel;
import com.business.tpas.model.MajorSearchModel;
import com.business.tpas.service.MajorService;
import com.github.pagehelper.PageInfo;
import com.management.common.config.FileConfig;
import com.management.common.constant.Constant;
import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import com.management.common.model.BaseResponse;
import com.management.common.model.UploadResponseModel;
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
 * @description 专业信息基本接口
 **/

@RestController
@RequestMapping("/major/info")
@Api(tags = {"专业信息基本接口"})
public class MajorController {

    private static final Logger logger = LoggerFactory.getLogger(MajorController.class);

    @Autowired
    private MajorService majorService;

    @Autowired
    private FileConfig fileConfig;

    private static final String fileName = "专业信息模板.xls";

    /**
     * 下载专业信息模板
     * @param response
     */
    @ApiOperation(value = "下载专业信息模板", notes = "下载专业信息模板")
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

    @ApiOperation(value = "导入专业信息文件", notes = "导入专业信息文件")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok"),
        @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/import")
    public BaseResponse<?> uploadMajor(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            logger.error("upload file is empty");
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "上传文件为空");
        }
        final MajorUploadListener majorUploadListener = new MajorUploadListener(majorService);
        try {
            EasyExcel.read(file.getInputStream(), MajorModel.class, majorUploadListener).sheet().doRead();
        } catch (IOException e) {
            logger.error("fail to upload major file");
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg, e);
        }

        Integer successCount = majorUploadListener.getSuccessCount();
        List<MajorModel> majorModels = majorUploadListener.getRejectInsertList();
        Integer failCount = majorModels.size();

        return new BaseResponse<>(
            new UploadResponseModel<MajorModel>(majorModels, successCount, failCount, successCount + failCount));
    }

    @ApiOperation(value = "导出专业文件", notes = "导出专业文件")
    @PostMapping("/export")
    public void exportMajor(HttpServletResponse response, @RequestBody MajorSearchModel searchModel)
        throws IOException {
        List<MajorModel> majorModels = majorService.getMajor(searchModel);

        response.setContentType(Constant.EASYEXCEL_CONTENT_TYPE);
        response.setCharacterEncoding(Constant.EASYEXCEL_ENCODING);
        String fileName = URLEncoder.encode("专业信息导出文件", Constant.EASYEXCEL_ENCODING);
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), MajorModel.class).sheet("专业信息")
            // 设置字段宽度为自动调整
            .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy()).doWrite(majorModels);
    }


    @ApiOperation(value = "根据条件查询专业信息", notes = "根据条件查询专业信息")
    @ApiResponses(value = { @ApiResponse(code = 0, message = "ok", response = MajorModel .class),
        @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/getMajor")
    public BaseResponse<?> getMajor(@RequestBody MajorSearchModel searchModel) {
        PageInfo<MajorModel> majorByPage = majorService.getMajorByPage(searchModel);
        return new BaseResponse<>(majorByPage);
    }

    @ApiOperation(value = "修改单条专业信息", notes = "修改单条专业信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/{id}/modify")
    public BaseResponse<?> modifyMajor(@PathVariable("id") Long id, @RequestBody MajorModel majorModel) {
        validateModifyInsertMajorParam(majorModel);
        majorModel.setId(id);
        majorService.modifyMajor(majorModel);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "删除专业信息", notes = "删除专业信息")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok"), @ApiResponse(code = 500, message = "系统错误")})
    @PostMapping("/deleteMajor")
    public BaseResponse<?> deleteMajor(@RequestBody List<Long> ids) {
        majorService.deleteMajor(ids);
        return new BaseResponse<>();
    }

    @ApiOperation(value = "插入单条专业信息记录" ,notes = "插入单条专业信息记录")
    @ApiResponses(value = {@ApiResponse(code = 0, message = "ok")})
    @PostMapping("/insert")
    public BaseResponse<?> insertMajor(@RequestBody MajorModel majorModel) {
        validateModifyInsertMajorParam(majorModel);
        majorService.insertMajor(majorModel);
        return new BaseResponse<>();
    }

    private void validateModifyInsertMajorParam(MajorModel majorModel) {
        if (StringUtils.isBlank(majorModel.getMajorName())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "专业编码为空");
        }
        if (StringUtils.isBlank(majorModel.getMajorCode())) {
            throw new BusinessException(ErrorCodeEnum.PARAM_IS_EMPTY.code, "专业名称为空");
        }
    }

}
