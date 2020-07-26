package com.management.common.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author dude
 * @description MybatisPlus的代码生成器
 * @date 2020/7/16
 **/
public class CodeGenerator {
    //作者名称
    private static String author = "";
    //生成的位置
    private static String outputDir = "/Users/lengwen/Desktop/erp";
    //驱动，注意版本
    private static String driver = "com.mysql.cj.jdbc.Driver";
    //连接路径,注意修改数据库名称
    private static String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8" +
            "&serverTimezone=UTC";
    //数据库用户名
    private static String username = "root";
    //数据库密码
    private static String password = "root";
    //数据库表的前缀，如t_user
    private static String tablePrefix = "t_";
    //顶级包结构
    private static String parentPackage = "com.management";
    //数据访问层包名称
    private static String dao = "dao";

    //业务逻辑层包名称
    private static String service = "service";
    //实体层包名称
    private static String entity = "entity";
    //控制器层包名称
    private static String controller = "controller";
    //mapper映射文件包名称
    private static String mapperxml = "mapper";
    //模块名
    private static String moduleName = "sys";

    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {

        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
// 作者
        config.setAuthor(author)
// 生成路径
                .setOutputDir(outputDir)
// 文件覆盖
                .setFileOverride(true)
// 主键策略
                .setIdType(IdType.AUTO)
                // 设置生成的service接口的名字的首字母是否为I，加%s则不生成I
                .setServiceName("%sService")
                //映射文件中是否生成ResultMap配置
                .setBaseResultMap(true)
                //生成通用sql字段
                .setBaseColumnList(true);

        //2. 数据源配置
        DataSourceConfig dsConfig = new DataSourceConfig();
        // 设置数据库类型
        dsConfig.setDbType(DbType.MYSQL)
                //设置驱动
                .setDriverName(driver)
                //设置连接路径
                .setUrl(url)
//设置用户名
                .setUsername(username)
//设置密码
                .setPassword(password);

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
//顶级包结构
        pkConfig.setParent(parentPackage)
                //数据访问层
                .setMapper(dao)
//业务逻辑层
                .setService(service)
//控制器
                .setController(controller)
                //实体类
                .setEntity(entity)
//mapper映射文件
                .setXml(mapperxml)
//模块名;
                .setModuleName(scanner("模块名"));

        //3. 策略配置
        StrategyConfig stConfig = new StrategyConfig();
//全局大写命名
        stConfig.setCapitalMode(true)
// 数据库表映射到实体的命名策略
                .setNaming(NamingStrategy.underline_to_camel)

                .setColumnNaming(NamingStrategy.underline_to_camel)
                //.setTablePrefix(tablePrefix) //表前缀
                .setInclude(scanner("请输入表名(多个以英文逗号隔开):").split(","))
                .setTablePrefix(pkConfig.getModuleName() + "_")
                //.setInclude(tables) // 生成的表
                .setControllerMappingHyphenStyle(true);


        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config)
                .setDataSource(dsConfig)
                .setStrategy(stConfig)
                .setPackageInfo(pkConfig);
        //6. 执行
        ag.execute();

    }

}