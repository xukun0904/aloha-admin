package fun.xukun.model.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * 日期:2020/3/8
 *
 * @author xukun
 * @version 1.00
 */
public class CodeGenerator {

    /**
     * 数据库IP
     */
    private static final String IP = "192.168.184.128";

    /**
     * 数据库端口
     */
    private static final String PORT = "3306";

    /**
     * 数据库名
     */
    private static final String DATABASE = "aloha_admin";

    /**
     * 要生成位置的项目名
     */
    private static final String PROJECT_NAME = "model";

    /**
     * 父包名
     */
    private static final String PARENT_PACKAGE_NAME = "fun.xukun.model";

    /**
     * 模块名
     */
    private static final String MODULE = "system";

    /**
     * 实体类包名
     */
    private static final String ENTITY_PACKAGE_NAME = "domain." + MODULE;

    /**
     * 业务层包名
     */
    private static final String SERVICE_PACKAGE_NAME = "manager." + MODULE;
    private static final String SERVICE_IMPL_PACKAGE_NAME = "manager." + MODULE + ".impl";

    /**
     * 持久层包名
     */
    private static final String MAPPER_PACKAGE_NAME = "mapper." + MODULE;
    private static final String MAPPER_XML_PACKAGE_NAME = "mapper/" + MODULE;

    /**
     * 数据库用户名
     */
    private static final String DATABASE_USERNAME = "root";

    /**
     * 数据库密码
     */
    private static final String DATABASE_PASSWORD = "root";

    /**
     * 驱动名
     */
    private static final String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";

    /**
     * 表前缀
     */
    // private static final String TABLE_PREFIX = "b_";

    public static void main(String[] args) {
        // "menu","role","role_menu","department","user"
        executeCodeGenerator("user_role");
    }

    /**
     * 生成代码
     *
     * @param tableNames 需要生成的表名
     */
    private static void executeCodeGenerator(String... tableNames) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = getGlobalConfig();
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = getDataSourceConfig();
        mpg.setDataSource(dsc);
        // 包配置
        PackageConfig pc = getPackageConfig();
        mpg.setPackageInfo(pc);
        // 自定义配置
        InjectionConfig cfg = getInjectionConfig();
        mpg.setCfg(cfg);
        // 配置模板
        TemplateConfig templateConfig = getTemplateConfig();
        mpg.setTemplate(templateConfig);
        // 策略配置
        StrategyConfig strategy = getStrategyConfig(tableNames);
        mpg.setStrategy(strategy);
        // 模板引擎
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        // 执行
        mpg.execute();
    }

    /**
     * 获取数据库表配置
     *
     * @param tableNames 表名
     * @return 数据库表配置
     */
    private static StrategyConfig getStrategyConfig(String... tableNames) {
        StrategyConfig strategy = new StrategyConfig();
        // 是否跳过视图
        strategy.setSkipView(true);
        // 数据库表映射到实体的命名策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 数据库表字段映射到实体的命名策略, 未指定按照 naming 执行
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        // 表前缀
        // strategy.setTablePrefix(TABLE_PREFIX);
        // 字段前缀
        // strategy.setFieldPrefix(null);
        // 自定义继承的Entity类全称，带包名
        // strategy.setSuperEntityClass(BaseDO.class);
        // 自定义基础的Entity类，公共字段
        // 写于父类中的公共字段
        // strategy.setSuperEntityColumns("id", "create_time", "update_time");
        // strategy.setSuperMapperClass(null);
        // strategy.setSuperServiceClass(null);
        // strategy.setSuperServiceImplClass(null);
        // 公共父类
        // strategy.setSuperControllerClass("你自己的父类控制器,没有就不用设置!");
        // 需要包含的表名，允许正则表达式（与exclude二选一配置）
        strategy.setInclude(tableNames);
        // 需要排除的表名，允许正则表达式
        // strategy.setExclude(null);
        // 自3.3.0起，模糊匹配表名
        // strategy.setLikeTable(null);
        // strategy.setNotLikeTable(null);
        // 【实体】是否生成字段常量（默认 false）
        strategy.setEntityColumnConstant(false);
        // 【实体】是否为构建者模型（默认 false）
        // strategy.setEntityBuilderModel(false);
        // 【实体】是否为lombok模型（默认 false）
        strategy.setEntityLombokModel(true);
        // Boolean类型字段是否移除is前缀（默认 false）
        strategy.setEntityBooleanColumnRemoveIsPrefix(true);
        // 生成 @RestController 控制器
        strategy.setRestControllerStyle(false);
        // 驼峰转连字符
        strategy.setControllerMappingHyphenStyle(true);
        // 是否生成实体时，生成字段注解
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 乐观锁属性名称
        // strategy.setVersionFieldName(null);
        // 逻辑删除属性名称
        // strategy.setLogicDeleteFieldName(null);
        // 表填充字段
        // strategy.setTableFillList(null);
        return strategy;
    }

    /**
     * 获取自定义配置
     *
     * @return 自定义配置
     */
    private static InjectionConfig getInjectionConfig() {
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // 注入自定义 Map 对象
                // to do nothing
            }
        };
        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";
        String projectPath = System.getProperty("user.dir") + "/" + PROJECT_NAME;
        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义输出文件,自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/" + MAPPER_XML_PACKAGE_NAME + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        // 自定义判断是否创建文件
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        return cfg;
    }

    /**
     * 获取模板配置
     *
     * @return 模板配置
     */
    private static TemplateConfig getTemplateConfig() {
        // 配置自定义输出模板
        TemplateConfig templateConfig = new TemplateConfig();
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity2.java");
        // Kotin 实体类模板
        // templateConfig.setEntityKt(null);
        // Service 类模板
        // templateConfig.setService(null);
        // mapper 模板
        // templateConfig.setMapper(null);
        // Service impl 实现类模板
        // templateConfig.setServiceImpl(null);
        // controller 控制器模板
        templateConfig.setController(null);
        // mapper xml 模板
        templateConfig.setXml(null);
        return templateConfig;
    }

    /**
     * 获取包配置
     *
     * @return 包配置
     */
    private static PackageConfig getPackageConfig() {
        PackageConfig pc = new PackageConfig();
        // 父包模块名
        // pc.setModuleName(null);
        // 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
        pc.setParent(PARENT_PACKAGE_NAME);
        pc.setEntity(ENTITY_PACKAGE_NAME);
        pc.setService(SERVICE_PACKAGE_NAME);
        pc.setServiceImpl(SERVICE_IMPL_PACKAGE_NAME);
        pc.setMapper(MAPPER_PACKAGE_NAME);
        // pc.setXml("resource.mapper");
        // pc.setController(null);
        // pc.setPathInfo(null);
        return pc;
    }

    /**
     * 获取数据源配置
     *
     * @return 数据源配置
     */
    private static DataSourceConfig getDataSourceConfig() {
        DataSourceConfig dsc = new DataSourceConfig();
        // 数据库信息查询类
        // dsc.setDbQuery();
        // 数据库类型
        dsc.setDbType(DbType.MYSQL);
        // 类型转换
        dsc.setTypeConvert(new MySqlTypeConvert() {
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                String t = fieldType.toLowerCase();
                if (t.contains("tinyint(1)")) {
                    return DbColumnType.INTEGER;
                }
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });
        dsc.setUrl("jdbc:mysql://" + IP + ":" + PORT + "/" + DATABASE + "?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        // dsc.setSchemaName("public");
        dsc.setDriverName(DRIVER_NAME);
        dsc.setUsername(DATABASE_USERNAME);
        dsc.setPassword(DATABASE_PASSWORD);
        return dsc;
    }

    /**
     * 获取全局配置
     *
     * @return 全局配置
     */
    private static GlobalConfig getGlobalConfig() {
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir") + "/" + PROJECT_NAME;
        // 生成文件的输出目录
        gc.setOutputDir(projectPath + "/src/main/java");
        // 是否覆盖已有文件
        gc.setFileOverride(true);
        // 是否打开输出目录
        gc.setOpen(false);
        // 是否在xml中添加二级缓存配置
        gc.setEnableCache(false);
        // 开发人员
        gc.setAuthor("xukun");
        // 开启 Kotlin 模式
        gc.setKotlin(false);
        // 开启 swagger2 模式
        gc.setSwagger2(true);
        // 开启 ActiveRecord 模式
        gc.setActiveRecord(false);
        // 开启 BaseResultMap
        gc.setBaseResultMap(true);
        // 开启 baseColumnList
        gc.setBaseColumnList(false);
        // 时间策略
        // gc.setDateType(DateType.ONLY_DATE);
        // 实体命名方式
        gc.setEntityName("%s");
        // mapper 命名方式
        gc.setMapperName("%sMapper");
        // Mapper xml 命名方式
        gc.setXmlName("%sMapper");
        // service 命名方式
        gc.setServiceName("%sManager");
        // service impl 命名方式
        gc.setServiceImplName("%sManagerImpl");
        // controller 命名方式
        gc.setControllerName(null);
        // 指定生成的主键的ID类型
        gc.setIdType(IdType.ASSIGN_ID);
        return gc;
    }
}
