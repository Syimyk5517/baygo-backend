package com.example.baygo.repository;

import com.example.baygo.db.model.OrderSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface OrderSizeRepository extends JpaRepository<OrderSize, Long> {
    @Query("SELECT SUM (r.productQuantity) FROM OrderSize o JOIN Return r ON r.orderSize.id = o.id WHERE o.id =?1")
    Integer countOfProductsToBeReturned(Long id);
}