package com.bjpowernode.config;

import com.bjpowernode.config.filter.TokenVerifyFilter;
import com.bjpowernode.config.handler.MyAccessDeniedHandler;
import com.bjpowernode.config.handler.MyAuthenticationFailureHandler;
import com.bjpowernode.config.handler.MyAuthenticationSuccessHandler;
import com.bjpowernode.config.handler.MyLogoutSuccessHandler;
import com.bjpowernode.constant.Constants;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * @Author hzz
 * @Date 2024-03-09 0:10
 */
@EnableMethodSecurity // 开启方法级别的权限检查
@Configuration
public class SecurityConfig {

    @Resource
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Resource
    private MyAuthenticationFailureHandler myAuthenticationFailureHandler;

    @Resource
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Resource
    private MyAccessDeniedHandler myAccessDeniedHandler;

    @Resource
    private TokenVerifyFilter tokenVerifyFilter;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFileterChain(HttpSecurity httpSecurity,CorsConfigurationSource corsConfigurationSource) throws Exception {
        return httpSecurity
                .formLogin((formLogin) -> {
                    formLogin.loginProcessingUrl(Constants.LOGIN_URI) // 登录处理地址，不需要写controller
                            .usernameParameter("loginAct")
                            .passwordParameter("loginPwd")
                            .successHandler(myAuthenticationSuccessHandler)
                            .failureHandler(myAuthenticationFailureHandler);
                })
                // formLogin.loginProcessingUrl("/api/login").usernameParameter("loginAct").passwordParameter("loginPwd")这些链式调用并没有返回新的对象类型
                // 而是返回了配置器本身（FormLoginConfigurer<HttpSecurity>），以便可以继续配置其他属性。
                // 完全避免使用lambda和匿名内部类的方式：
                // FormLoginConfigurer<HttpSecurity> formLoginConfigurer = new FormLoginConfigurer<>();
                // formLoginConfigurer.setLoginProcessingUrl("/api/login");
                // formLoginConfigurer.setUsernameParameter("loginAct");
                // formLoginConfigurer.setPasswordParameter("loginPwd");

                .authorizeHttpRequests((authoriize) -> {
                    authoriize.requestMatchers("/api/login","/api/user/register").permitAll()
                            //其他任何请求都需要登录后才需要访问
                            .anyRequest().authenticated();
                })
                .csrf((csrf) -> {
                    csrf.disable(); //关闭跨域请求保护，即禁用跨站请求伪造
                })
                // 不用lambda表达式的写法：httpSecurity.csrf().disable();

                // 支持跨域请求
                .cors((cors) -> {
                    cors.configurationSource(corsConfigurationSource);
                })

                .sessionManagement((session) -> {
                    //session创建策略
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 无session状态，也就是禁用session
                })

                // 添加自定义的Filter
                .addFilterBefore(tokenVerifyFilter, LogoutFilter.class)

                // 退出登录
                .logout((logout) -> {
                    logout.logoutUrl("/api/logout") // 退出提交到该地址，该地址不需要我们写controller的，是框架处理
                        .logoutSuccessHandler(myLogoutSuccessHandler);
                })

                // 无权限时的处理
                .exceptionHandling((exceptionHandling) -> {
                    exceptionHandling.accessDeniedHandler(myAccessDeniedHandler);
                })

                .build();
    }

    // 跨域
    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*")); // 允许任何来源 本项目的源头：http://localhost:8081 ，即前端项目的地址
        configuration.setAllowedMethods(Arrays.asList("*")); // 允许任何请求方法 post、get、put、delete
        configuration.setAllowedHeaders(Arrays.asList("*")); // 允许任何请求头 不管请求头里传什么，都是允许的

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**",configuration);
        return source;
    }
}
