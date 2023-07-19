package com.example.baygo.db.service;

import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface AddBasketService {
    SimpleResponse addToBasket(Long sizeId);
    List<ProductsInBasketResponse> getAllProductFromBasket();
    SimpleResponse deleteFromBasket(Long sizeId);

}
