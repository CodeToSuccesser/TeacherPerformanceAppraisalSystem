package com.management.tpas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author dude
 * @description springBoot驱动
 * @date 2020/7/16
 **/
@EnableAsync @EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.management"}, exclude = {DataSourceAutoConfiguration.class})
@ConfigurationPropertiesScan(basePackages = {"com.management"}) @MapperScan(basePackages = {"com.management.**.dao"})
@ServletComponentScan public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
