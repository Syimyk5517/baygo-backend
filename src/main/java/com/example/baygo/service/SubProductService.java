package com.example.baygo.service;

import com.example.baygo.db.dto.response.product.ProductGetByIdResponse;

public interface SubProductService {
    ProductGetByIdResponse getById(Long subProductId);
}
