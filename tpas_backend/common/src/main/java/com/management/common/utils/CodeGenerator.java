package com.management.common.utils;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Scanner;

/**
 * @author dude
 * @description MybatisPlus的代码生成器
 * @date 2020/7/16
 **/
public class CodeGenerator {
    private static String author = "";
    private static String outputDir = "/Users/lengwen/Desktop/erp";
    private static String driver = "com.mysql.cj.jdbc.Driver";
    private static String url = "jdbc:mysql://localhost:3306/erp?useUnicode=true&characterEncoding=UTF-8" +
            "&serverTimezone=UTC";
    private static String username = "root";
    private static String password = "root";
    private static String tablePrefix = "t_";
    private static String parentPackage = "com.management";
    private static String dao = "dao";
    private static String service = "service";
    private static String entity = "entity";
    private static String controller = "controller";
    private static String mapperxml = "mapper";
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

    public void generator() throws IOException {
         Properties properties = new Properties();
         // 使用ClassLoader加载properties配置文件生成对应的输入流
           InputStream in = CodeGenerator.class.getClassLoader().getResourceAsStream("config.properties");
           // 使用properties对象加载输入流
           properties.load(in);

        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
// 作者
        config.setAuthor(properties.getProperty("author"))
// 生成路径
                .setOutputDir(properties.getProperty("outputDir"))
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
                .setDriverName(properties.getProperty("driver"))
                //设置连接路径
                .setUrl(properties.getProperty("url"))
//设置用户名
                .setUsername(properties.getProperty("username"))
//设置密码
                .setPassword(properties.getProperty("password"));

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
//顶级包结构
        pkConfig.setParent(properties.getProperty("parentPackage"))
                //数据访问层
                .setMapper(properties.getProperty("dao"))
//业务逻辑层
                .setService(properties.getProperty("service"))
//控制器
                .setController(properties.getProperty("controller"))
                //实体类
                .setEntity(properties.getProperty("entity"))
//mapper映射文件
                .setXml(properties.getProperty("mapperxml"))
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