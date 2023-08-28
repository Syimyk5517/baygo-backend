package com.example.baygo.repository;

import com.example.baygo.db.model.SupplyProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SupplyProductRepository extends JpaRepository<SupplyProduct, Long> {
    @Query("select s from SupplyProduct s JOIN Supply sup ON sup.id = s.supply.id JOIN Size s2 ON s2.id = s.size.id WHERE s2.barcode = ?1 AND sup.id = ?2 ")
    Optional<SupplyProduct> findBySupplyProductWithBarcode(int barcode,Long supplyId);
}