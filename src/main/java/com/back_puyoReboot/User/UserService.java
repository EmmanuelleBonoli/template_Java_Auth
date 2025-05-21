package com.back_puyoReboot.User;

import com.back_puyoReboot.Authentication.UserRegistrationRequestDTO;
import com.back_puyoReboot.Exception.EmailAlreadyUsedException;
import com.back_puyoReboot.Exception.ResourceNotFoundException;
import java.util.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void checkUserExists(String email) {
    if (userRepository.existsByEmail(email)) {
      throw new EmailAlreadyUsedException("Cet email est déjà utilisé");
    }
  }

  public UUID getAuthenticatedUserId(UserDetails userDetails) {
    if (userDetails == null) {
      return null;
    }
    String authenticatedUserEmail = userDetails.getUsername();
    return findByEmail(authenticatedUserEmail).getId();
  }

  public boolean registerUser(UserRegistrationRequestDTO userRegistrationDTO) {
    User user = new User(
      new HashSet<>(List.of(UserEnumType.ROLE_PLAYER)),
      AccountEnumType.ACTIVE,
      passwordEncoder.encode(userRegistrationDTO.password()),
      userRegistrationDTO.email()
    );

    user.setPlayerNumber(findNextAvailablePlayerNumber());
    user.setPlayerName("player" + user.getPlayerNumber());
    userRepository.save(user);

    return true;
  }

  public User findById(UUID id) {
    return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
  }

  public User findByEmail(String email) {
    return userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("Utilisateur non trouvé"));
  }

  public Long findNextAvailablePlayerNumber() {
    Long maxNumber = userRepository.findMaxPlayerNumber();
    return (maxNumber != null ? maxNumber + 1 : 1L);
  }
}
