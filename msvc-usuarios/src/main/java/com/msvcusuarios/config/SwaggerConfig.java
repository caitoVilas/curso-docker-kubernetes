package com.msvcusuarios.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi openApi(){
        return GroupedOpenApi.builder()
                .group("usuario")
                .packagesToScan("com.msvcusuarios")
                .build();
    }

    @Bean
    public OpenAPI api(){
        String version = "1.0";
        return new OpenAPI()
                .info(new Info().title("MSVC - Modulo Usuarios")
                        .description("Modulo de Usuarios")
                        .contact(new Contact().name("caito").email("caitocd@gmail.com"))
                        .version(version));
    }
}
