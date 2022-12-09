package com.github.hronom.gatewayplayground.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class AppConfig {
    private final Logger logger = LoggerFactory.getLogger(AppConfig.class);

    private static final String API_URI = "/v3/api-docs";

    private final RouteDefinitionLocator locator;

    @Autowired
    public AppConfig(RouteDefinitionLocator locator) {
        this.locator = locator;
    }

    @Bean
    public GroupedOpenApi apis(SwaggerUiConfigProperties swaggerUiConfigProperties) {
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();

//        locator.getRouteDefinitions().subscribe(routeDefinition -> {
//            logger.info("Discovered route definition: {}", routeDefinition.getId());
//            String resourceName = routeDefinition.getId();
//            String location = routeDefinition.getPredicates().get(0).getArgs().get("_genkey_0").replace("/**", API_URI);
//            logger.info("Adding swagger resource: {} with location {}", resourceName, location);
//            urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl(resourceName, location, resourceName));
//        });
        urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("auth", "https://raw.githubusercontent.com/Hronom/openapi-playground/main/openapi/auth/spec.yaml", null));
        urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("user-search", "https://raw.githubusercontent.com/Hronom/openapi-playground/main/openapi/user-search/spec.yaml", null));
        urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("user-search-v2", "https://raw.githubusercontent.com/Hronom/openapi-playground/main/openapi/user-search-v2/spec.yaml", null));

        swaggerUiConfigProperties.setUrls(urls);

        return GroupedOpenApi.builder()
                .group("resource")
                .pathsToMatch("/api/**")
                .build();
    }
}
