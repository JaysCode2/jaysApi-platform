package com.example.apiplatform.loginUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Slf4j
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录拦截器
        registry.addInterceptor(new LoginInterceptor())
                .excludePathPatterns(
                        "/user/login",
                        "/user/register",
                        // 注意放行knife4j============
                        "/doc.html/**",
                        "/swagger-resources/**",
                        "/**/v2/api-docs",
                        "/webjars/**"
                );
    }
}
