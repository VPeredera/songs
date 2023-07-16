package com.peredera.songs.util.config.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "VPeredera",
                        email = "vladislavperedera@gmail.com",
                        url = "https://github.com/VPeredera"
                ),
                description = "Spring Boot RESTful service using springdoc-openapi and OpenAPI 3.",
                title = "Song CRUD API",
                version = "v0.0.1",
                license = @License(
                        name = "MIT License",
                        url = "https://choosealicense.com/licenses/mit/"
                )
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:8888"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "https://songs-api.com"
                )
        }
)
public class OpenApiConfig {
}