package com.back_puyoReboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
      .info(new Info().title("My backend template project").version("1.0").description("Documentation de l'API avec Springdoc OpenAPI"));
  }
}
