# ALOHA-ADMIN 后台管理平台

## 项目介绍

基于SpringBoot+Mybatis-Plus+SpringSecurity+JWT+Redis+Layui简单规范的后台管理平台🐏

### 组织结构

``` 
aloga-admin
├── common -- 公共模块
├── mapper -- 公用Mapper
├── platform -- 后台管理平台[端口:8091]
```

### 功能模块

- 系统管理
  - 菜单管理
  - 角色管理
  - 部门管理
  - 用户管理

### 技术选型

#### 后端技术：
技术 | 名称 | 官网
:---|:-----|----
SpringBoot | 简化开发框架 | [https://spring.io/projects/spring-boot/](https://spring.io/projects/spring-boot/)
SpringFramework | 容器  | [http://projects.spring.io/spring-framework/](http://projects.spring.io/spring-framework/)
SpringMVC | MVC框架  | [https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/web.html#spring-web](https://docs.spring.io/spring/docs/5.2.8.RELEASE/spring-framework-reference/web.html#spring-web) 
SpringSecurity | 安全框架  | [https://spring.io/projects/spring-security/](https://spring.io/projects/spring-security/) 
SpringCache | 缓存框架 | [https://spring.io/guides/gs/caching/](https://spring.io/guides/gs/caching/) 
MyBatis | ORM框架  | [http://www.mybatis.org/mybatis-3/zh/index.html](http://www.mybatis.org/mybatis-3/zh/index.html)
MyBatis-Plus | ORM简化开发 | [https://mp.baomidou.com/](https://mp.baomidou.com/) 
HikariCP | 数据库连接池  | [https://github.com/brettwooldridge/HikariCP](https://github.com/brettwooldridge/HikariCP) 
Thymeleaf | 模板引擎  | [http://www.thymeleaf.org/](http://www.thymeleaf.org/)
Redis | 缓存数据库  | [https://redis.io/](https://redis.io/)
Logback | 日志组件  | [https://logback.qos.ch/](https://logback.qos.ch/) 
Swagger2 | 接口测试框架 | [http://swagger.io/](http://swagger.io/)
Jackson | 数据序列化  | [https://github.com/FasterXML/jackson](https://github.com/FasterXML/jackson) 
Maven | 项目构建管理  | [http://maven.apache.org/](http://maven.apache.org/)
Lombok | 消除冗余代码工具 | [https://projectlombok.org/](https://projectlombok.org/) 
easy-captcha | Java图形验证码 | [https://github.com/whvcse/EasyCaptcha](https://github.com/whvcse/EasyCaptcha) 
JJWT | JSON Web令牌 | [https://github.com/jwtk/jjwt](https://github.com/jwtk/jjwt) 

#### 前端技术：

技术 | 名称 | 官网
----|------|----
jQuery | 函式库  | [http://jquery.com/](http://jquery.com/)
Layui | 前端框架  | [https://www.layui.com/](https://www.layui.com/)
Nepadmin | 后台单页面模板 | [https://gitee.com/june000/nep-admin/tree/master/](https://gitee.com/june000/nep-admin/tree/master/) 
EleTree | 树形组件 | [https://layuiextend.hsianglee.cn/eletree/](https://layuiextend.hsianglee.cn/eletree/) 
FormSelects | 下拉多选框 | [https://fly.layui.com/extend/formSelects/](https://fly.layui.com/extend/formSelects/) 
TreeSelect | 树形下拉选择器 | [https://fly.layui.com/extend/treeSelect/](https://fly.layui.com/extend/treeSelect/) 

**初始模板基于：**[FEBS-Shiro](https://github.com/febsteam/FEBS-Shiro)

## 环境搭建

#### 开发工具：
- MySql: 数据库
- Tomcat: 应用服务器
- Git: 版本管理
- IntelliJ IDEA: 开发IDE
- PowerDesigner: 建模工具
- Heidisql: 数据库客户端

#### 开发环境：
- Jdk8+
- MariaDB10+
- Redis5+

#### 生产环境：

- Jdk8+
- MariaDB10+
- Redis5+

**工具安装指南：**[http://xukun.fun/2020/07/27/CentOS7%E4%B8%8BMariaDB%E3%80%81JDK%E5%8F%8ARedis%E5%AE%89%E8%A3%85%E4%B8%8E%E9%85%8D%E7%BD%AE/](http://xukun.fun/2020/07/27/CentOS7下MariaDB、JDK及Redis安装与配置/)

**本地部署账号密码：**

| 账号  | 密码     | 权限                             |
| ----- | -------- | -------------------------------- |
| admin | 12345678 | 超级管理员，拥有所有增删改查权限 |

**控制台参数**

- MYSQL_HOST：数据库主机地址
- MYSQL_PORT：数据库端口号
- MYSQL_DBNAME：数据库库名
- REDIS_HOST：Redis主机地址
- REDIS_PORT：Redis端口地址
- APP_PORT：应用访问端口

## 开发指南:

### 包名说明：

- common -- 通用类
- exception -- 全局异常处理类
- model -- 实体类
  - ext -- 扩展类
  - constant -- 常量类
  - request -- 请求类
  - response -- 响应类
- repository -- 数据操作类
- util -- 工具类
- web -- 控制类
- mapper -- Mapper接口类
- generator -- 代码生成类
- config -- 配置参数类
- service -- 服务类
- web -- 控制类

### 配置文件说明：

- application-redis.yml -- redis配置
- application-db.yml -- 数据库配置
- application.yml -- 应用配置
- logback-spring.xml -- 日志配置

### 框架规范约定：

约定优于配置(convention over configuration)，此框架约定了很多编程规范，下面一一列举：

```
- 每个功能目录对应一个包名，如`系统管理`对应`system`包

- pojo，mapper，manager类使用mybatis-plus-generator工具(fun.xukun.model.generator.CodeGenerator)自动生成

- service类，需要在叫名`service`的包下，并以`Service`结尾，如`DepartmentService`，实现类在对应`impl`包下

- controller类，需要在以`web`结尾的包下，类名以Controller结尾，如`DepartmentController.java`，并继承`BaseController`

- Restful接口需要对应的Swagger描述，统一使用ResponseResult对象返回

- 对可能发生的异常在`service`中使用ExceptionCast抛出，定义对应模块异常信息的枚举类

- 类名：首字母大写驼峰规则，方法名：首字母小写驼峰规则，常量：全大写，变量：首字母小写驼峰规则，尽量非缩写

- 配置文件统一放到`src/main/resources`目录下

- `RequestMapping`指定method方法

- 数据表命名为为小写以下划线分隔

- 更多规范，参考[[阿里巴巴Java开发手册]
```
