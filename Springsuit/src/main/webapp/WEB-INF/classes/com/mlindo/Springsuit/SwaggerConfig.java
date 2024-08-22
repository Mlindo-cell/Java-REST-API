package com.mlindo.Springsuit;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import org.glassfish.jersey.server.ResourceConfig;

import jakarta.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class SwaggerConfig extends ResourceConfig {
    public SwaggerConfig() {
        // Register the OpenApiResource to expose the OpenAPI documentation
        register(OpenApiResource.class);
        
        // Optionally set custom OpenAPI configuration
       // register(new OpenAPIConfiguration());
    }

    // Method to provide custom OpenAPI configuration
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("My RESTful API")
                        .version("1.0")
                        .description("API documentation for managing users, posts, and comments.")
                        .termsOfService("https://example.com/terms")
                        .contact(new Contact()
                                .name("Support Team")
                                .email("support@example.com")
                                .url("https://example.com/support"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}
