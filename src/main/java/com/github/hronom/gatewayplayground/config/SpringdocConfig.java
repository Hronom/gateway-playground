package com.github.hronom.gatewayplayground.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springdoc.core.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class SpringdocConfig {
    private final Logger logger = LoggerFactory.getLogger(SpringdocConfig.class);

    @Autowired
    public SpringdocConfig(
            SwaggerUiConfigProperties swaggerUiConfigProperties
    ) {

        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = new HashSet<>();
        //urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("auth", "https://raw.githubusercontent.com/Hronom/openapi-playground/main/openapi/auth/spec.yaml", null));
        urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("auth", "http://localhost:8080/api/spec/auth.yaml", null));
        urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("user-search", "http://localhost:8080/api/spec/user-search.yaml", null));
        urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl("user-search-v2", "http://localhost:8080/api/spec/user-search-v2.yaml", null));
        swaggerUiConfigProperties.setUrls(urls);
    }
}
