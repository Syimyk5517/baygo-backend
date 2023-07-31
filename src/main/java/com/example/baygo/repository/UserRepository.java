package com.example.baygo.repository;

import com.example.baygo.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);
}