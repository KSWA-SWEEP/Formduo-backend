package com.sweep.formduo.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Formduo API",
                description = "설문 서비스 \"폼듀\" API 명세서",
                version = "v1"))

@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi NonSecurityGroupOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("Non Security Open Api")
                .pathsToMatch("/api/v1/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi SecurityGroupOpenApi() {
        return GroupedOpenApi
                .builder()
                .group("Security Open Api")
                .pathsToExclude("/api/v1/auth/**", "/v2/**")
                .addOpenApiCustomiser(buildSecurityOpenApi())
                .build();
    }

//    @Bean
//    public GroupedOpenApi publicApi() {
//        String[] paths = {"/api/v1/**"};
//
//        return GroupedOpenApi.builder()
//                .group("v1-definition")
//                .pathsToMatch(paths)
//                .build();
//    }

    public OpenApiCustomiser buildSecurityOpenApi() {
        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .type(SecurityScheme.Type.HTTP)
                .in(SecurityScheme.In.HEADER)
                .bearerFormat("JWT")
                .scheme("bearer");

        return OpenApi -> OpenApi
                .addSecurityItem(new SecurityRequirement().addList("jwt token"))
                .getComponents().addSecuritySchemes("jwt token", securityScheme);
    }
}


