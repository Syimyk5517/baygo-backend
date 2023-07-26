package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface ProductService {
    SimpleResponse saveProduct(ProductRequest request);

    int getBarcode();

    PaginationResponse<ProductResponseForSeller> findAll(String status, String keyWord, int page, int size);
}
