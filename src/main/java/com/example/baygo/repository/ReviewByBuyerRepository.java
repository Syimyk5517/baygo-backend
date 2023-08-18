package com.example.baygo.repository;

import com.example.baygo.db.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewByBuyerRepository extends JpaRepository<Review,Long> {
}
