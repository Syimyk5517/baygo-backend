package com.example.baygo.service;

import com.example.baygo.db.dto.request.SellerProductRequest;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    SimpleResponse saveProduct(SellerProductRequest request);

    PaginationResponse<ProductResponseForSeller> findAll(String status, String keyWord, int page, int size);
}
