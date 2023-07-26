package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SimpleResponse;

import java.util.List;

public interface ProductService {
   SimpleResponse saveProduct(ProductRequest request);
   int getBarcode();

    List<ProductResponseForSeller> findAll(int page, int size, String status, String keyWord);

}
