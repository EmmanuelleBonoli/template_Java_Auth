package com.back_puyoReboot;

import com.back_puyoReboot.config.InitLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
public class BackendProjectTemplateApplication {

  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(BackendProjectTemplateApplication.class, args);
    InitLogger logger = new InitLogger();
    logger.logCurrentEnvironment(context);
  }

  @Bean
  public RestTemplate restTemplate() {
    return new RestTemplate();
  }
}
