package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductGetByIdResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomProductRepository {
    PaginationResponse<ProductResponseForSeller> getAll(Long sellerId, Long categoryId, String keyWord, String sortBy, boolean ascending, int page, int size);
    ProductGetByIdResponse getById(Long id);
}
