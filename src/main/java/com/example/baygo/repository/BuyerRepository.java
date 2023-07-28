package com.example.baygo.repository;

import com.example.baygo.db.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
}