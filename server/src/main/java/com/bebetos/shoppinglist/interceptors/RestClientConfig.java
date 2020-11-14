package com.bebetos.shoppinglist.interceptors;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class RestClientConfig {

    @Bean
    public LoggerInterceptor myLoggerInterceptor() {
        return new LoggerInterceptor();
    }


    @Bean
    public WebMvcConfigurer adapter() {
        return new WebMvcConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(myLoggerInterceptor());
            }
        };
    }
    
}
