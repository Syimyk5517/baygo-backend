package com.example.baygo.repository.custom;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomProductRepository {
    PaginationResponse<ProductResponseForSeller> getAll(Long sellerId, String status, String keyWord, int page, int size);
}
