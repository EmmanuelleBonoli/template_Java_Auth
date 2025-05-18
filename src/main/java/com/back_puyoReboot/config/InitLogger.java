package com.back_puyoReboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

@Configuration
public class InitLogger {

  private static final Logger logger = LoggerFactory.getLogger(InitLogger.class);
  private static final String[] ALLOWED_ENVIRONMENTS = { "development", "staging", "production" };

  private static final String RESET = "\u001B[0m";
  private static final String GREEN = "\u001B[32m";
  private static final String CYAN = "\u001B[36m";

  @Value("${spring.datasource.url}")
  private String datasourceUrl;

  @Value("${spring.datasource.username}")
  private String datasourceUsername;

  private void echoSeparator() {
    logger.info(CYAN + "--------------------------------------" + RESET);
  }

  private void echoGreen(String message) {
    logger.info(GREEN + message + RESET);
  }

  @EventListener
  public void logDataSourceConfig(ApplicationReadyEvent event) {
    logger.info("Current data source configuration:");
    logger.info("URL: {}", datasourceUrl);
    logger.info("Username: {}", datasourceUsername);
  }

  public void logCurrentEnvironment(ApplicationContext context) {
    echoSeparator();
    echoGreen("✨ Starting server for Environment: " + getEnvironment(context));
    echoSeparator();
  }

  private String getEnvironment(ApplicationContext context) {
    String[] activeProfiles = context.getEnvironment().getActiveProfiles();
    if (activeProfiles.length == 0) {
      return "development";
    } else {
      String environment = String.join(", ", activeProfiles);
      verifyEnvironment(environment);
      return environment;
    }
  }

  public void verifyEnvironment(String environment) {
    boolean isAllowed = false;
    for (String allowedEnvironment : ALLOWED_ENVIRONMENTS) {
      if (allowedEnvironment.equals(environment)) {
        isAllowed = true;
        break;
      }
    }

    if (!isAllowed) {
      throw new IllegalArgumentException("Environment \"" + environment + "\" not allowed. Please check the configuration.");
    }
  }
}
