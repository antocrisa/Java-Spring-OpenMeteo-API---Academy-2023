package com.example.demo.configuration;

import com.example.demo.constant.Path;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfiguration {

    @Bean
    public GroupedOpenApi cityOpenApiGroup(){
        return GroupedOpenApi.builder()
                .group("Cities")
                .displayName("Cities API")
                .packagesToScan("com.example.demo")
                .pathsToMatch(Path.CITIES + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi geoCoordinateOpenApiGroup(){
        return GroupedOpenApi.builder()
                .group("Geo")
                .displayName("Geo API")
                .packagesToScan("com.example.demo")
                .pathsToMatch(Path.GEO + "/**")
                .build();
    }

    @Bean
    public GroupedOpenApi weatherOpenApiGroup(){
        return GroupedOpenApi.builder()
                .group("Weather")
                .displayName("Weather API")
                .packagesToScan("com.example.demo")
                .pathsToMatch(Path.WEATHER + "/**")
                .build();
    }


    @Bean
    public OpenAPI openAPIConfig() {
        return new OpenAPI()
                .info(new Info()
                        .title("API services")
                        .description("City-Weather services")
                        .version("1.0.0"));
    }
}
