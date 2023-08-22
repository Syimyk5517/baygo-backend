package com.example.baygo.service;

import com.example.baygo.db.dto.request.SellerProductRequest;
import com.example.baygo.db.dto.response.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface ProductService {
    SimpleResponse saveProduct(SellerProductRequest request);

    PaginationResponse<ProductResponseForSeller> findAll(Long categoryId, String keyWord, String sortBy, boolean ascending, int page, int size);


    PaginationResponseWithQuantity<ProductBuyerResponse> getAllProductsBuyer(String keyWord,
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

    ProductGetByIdResponse getById(Long id);

    SimpleResponse updateProduct(SellerProductRequest request, Long id);

}
