package com.example.baygo.repository;

import com.example.baygo.db.model.SupplyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplyProductRepository extends JpaRepository<SupplyProduct, Long> {
}