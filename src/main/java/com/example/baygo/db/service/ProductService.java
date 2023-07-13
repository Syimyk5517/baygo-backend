package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.response.SimpleResponse;

public interface ProductService {
   SimpleResponse saveProduct(ProductRequest request);
   int getBarcode();
}
