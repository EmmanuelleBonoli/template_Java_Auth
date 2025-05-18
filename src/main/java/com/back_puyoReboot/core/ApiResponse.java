package com.back_puyoReboot.core;

import java.time.LocalDateTime;

public record ApiResponse<T>(String message, T payload, LocalDateTime timestamp) {
  public ApiResponse(String message, T payload) {
    this(message, payload, LocalDateTime.now());
  }
}
