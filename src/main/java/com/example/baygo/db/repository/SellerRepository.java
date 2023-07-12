package com.example.baygo.db.repository;

import com.example.baygo.db.model.Seller;
import com.example.baygo.db.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    @Query("select u from User u where u.email = ?1")
    Optional<User> findUserByEmail(String email);
}
