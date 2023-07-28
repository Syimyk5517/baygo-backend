package com.example.baygo.repository;

import com.example.baygo.db.model.Supply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyRepository extends JpaRepository<Supply, Long> {
}