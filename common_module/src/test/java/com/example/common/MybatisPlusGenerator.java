package com.example.common;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.builder.ConfigBuilder;
import com.baomidou.mybatisplus.generator.config.converts.DmTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.FileType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Description mybatis自动生成
 *
 * @author 杨寒冰
 * @version 1.0
 * @date 2020/7/20 16:53
 **/
public class MybatisPlusGenerator {

    /**
     * 生成规则过滤前缀
     */
    private static String excludePrefix = "";

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

        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        String projectPath = System.getProperty("user.dir") + "/" + scanner("maven Modules名称(示例：user_module)") + "/";

        mpg.setGlobalConfig(getGlobalConfig(projectPath));

        mpg.setDataSource(getDataSourceConfig());

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent(scanner("包名(示例：com.example.test6.user_module.login)"));
        pc.setEntity("model");
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mybatis/custom_mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        String mapperPath = scanner("mapper 生成路径(示例：user_module/login)");

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {

                String entityName = tableInfo.getEntityName();

                if (entityName.startsWith(excludePrefix)) {
                    entityName = entityName.replaceFirst(excludePrefix, "");

                    tableInfo.setEntityName(entityName);
                    tableInfo.setMapperName(tableInfo.getMapperName().replaceFirst(excludePrefix, ""));
                }

                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/META-INF/mapper/" + mapperPath
                        + "/" + entityName + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                File file = new File(filePath);

                boolean exist = file.exists();

                if (!exist) {
                    file.getParentFile().mkdirs();
                }

                // 判断自定义文件夹是否需要创建,mapper和mapper.xml不覆盖
                if (fileType == FileType.OTHER || fileType == FileType.MAPPER) {
                    // 已经生成 mapper 文件判断存在，不想重新生成返回 false
                    return !new File(filePath).exists();
                }
                // 允许生成模板文件
                return true;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        templateConfig.setService(null);
        templateConfig.setController(null);
        templateConfig.setServiceImpl(null);
        templateConfig.setMapper("/templates/mybatis/custom_mapper.java");
        templateConfig.setEntity("/templates/mybatis/custom_entity.java");

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);


        mpg.setStrategy(getStrategyConfig());
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    private static GlobalConfig getGlobalConfig(String projectPath) {
        // 全局配置
        GlobalConfig gc = new GlobalConfig();

        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("duyu");
        gc.setOpen(false);
        //实体属性 Swagger2 注解
        gc.setSwagger2(true);
        //是否开启覆盖
        gc.setFileOverride(true);

        gc.setBaseResultMap(true);

        gc.setBaseColumnList(true);

        return gc;
    }

    /**
     * 数据源配置
     *
     * @return DataSourceConfig
     */
    private static DataSourceConfig getDataSourceConfig() {

        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://192.168.124.5:3306/peipei_test?serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("peipei_test");
        dsc.setPassword("123456");
        dsc.setTypeConvert(new DmTypeConvert() {
            @Override
            public DbColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {

                //tinyint长度为1以上的转换成BYTE
                if (fieldType.toLowerCase().contains("tinyint(1)")) {
                    return DbColumnType.BOOLEAN;
                } else if (fieldType.toLowerCase().contains("tinyint")) {
                    return DbColumnType.BYTE;
                }

                return (DbColumnType) super.processTypeConvert(globalConfig, fieldType);
            }

        });

        return dsc;
    }

    /**
     * 策略配置
     *
     * @return StrategyConfig
     */
    private static StrategyConfig getStrategyConfig() {

        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setEntityLombokModel(true);
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        List<TableFill> tableFillList = new ArrayList<>();
        TableFill createTime = new TableFill("create_time", FieldFill.INSERT);
        TableFill updateTime = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        TableFill createUser = new TableFill("create_user", FieldFill.INSERT);
        TableFill updateUser = new TableFill("update_user", FieldFill.INSERT_UPDATE);
        tableFillList.add(createTime);
        tableFillList.add(updateTime);
        tableFillList.add(createUser);
        tableFillList.add(updateUser);
        strategy.setTableFillList(tableFillList);

        return strategy;
    }

}
