package com.felipegabriel.ecommerceapi.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 *
 * Reference: https://www.bezkoder.com/spring-boot-swagger-3/
 *
 * */

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI myOpenApi() {
        Server devServer = new Server();

        Info info = new Info().title("E-Commerce Management API");

        return new OpenAPI().info(info).servers(List.of(devServer));
    }
}
