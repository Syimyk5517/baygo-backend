package com.example.baygo.repository;

import com.example.baygo.db.model.SupplyProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SupplyProductRepository extends JpaRepository<SupplyProduct, Long> {
}