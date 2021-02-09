package com.business.tpas.utils;

import com.management.common.enums.ErrorCodeEnum;
import com.management.common.exception.BusinessException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Map;

/**
 * @classname FileUtil
 * @description 文件操作工具类
 **/
public class FileUtil {

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

    public static void downloadClassPathFile(HttpServletResponse response, String path, String contentType,
        Map<String, String> headerMap) throws IOException {
        Resource resource = new ClassPathResource(path);
        File file = resource.getFile();
        if (!file.exists()) {
            throw new BusinessException(ErrorCodeEnum.EXCEPTION.code, ErrorCodeEnum.EXCEPTION.msg);
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
