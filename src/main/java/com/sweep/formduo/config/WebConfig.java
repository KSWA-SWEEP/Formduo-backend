package com.sweep.formduo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry){
        registry.addMapping("/**")
                .allowedOrigins("http://210.109.63.99:3000", "http://172.16.1.239:3000", "https://172.16.1.239:3000", "http://localhost:3000", "http://localhost:3001", "https://formduo.ddns.net")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
