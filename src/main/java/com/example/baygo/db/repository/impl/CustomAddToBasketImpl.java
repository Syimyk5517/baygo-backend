package com.example.baygo.db.repository.impl;

import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import com.example.baygo.db.repository.CustomAddToBasket;

import java.util.List;

public class CustomAddToBasketImpl implements CustomAddToBasket {
    @Override
    public List<ProductsInBasketResponse> getAllProductFromBasket() {
        String query = """
                select
                id as sizeId,
                productTitle as productTitle,
                productPhoto as productPhoto, 
                discount as discount
                from Buyer 
                """;
        return null;
    }
}
