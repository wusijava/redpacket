package com.zanclick.redpacket.api.context;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author duchong
 * @date 2020-5-28 16:28:07
 **/
@Configuration
public class ApiConfiguration implements WebMvcConfigurer {

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestContextInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(gatewayInterceptor()).addPathPatterns("/gateway.do");
    }

    @Bean
    public OpenApiInterceptor gatewayInterceptor() {
        return new OpenApiInterceptor();
    }

    @Bean
    public RequestContextInterceptor requestContextInterceptor() {
        return new RequestContextInterceptor();
    }
}
