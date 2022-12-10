package com.github.hronom.gatewayplayground.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class SpecProviderService {

    public OpenAPI getOpenApi(String serviceName) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        objectMapper.findAndRegisterModules();
        OpenAPI openAPI = objectMapper.readValue(
                new URL("https://raw.githubusercontent.com/Hronom/openapi-playground/main/openapi/" + serviceName + "/spec.yaml"),
                OpenAPI.class
        );
        openAPI.getServers().add(new Server().url("http://localhost:8083").description("LOCAL"));
        return openAPI;
    }
}
