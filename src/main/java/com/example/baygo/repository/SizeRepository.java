package com.example.baygo.repository;

import com.example.baygo.db.model.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SizeRepository extends JpaRepository<Size, Long> {
    @Query("SELECT CASE WHEN COUNT(sp) > 0 THEN TRUE ELSE FALSE END FROM " +
            "SupplyProduct sp " +
            "JOIN sp.size s " +
            "JOIN s.subProduct sub " +
            "WHERE sub.id = ?1")
    Boolean isSupplyProduct(Long subProductId);

    @Query("SELECT CASE WHEN COUNT (o) > 0 THEN TRUE ELSE FALSE END FROM " +
            "OrderSize o " +
            "JOIN o.size s " +
            "JOIN s.subProduct sub " +
            "WHERE sub.id = ?1")
    Boolean isOrderSize(Long subProductId);

}
