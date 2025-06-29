package com.example.bookmanager.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI bookManagerOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                        .title("Book Manager API")
                        .description("API for Managing Books")
                        .version("1.0"));
    }
}
