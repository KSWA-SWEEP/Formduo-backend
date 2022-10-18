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
                .allowedOrigins("http://210.109.63.99:3000", "http://localhost:3000", "http://localhost:3001", "http://121.136.181.196:3001")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}
