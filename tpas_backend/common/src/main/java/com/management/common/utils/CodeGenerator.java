package com.management.common.utils;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.apache.commons.lang3.StringUtils;

import java.util.ResourceBundle;
import java.util.Scanner;

/**
 * @author dude
 * @description MybatisPlus的代码生成器
 * @date 2020/7/16
 **/
public class CodeGenerator {

    private static ResourceBundle resourceBundle;

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

    public static void generator() {

        //1. 全局配置
        GlobalConfig config = new GlobalConfig();
        // 作者
        config.setAuthor(resourceBundle.getString("author"))
            // 生成路径
            .setOutputDir(resourceBundle.getString("outputDir"))
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
            .setDriverName(resourceBundle.getString("driver"))
            //设置连接路径
            .setUrl(resourceBundle.getString("url"))
            //设置用户名
            .setUsername(resourceBundle.getString("username"))
            //设置密码
            .setPassword(resourceBundle.getString("password"));

        //4. 包名策略配置
        PackageConfig pkConfig = new PackageConfig();
        //顶级包结构
        pkConfig.setParent(resourceBundle.getString("parentPackage"))
            //数据访问层
            .setMapper(resourceBundle.getString("dao"))
            //业务逻辑层
            .setService(resourceBundle.getString("service"))
            //控制器
            .setController(resourceBundle.getString("controller"))
            //实体类
            .setEntity(resourceBundle.getString("entity"))
            //mapper映射文件
            .setXml(resourceBundle.getString("mapperxml"))
            //模块名;
            .setModuleName(scanner("模块名"));

        //3. 策略配置
        StrategyConfig stConfig = new StrategyConfig();
        //全局大写命名
        stConfig.setCapitalMode(true)
            // 数据库表映射到实体的命名策略
            .setNaming(NamingStrategy.underline_to_camel)

            .setColumnNaming(NamingStrategy.underline_to_camel)
            //表前缀
            .setInclude(scanner("请输入表名(多个以英文逗号隔开):").split(",")).setTablePrefix(pkConfig.getModuleName() + "_")
            // 生成的表
            .setControllerMappingHyphenStyle(true);

        //5. 整合配置
        AutoGenerator ag = new AutoGenerator();
        ag.setGlobalConfig(config).setDataSource(dsConfig).setStrategy(stConfig).setPackageInfo(pkConfig);
        //6. 执行
        ag.execute();
    }

    public static void main(String args[]) {

        resourceBundle = ResourceBundle.getBundle("mybatisPlus");

        generator();

    }

}