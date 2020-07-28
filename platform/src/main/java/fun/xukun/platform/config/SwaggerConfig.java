package fun.xukun.platform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 日期:2020/3/10
 * Swagger2配置类
 *
 * @author xukun
 * @version 1.00
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private ApiInfo initApiInfo() {
        return new ApiInfoBuilder()
        // 大标题
        .title("Aloha Platform API")
        // 简单的描述
        .description("Aloha后台管理系统后台服务接口文档")
        // 版本
        .version("1.0")
        .build();
    }

    @Bean
    public Docket restfulApi() {
        return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.any())
        .paths(PathSelectors.any())
        .apis(RequestHandlerSelectors.basePackage("fun.xukun.platform"))
        .build()
        .groupName("1.0")
        .apiInfo(initApiInfo());
    }
}