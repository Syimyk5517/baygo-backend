package com.example.baygo.service;

import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface AddToBasketService {
    SimpleResponse addToBasket(Long sizeId);
    List<ProductsInBasketResponse> getAllProductsFromBasket();
    SimpleResponse deleteFromBasket(Long sizeId);
}
