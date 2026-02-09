package com.ploydev.SimuladorDeRiscos.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Simulador de Riscos")
                        .version("1.0.0")
                        .description("Sistema de avaliação e simulação de riscos de segurança da informação")
                        .contact(new Contact()
                                .name("Vinicius Henrique")
                                .email("viniciushf0360@gmail.com")
                                .url("https://github.com/DevViniciusAlves/SimuladorDeRiscos")
                        )
                );
    }
}