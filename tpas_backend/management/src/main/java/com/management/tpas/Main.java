package com.management.tpas;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author dude
 * @description springBoot驱动
 * @date 2020/7/16
 **/
@EnableAsync @EnableConfigurationProperties
@SpringBootApplication(scanBasePackages = {"com.management"}, exclude = {DataSourceAutoConfiguration.class})
@ConfigurationPropertiesScan(basePackages = {"com.management"}) @MapperScan(basePackages = {"com.management.tpas.dao","com.business.tpas.dao"})
@ServletComponentScan public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

}
