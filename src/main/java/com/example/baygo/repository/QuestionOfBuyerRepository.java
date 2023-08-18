package com.example.baygo.repository;

import com.example.baygo.db.model.BuyerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionOfBuyerRepository extends JpaRepository<BuyerQuestion, Long> {
    @Query("""
           SELECT COUNT (bq) FROM BuyerQuestion bq
           JOIN bq.subProduct sp
           JOIN sp.product p
           WHERE p.seller.id = :sellerId AND bq.answer IS NULL
           """)
    int countOfUnansweredBySellerId(Long sellerId);

    @Query("""
           SELECT COUNT (bq) FROM BuyerQuestion bq
           JOIN bq.subProduct sp
           JOIN sp.product p
           WHERE p.seller.id = :sellerId AND bq.answer IS NOT NULL
           """)
    int countOfAnsweredBySellerId(Long sellerId);
}
