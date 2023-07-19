package com.example.baygo.db.repository;

import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import com.example.baygo.db.model.Buyer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BuyerRepository extends JpaRepository<Buyer, Long> {
    @Query("select new com.example.baygo.db.dto.response.ProductsInBasketResponse(b.basket)from Buyer b join Size s where b.basket= ")
    List<ProductsInBasketResponse> getAllProductFromBasket();

}
