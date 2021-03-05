package com.management.common.config;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

/**
 * @author Pietra
 * @version 1.0
 * @classname SessionFactoryConfig
 * @description TODO
 * @date 2020/8/2
 **/
@Configuration
public class SessionFactoryConfig {

    @Value("${mybatis-plus.mapper-locations}")
    private String mapperPath;

    @Value("${mybatis-plus.typeAliasesPackage}")
    private String entityPackage;

    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;

    @Bean
    public MybatisSqlSessionFactoryBean creatSqlSessionFactory() throws IOException {
        MybatisSqlSessionFactoryBean mybatisSqlSessionFactoryBean = new MybatisSqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        mybatisSqlSessionFactoryBean.setMapperLocations(resolver.getResources(mapperPath));
        mybatisSqlSessionFactoryBean.setDataSource(dataSource);
        mybatisSqlSessionFactoryBean.setTypeAliasesPackage(entityPackage);
        return mybatisSqlSessionFactoryBean;
    }
}
