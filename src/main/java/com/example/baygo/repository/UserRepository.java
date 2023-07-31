package com.example.baygo.repository;

import com.example.baygo.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<User> findUserByEmail(String email);

    Optional<User> findByResetPasswordToken(String token);
}