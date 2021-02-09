package com.management.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author dude
 * @version 1.0
 * @classname FileConfig
 * @description 文件配置：上传路径、图片文件格式化尺寸
 * @date 2021/2/8
 **/
@Configuration
public class FileConfig {

    @Value("${upload.image-size.height}")
    public int imageHeight = 10;

    @Value("${upload.image-size.width}")
    public int imageWeight = 10;

    @Value("${upload.portrait-max-length}")
    public int portraitMaxLength = 1;

    @Value("${upload.file-path}")
    public String baseFilePath = "";

    @Value("${upload.file-menu.sample}")
    public String SampleFileMenu = "";

    @Value("${upload.file-menu.image}")
    public String imageFileMenu = "";
}
