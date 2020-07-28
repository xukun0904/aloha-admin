package fun.xukun.platform.security.config;

import fun.xukun.platform.config.AlohaProperties;
import fun.xukun.platform.security.jwt.JwtAccessDeniedHandler;
import fun.xukun.platform.security.jwt.JwtAuthenticationEntryPoint;
import fun.xukun.platform.security.jwt.JwtComponent;
import fun.xukun.platform.security.jwt.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 日期:2020/6/9
 * Spring Security配置
 *
 * @author xukun
 * @version 1.00
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AlohaProperties alohaProperties;

    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    private final JwtComponent jwtComponent;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SecurityProperties securityProperties = alohaProperties.getSecurity();
        // 关闭CSRF跨域
        http.csrf().disable()
        // 授权异常
        .exceptionHandling()
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        .accessDeniedHandler(jwtAccessDeniedHandler).and()
        .addFilterBefore(new JwtTokenFilter(alohaProperties, jwtComponent), UsernamePasswordAuthenticationFilter.class)
        // 防止iframe 造成跨域
        .headers().frameOptions().disable()
        // 不创建会话
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        // 设置拦截忽略文件夹，可以对静态资源放行
        .and().authorizeRequests().antMatchers(securityProperties.getIgnoreUrls()).permitAll()
        // 放行OPTIONS请求
        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
        .anyRequest().authenticated()
        // 退出登录
        .and().logout().deleteCookies(alohaProperties.getJwt().getCookieName());
    }

    /**
     * 采用BCrypt对密码进行编码
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
