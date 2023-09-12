package com.example.baygo.repository;

import com.example.baygo.db.model.OrderSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderSizeRepository extends JpaRepository<OrderSize, Long> {
    List<OrderSize> findAllByIdIn(List<Long> singletonList);
    @Query("SELECT COUNT(r.productQuantity) FROM OrderSize o JOIN Return r ON r.orderSize.id = o.id ")
    int countOfProductsToBeReturned(Long id);
}