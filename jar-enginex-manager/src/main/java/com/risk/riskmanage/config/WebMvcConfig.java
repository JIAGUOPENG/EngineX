package com.risk.riskmanage.config;

import com.risk.riskmanage.spring.interceptor.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private SessionInterceptor sessionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //  添加拦截器
        registry.addInterceptor(sessionInterceptor)
                .excludePathPatterns("")  //  排除拦截器要拦截的路径
                .addPathPatterns("/**");    //  添加拦截器需要要拦截的路径

    }

}
