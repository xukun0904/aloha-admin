package fun.xukun.platform.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 日期:2020/6/9
 * mybatis-plus配置类
 *
 * @author xukun
 * @version 1.00
 */
@EnableTransactionManagement
@Configuration
@MapperScan("fun.xukun.model")
public class MybatisPlusConfig {

    /**
     * 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
     * paginationInterceptor.setOverflow(false);
     * 设置最大单页限制数量，默认 500 条，-1 不受限制
     * paginationInterceptor.setLimit(500);
     * 开启 count 的 join 优化,只针对部分 left join
     *
     * @return PaginationInterceptor
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));
        return paginationInterceptor;
    }
}
