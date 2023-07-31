package com.example.baygo.repository;

import com.example.baygo.db.model.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount,Long> {
    List<Discount> findByDateOfFinishIsLessThanEqual(LocalDateTime date);
}
