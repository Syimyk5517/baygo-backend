package com.example.baygo.repository;

import com.example.baygo.db.dto.response.DiscountProductResponse;
import com.example.baygo.db.model.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    List<Discount> findByDateOfFinishIsLessThanEqual(LocalDateTime date);

    @Query("""
            SELECT NEW com.example.baygo.db.dto.response.DiscountProductResponse(
            p.id, sp.id, sp.mainImage, sp.articulOfSeller, sp.articulBG, p.subCategory.name, p.brand, CAST(ROUND(p.rating, 0) AS INTEGER), sp.color, COALESCE(d.percent, 0)
            )
            FROM Product p
            JOIN p.subProducts sp
            LEFT JOIN sp.discount d
            WHERE p.seller.id = :sellerId AND (:isForCancel = FALSE AND COALESCE(d.percent, 0) = 0 OR :isForCancel = TRUE AND COALESCE(d.percent, 0) > 0 )
            """)
    Page<DiscountProductResponse> getAllProducts(Long sellerId, boolean isForCancel, Pageable pageable);
}
