package com.felix.basic_projects.mini_market.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(new Info()
        .title("Mini Market API")
        .version("1.0.0")
        .description("API documentation for Mini Market application")
        .contact(new Contact()
          .name("Felix")
          .email("felix@example.com")
          .url("https://example.com")));
  }

}
