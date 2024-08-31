package com.themarket.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "박소연_CJTheMarket API",
                description = "CJ The Market 과제 API 문서",
                version = "v1"))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/api/**"};

        return GroupedOpenApi.builder()
                .group("박소연_CJTheMarket API")
                .pathsToMatch(paths)
                .build();
    }
}
