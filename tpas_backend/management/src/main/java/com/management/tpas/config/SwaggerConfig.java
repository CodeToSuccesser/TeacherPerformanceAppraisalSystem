package com.management.tpas.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author dude
 * @version 1.0
 * @classname SwaggerConfig
 * @description Swagger文档配置
 * @date 2020/9/4
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${swagger.enable}")
    private final Boolean swaggerEnable = true;

    @Bean
    public Docket createManagementApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .enable(swaggerEnable)
                .pathMapping("/user")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.management.tpas.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(new ApiInfoBuilder()
                .title("TPAS后台登录权限管理-API文档")
                .build());
    }
}
