package com.management.tpas.intercepter;

import com.management.common.config.FileConfig;
import com.management.common.config.SwaggerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

/**
 * @author dude
 * @version 1.0
 * @classname InterceptorConfigurer
 * @description 拦截器配置适配器
 * @date 2020/8/11
 **/
@Configuration
public class InterceptorConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Autowired
    private ParamInterceptor paramInterceptor;

    @Autowired
    private FileConfig fileConfig;

    @Autowired
    private SwaggerConfig swaggerConfig;

    /**
     * @description 拦截器配置
     * @param registry 拦截配置
     * @return void
     *
     * @author dude
     * @date 2020/8/11
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        int order = 1;

        // 登录拦截
        registry.addInterceptor(loginInterceptor)
                .order(order++)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/swagger/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/error");

        // 参数处理拦截
        registry.addInterceptor(paramInterceptor)
                .order(order)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/swagger-resources/**")
                .excludePathPatterns("/swagger/**")
                .excludePathPatterns("/swagger-ui.html")
                .excludePathPatterns("/error");

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // swagger静态映射
        if (swaggerConfig.getSwaggerEnable()) {
            registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        }
        // 图片访问
        File filePath = new File(System.getProperty("user.dir").concat(fileConfig.baseFilePath), fileConfig.imageFileMenu);
        if (filePath.exists() && filePath.isDirectory()) {
            String pathPatterns = fileConfig.baseFilePath.concat(fileConfig.imageFileMenu).concat(File.separator).concat("**");
            String localPath = "file://".concat(System.getProperty("user.dir"))
                    .concat(fileConfig.baseFilePath)
                    .concat(fileConfig.imageFileMenu)
                    .concat(File.separator);
            registry.addResourceHandler(pathPatterns)
                    .addResourceLocations(localPath);
        }
    }

}
