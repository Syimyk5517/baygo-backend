package com.example.baygo.db.repository;

import com.example.baygo.db.dto.response.ProductsInBasketResponse;

import java.util.List;

public interface CustomAddToBasket {
    List<ProductsInBasketResponse> getAllProductFromBasket();

}
