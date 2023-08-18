package com.example.baygo.repository;

import com.example.baygo.db.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("""
           SELECT COUNT (r) FROM Review r
           JOIN r.subProduct sp
           JOIN sp.product p
           WHERE p.seller.id = :sellerId AND r.answer IS NULL
           """)
    int countOfUnansweredBySellerId(Long sellerId);

    @Query("""
           SELECT COUNT (r) FROM Review r
           JOIN r.subProduct sp
           JOIN sp.product p
           WHERE p.seller.id = :sellerId AND r.answer IS NOT NULL
           """)
    int countOfAnsweredBySellerId(Long sellerId);
}