package com.healthhub.healthhub.webConfig;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI healthHubOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("HealthHub API")
                        .description("REST API documentation for the HealthHub application")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("HealthHub Support")
                                .email("support@healthhub.com")));
    }
}
