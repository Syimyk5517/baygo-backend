package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductBuyerResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CustomProductRepository {
    PaginationResponse<ProductResponseForSeller> getAll(Long sellerId, String status, String keyWord, int page, int size);

    PaginationResponse<ProductBuyerResponse> getAllProductsBuyer(String keyWord, List<String> sizes, List<String> compositions, List<String> brands, BigDecimal minPrice, BigDecimal maxPrice, List<String> colors, String sortBy, int page, int pageSize);
}
