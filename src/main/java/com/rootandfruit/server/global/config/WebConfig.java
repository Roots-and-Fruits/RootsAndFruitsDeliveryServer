package com.rootandfruit.server.global.config;

import com.rootandfruit.server.global.auth.jwt.TokenInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final TokenInterceptor tokenInterceptor;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*", "http://localhost:5173", "https://yeolmae.store")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE")
                .allowedHeaders("Authorization", "Content-Type")
                .maxAge(3600);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/v1/delivery/**", "/api/v1/product/**", "/api/v1/order/**",
                        "/api/v1/order/cancel/**", "/api/v1/admin/**")
                .excludePathPatterns("/api/v1/admin/authenticate", "/api/v1/delivery/max", "/api/v1/product/sailed",
                        "/api/v1/product/all", "/api/v1/order", "/api/v1/order/{orderNumber}");
    }
}