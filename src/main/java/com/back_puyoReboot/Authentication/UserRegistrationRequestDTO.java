package com.back_puyoReboot.Authentication;

import static com.back_puyoReboot.Authentication.ValidationConstants.*;

import jakarta.validation.constraints.*;

public record UserRegistrationRequestDTO(
  @NotBlank(message = "L'email est requis") @Email(message = "L'email doit être valide") String email,

  @NotBlank(message = "Le mot de passe est requis")
  @Pattern(
    regexp = PASSWORD_REGEX,
    message = "Le mot de passe doit contenir une minuscule, une majuscule, un chiffre, un caractère spécial et au moins 8 caractères"
  )
  String password
) {}
