package com.home.examination.common.config;

import com.home.examination.common.interceptor.WebAccessHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebAccessHandlerInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/app/**", "/web/user/login", "/error", "/webapp/login.jsp", "/css/**", "/js/**");
    }

}