package com.github.hronom.gatewayplayground.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SwaggerUiConfigProperties;
import org.springdoc.core.customizers.GlobalOpenApiCustomizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

@RestController
public class MainController {

    private final SwaggerUiConfigProperties swaggerUiConfigProperties;
    private final SpringDocConfigProperties springDocConfigProperties;
    private final List<GroupedOpenApi> groupedOpenApis;

    @Autowired
    public MainController(
            SwaggerUiConfigProperties swaggerUiConfigProperties,
            SpringDocConfigProperties springDocConfigProperties,
            List<GroupedOpenApi> groupedOpenApis
    ) {
        this.swaggerUiConfigProperties = swaggerUiConfigProperties;
        this.springDocConfigProperties = springDocConfigProperties;
        this.groupedOpenApis = groupedOpenApis;
    }

    @Operation(hidden = true)
    @PutMapping(path = "/add")
    public void addItemToSwagger() {
        String groupName = "test-" + ThreadLocalRandom.current().nextInt(0, 1000) + "-service";
        Set<AbstractSwaggerUiConfigProperties.SwaggerUrl> urls = swaggerUiConfigProperties.getUrls();
        urls.add(new AbstractSwaggerUiConfigProperties.SwaggerUrl(groupName, "https://raw.githubusercontent.com/Hronom/openapi-playground/main/openapi/user-search-v2/spec.yaml", null));
//
//        OpenAPI api = new OpenAPIV3Parser().read(this.specLocation);
    }
}
