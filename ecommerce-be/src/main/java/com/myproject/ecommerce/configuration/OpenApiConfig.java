package com.myproject.ecommerce.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI(
            @Value("${open.api.title}") String title,
            @Value("${open.api.version}") String version,
            @Value("${open.api.description}") String description,
            @Value("${open.api.server-url}") String serverUrl,
            @Value("${open.api.server-name}") String serverName) {
        return new OpenAPI()
                .info(new Info()
                        .title(title)
                        .version(version)
                        .description(description)
                        .license(new License().name("API License").url("http://domain.vn/license")))
                .servers(List.of(new Server().url(serverUrl).description(serverName)))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }

    @Bean
    public GroupedOpenApi accountGroup() {
        return GroupedOpenApi.builder()
                .group("Accounts")
                .pathsToMatch("/api/accounts/**")
                .build();
    }

    @Bean
    public GroupedOpenApi authGroup() {
        return GroupedOpenApi.builder()
                .group("Authentication")
                .pathsToMatch("/api/auth/**")
                .build();
    }

    @Bean
    public GroupedOpenApi cartGroup() {
        return GroupedOpenApi.builder()
                .group("Cart")
                .pathsToMatch("/api/cart/**")
                .build();
    }

    @Bean
    public GroupedOpenApi categoriesGroup() {
        return GroupedOpenApi.builder()
                .group("Categories")
                .pathsToMatch("/api/categories/**")
                .build();
    }

    @Bean
    public GroupedOpenApi checkoutGroup() {
        return GroupedOpenApi.builder()
                .group("Checkout")
                .pathsToMatch("/api/checkout/**")
                .build();
    }

    @Bean
    public GroupedOpenApi ordersGroup() {
        return GroupedOpenApi.builder()
                .group("Orders")
                .pathsToMatch("/api/orders/**")
                .build();
    }

    @Bean
    public GroupedOpenApi orderItemsGroup() {
        return GroupedOpenApi.builder()
                .group("Order Items")
                .pathsToMatch("/api/order-items/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productsGroup() {
        return GroupedOpenApi.builder()
                .group("Products")
                .pathsToMatch("/api/products/**")
                .build();
    }

    @Bean
    public GroupedOpenApi productGalleryGroup() {
        return GroupedOpenApi.builder()
                .group("Product Gallery")
                .pathsToMatch("/api/product-gallery/**")
                .build();
    }

    @Bean
    public GroupedOpenApi reviewsGroup() {
        return GroupedOpenApi.builder()
                .group("Reviews")
                .pathsToMatch("/api/reviews/**")
                .build();
    }

    @Bean
    public GroupedOpenApi uploadFilesGroup() {
        return GroupedOpenApi.builder()
                .group("Upload Files")
                .pathsToMatch("/api/upload/**")
                .build();
    }

    @Bean
    public GroupedOpenApi usersGroup() {
        return GroupedOpenApi.builder()
                .group("Users")
                .pathsToMatch("/api/users/**")
                .build();
    }

}
