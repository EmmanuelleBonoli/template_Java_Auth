package com.back_puyoReboot.User;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT MAX(u.playerNumber) FROM User u")
    Long findMaxPlayerNumber();
}
