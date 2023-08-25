package com.example.baygo.repository;

import com.example.baygo.db.model.OrderSize;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderSizeRepository extends JpaRepository<OrderSize, Long> {
    List<OrderSize> findAllByIdIn(List<Long> singletonList);
}