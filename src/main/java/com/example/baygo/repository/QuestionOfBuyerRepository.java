package com.example.baygo.repository;

import com.example.baygo.db.model.BuyerQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionOfBuyerRepository extends JpaRepository<BuyerQuestion, Long> {
}
