package com.example.baygo.db.repository.custom;

import com.example.baygo.db.dto.response.ProductResponseForSeller;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomProductRepository {
    Page<ProductResponseForSeller> getAll(Pageable pageable, long sellerId, String status, String keyWord);
}
