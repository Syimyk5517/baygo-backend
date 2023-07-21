package com.example.baygo.db.repository;

import com.example.baygo.db.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;


public interface DiscountRepository extends JpaRepository<Discount,Long> {
    List<Discount> findByDateOfFinishIsLessThanEqual(LocalDateTime date);
}
