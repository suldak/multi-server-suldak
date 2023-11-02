package com.sulsul.suldaksuldak.config;

import com.sulsul.suldaksuldak.interceptor.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new Interceptor())
                .excludePathPatterns("/css/**", "/images/**", "/js/**");
    }

//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                .allowedOrigins("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("Authorization", "Content-Type")
//                .exposedHeaders("Custom-Header")
//                .allowCredentials(true)
//                .maxAge(3600);
//    }
}
