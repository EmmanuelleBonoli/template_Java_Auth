package com.back_puyoReboot.Authentication;

public class ValidationConstants {

  public static final int MAX_LENGTH = 50;
  public static final int MIN_PASSWORD_LENGTH = 8;
  public static final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_\\-+=\\[\\]{};':\"\\\\|,.<>/?]).{8,}$";
}
