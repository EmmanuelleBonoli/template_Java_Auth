package com.back_puyoReboot.Exception;

public class EmailAlreadyUsedException extends RuntimeException {

  public EmailAlreadyUsedException(String message) {
    super(message);
  }
}
