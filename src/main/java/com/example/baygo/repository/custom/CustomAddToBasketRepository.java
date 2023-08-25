package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CustomAddToBasketRepository {
    List<ProductsInBasketResponse> getAllProductFromBasket();
}
