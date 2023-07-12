package com.example.baygo.db.service;

import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.responces.SimpleResponse;

public interface ProductService {
   SimpleResponse saveProduct(ProductRequest request);
   int getBarcode();
}
