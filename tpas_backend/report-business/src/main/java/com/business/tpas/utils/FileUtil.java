package com.business.tpas.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
}
