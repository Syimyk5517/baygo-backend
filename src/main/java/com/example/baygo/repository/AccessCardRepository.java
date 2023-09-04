package com.example.baygo.repository;

import com.example.baygo.db.model.AccessCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccessCardRepository extends JpaRepository<AccessCard, Long> {
}