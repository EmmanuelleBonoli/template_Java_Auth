package com.back_puyoReboot.Authentication;

import static com.back_puyoReboot.Authentication.ValidationConstants.MIN_PASSWORD_LENGTH;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserLoginRequestDTO(
  @NotBlank(message = "L'e-mail est requis") @Email(message = "L'e-mail doit être valide") String email,

  @NotBlank(message = "Le mot de passe est requis")
  @Size(min = MIN_PASSWORD_LENGTH, message = "Le mot de passe doit contenir au moins 8 caractères")
  String password
) {}
