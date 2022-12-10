package com.github.hronom.gatewayplayground.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import com.github.hronom.gatewayplayground.service.SpecProviderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.SpringDocProviders;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Locale;

import static org.springdoc.core.Constants.APPLICATION_OPENAPI_YAML;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class SpecProviderController {
    private final SpecProviderService specProviderService;
    private final SpringDocProviders springDocProviders;
    private final SpringDocConfigProperties springDocConfigProperties;


    @Autowired
    public SpecProviderController(
            SpecProviderService specProviderService,
            SpringDocProviders springDocProviders,
            SpringDocConfigProperties springDocConfigProperties
    ) {
        this.specProviderService = specProviderService;
        this.springDocProviders = springDocProviders;
        this.springDocConfigProperties = springDocConfigProperties;
    }

    /**
     * Inspired by {@link org.springdoc.webflux.api.OpenApiActuatorResource#openapiJson(ServerHttpRequest, Locale)}
     */
    @Operation(hidden = true)
    @GetMapping(path = "/api/spec/{serviceName}.json", produces = APPLICATION_JSON_VALUE)
    public Mono<String> getApiSpecServiceJson(@PathVariable String serviceName) throws IOException {
        return Mono.just(writeJsonValue(specProviderService.getOpenApi(serviceName)));
    }

    /**
     * Inspired by {@link org.springdoc.webflux.api.OpenApiActuatorResource#openapiYaml(ServerHttpRequest, Locale)}
     */
    @Operation(hidden = true)
    @GetMapping(path = "/api/spec/{serviceName}.yaml", produces = APPLICATION_OPENAPI_YAML)
    public Mono<String> getApiSpecServiceYaml(@PathVariable String serviceName) throws IOException {
        return Mono.just(writeYamlValue(specProviderService.getOpenApi(serviceName)));
    }

    protected String writeJsonValue(OpenAPI openAPI) throws JsonProcessingException {
        String result;
        ObjectMapper objectMapper = springDocProviders.jsonMapper();
        if (springDocConfigProperties.isWriterWithOrderByKeys())
            ObjectMapperProvider.sortOutput(objectMapper, springDocConfigProperties);
        if (!springDocConfigProperties.isWriterWithDefaultPrettyPrinter())
            result = objectMapper.writerFor(OpenAPI.class).writeValueAsString(openAPI);
        else
            result = objectMapper.writerWithDefaultPrettyPrinter().forType(OpenAPI.class).writeValueAsString(openAPI);
        return result;
    }

    protected String writeYamlValue(OpenAPI openAPI) throws JsonProcessingException {
        String result;
        ObjectMapper objectMapper = springDocProviders.yamlMapper();
        if (springDocConfigProperties.isWriterWithOrderByKeys())
            ObjectMapperProvider.sortOutput(objectMapper, springDocConfigProperties);
        YAMLFactory factory = (YAMLFactory) objectMapper.getFactory();
        factory.configure(YAMLGenerator.Feature.USE_NATIVE_TYPE_ID, false);
        if (!springDocConfigProperties.isWriterWithDefaultPrettyPrinter())
            result = objectMapper.writerFor(OpenAPI.class).writeValueAsString(openAPI);
        else
            result = objectMapper.writerWithDefaultPrettyPrinter().forType(OpenAPI.class).writeValueAsString(openAPI);
        return result;
    }
}
