package com.management.tpas.intercepter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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

    /**
     * @description 拦截器配置
     * @param registry
     * @return void
     *
     * @author dude
     * @date 2020/8/11
     **/
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截
        registry.addInterceptor(loginInterceptor)
                .order(1)
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login");
    }
}
