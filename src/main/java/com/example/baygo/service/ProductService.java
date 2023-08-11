package com.example.baygo.service;

import com.example.baygo.db.dto.request.ProductRequest;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductBuyerResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SimpleResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface ProductService {
    SimpleResponse saveProduct(ProductRequest request);

    int getBarcode();
    PaginationResponse<ProductResponseForSeller> findAll(String status, String keyWord, int page, int size);

    PaginationResponse<ProductBuyerResponse> getAllProductsBuyer(String keyWord,
                                                                 List<String> sizes,
                                                                 List<String> compositions,
                                                                 List<String> brands,
                                                                 BigDecimal minPrice,
                                                                 BigDecimal maxPrice,
                                                                 List<String> colors,
                                                                 String filterBy,
                                                                 String sortBy,
                                                                 int page,
                                                                 int pageSize);
}