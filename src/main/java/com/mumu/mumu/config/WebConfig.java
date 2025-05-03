package com.mumu.mumu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 URL에 대해
                .allowedOrigins(
                    "https://mumu-for-youth.github.io",  // GitHub Pages
                    "https://mumu-for-youth-backend.click",            // 새로운 도메인
                        "http://localhost:3000",
                        "https://localhost:3000"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowCredentials(true); // 쿠키 등 인증정보 포함 허용
    }
}