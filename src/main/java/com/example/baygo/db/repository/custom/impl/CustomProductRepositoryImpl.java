package com.example.baygo.db.repository.custom.impl;

import com.example.baygo.db.dto.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.repository.custom.CustomProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public PaginationResponse<ProductResponseForSeller> getAllProductOfSeller(int page, int size) {
        return null;
    }

    @Override
    public Page<ProductResponseForSeller> getAllProductOfSeller(PageRequest pageRequest) {
        ProductResponseForSeller product = new ProductResponseForSeller();
        String sql = """
                select
                """;
        return null;
    }
}
