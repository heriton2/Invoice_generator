package com.challenge.invoice_generator.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*") // Adicione a origem permitida para o seu frontend
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Adicione os métodos HTTP permitidos
                .allowedHeaders("*"); // Permitir todos os cabeçalhos
    }
}
