package com.back_puyoReboot.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import com.back_puyoReboot.User.AccountEnumType;
import com.back_puyoReboot.User.User;
import com.back_puyoReboot.User.UserEnumType;
import com.back_puyoReboot.User.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseInitializer {

    private final UserRepository userRepository;

    public DatabaseInitializer(
            UserRepository userRepository
    ) {
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
                        1L
                )
        );

        return args -> {
            // save general data
            userRepository.saveAll(users);

        };
    }
}
