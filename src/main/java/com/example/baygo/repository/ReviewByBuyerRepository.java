package com.example.baygo.repository;

import com.example.baygo.db.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReviewByBuyerRepository extends JpaRepository<Review,Long> {
    @Query("SELECT CASE WHEN COUNT (b) > 0 THEN TRUE ELSE FALSE END FROM " +
            "Buyer b " +
            "JOIN b.orders o " +
            "JOIN o.orderSizes os " +
            "JOIN os.size s " +
            "JOIN s.subProduct sub " +
            "WHERE sub.id = :subProductId " +
            "AND b.id = :buyerId")
    Boolean isOrderOfBuyer(Long subProductId, Long buyerId);
}
