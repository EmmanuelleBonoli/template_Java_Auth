package com.back_puyoReboot.User;

public record UserLoginResponseDTO(String email, String playerName, String avatar) {
  public static UserLoginResponseDTO fromEntityToDTO(User user) {
    return new UserLoginResponseDTO(user.getEmail(), user.getPlayerName(), user.getAvatar());
  }
}
