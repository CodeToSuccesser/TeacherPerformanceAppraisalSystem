package com.management.common.utils;

import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

import static com.management.common.config.GlobalConst.IMAGE_EXTENSIONS;
import static com.management.common.enums.ErrorCodeEnum.*;

/**
 * @author dude
 * @version 1.0
 * @classname FileUtil
 * @description 文件操作工具类
 * @date 2021/2/8
 **/
@Component
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    public String UploadPortrait(MultipartFile multipartFile, String basePath, String fileName, int imageWeight, int imageHeight) {
        if (multipartFile.isEmpty()) {
            throw new BusinessException(PARAM_IS_EMPTY);
        }
        try {
            // 获取文件名
            String originalName = multipartFile.getOriginalFilename();
            if (StringUtils.isBlank(originalName)) {
                throw new BusinessException(UPLOAD_FILE_WRONG);
            }
            // 获取文件类型
            String suffix = originalName.substring(originalName.lastIndexOf(".")).toLowerCase();
            if (!IMAGE_EXTENSIONS.contains(suffix)) {
                throw new BusinessException(UPLOAD_FILE_WRONG);
            }
            // 暂存文件
            File filePath = new File(System.getProperty("user.dir"), basePath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            File templateFile = new File(filePath, originalName);
            multipartFile.transferTo(templateFile);
            // 压缩
            BufferedImage sourceImage = ImageIO.read(templateFile);
            BufferedImage image = new BufferedImage(imageWeight, imageHeight, BufferedImage.TYPE_INT_RGB);
            Graphics graphics = image.getGraphics();
            graphics.drawImage(sourceImage, 0, 0, imageWeight, imageHeight, null);
            // 写入新文件
            File targetFile = new File(filePath, fileName + ".jpg");
            ImageIO.write(image, "jpg", targetFile);
            // 删除暂存文件
            templateFile.delete();
            return basePath + File.separator + targetFile.getName();
        } catch (IOException e) {
            logger.error("上传头像文件发生异常: ", e);
            throw new BusinessException(FILE_OPERATION_ERROR);
        }

    }

    /**
     * @description 文件下载
     */
    public static void downloadFile(HttpServletResponse response, File file) throws IOException {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
        byte[] buff = new byte[1024];
        OutputStream os = response.getOutputStream();
        int i = 0;
        while ((i = bufferedInputStream.read(buff)) != -1) {
            os.write(buff, 0, i);
            os.flush();
        }
    }

    public static void downloadFileByPath(HttpServletResponse response, String path, String contentType,
        Map<String, String> headerMap) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            throw new BusinessException(ErrorCodeEnum.OBJECT_NOT_FOUND.code, "下载文件不存在");
        }

        response.setContentType(contentType);

        if (!headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                response.addHeader(entry.getKey(), entry.getValue());
            }
        }

        downloadFile(response, file);
    }
}
