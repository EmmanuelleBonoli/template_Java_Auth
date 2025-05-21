package com.back_puyoReboot.config;

import com.back_puyoReboot.User.AccountEnumType;
import com.back_puyoReboot.User.User;
import com.back_puyoReboot.User.UserEnumType;
import com.back_puyoReboot.User.UserRepository;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

  private final Long PLAYER_NUMBER_ONE = 1L;
  private final Long PLAYER_NUMBER_TWO = 2L;

  private final UserRepository userRepository;

  public DatabaseInitializer(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Bean
  CommandLineRunner init() {
    List<User> users = Arrays.asList(
      new User(
        new HashSet<>(List.of(UserEnumType.ROLE_PLAYER, UserEnumType.ROLE_ADMIN)),
        AccountEnumType.ACTIVE,
        "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq", // password is "Password"
        "manu@gmail.com",
        "MajorManu",
        "/images/User/DonkeyKong.png",
        PLAYER_NUMBER_ONE
      ),
      new User(
        new HashSet<>(List.of(UserEnumType.ROLE_PLAYER)),
        AccountEnumType.ACTIVE,
        "$2a$10$jw6BeI/txUaC1BQNGYZn4.hs5wpmLhe2uYpTBB40oUveFE3ZRQYQq", // password is "Password"
        "player@gmail.com",
        "Player1",
        "/images/User/DonkeyKong.png",
        PLAYER_NUMBER_TWO
      )
    );

    return args -> {
      // save general data
      userRepository.saveAll(users);
    };
  }
}
