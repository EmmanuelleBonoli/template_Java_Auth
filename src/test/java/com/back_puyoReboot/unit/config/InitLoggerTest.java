package com.back_puyoReboot.unit.config;

import com.back_puyoReboot.config.InitLogger;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InitLoggerTest {

    @Test
    public void shouldVerifyEnvironmentIsValid() {
        InitLogger logger = new InitLogger();
        String[] environments = {"development", "staging", "production"};
        for (String env : environments) {
            assertDoesNotThrow(() -> logger.verifyEnvironment(env));
        }
    }

    @Test
    public void shouldThrowExceptionForInvalidEnvironment() {
        InitLogger logger = new InitLogger();
        String invalidEnv = "invalid-env";
        assertThrows(IllegalArgumentException.class, () -> logger.verifyEnvironment(invalidEnv));
    }

}