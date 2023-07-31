package com.example.baygo.repository;

import com.example.baygo.db.model.BuyerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BuyerQuestionRepository extends JpaRepository<BuyerQuestion, Long> {
}
