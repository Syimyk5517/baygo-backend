package com.example.baygo.db.repository.customRepository.impl;

import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.repository.customRepository.SupplyCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SupplyCustomRepositoryImpl implements SupplyCustomRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<SupplyProductResponse> searchSupplyProducts(String keyWord, int page, int size) {
        String query = """
                
                """;
        jdbcTemplate.query(query, (resultSet) ->{

        });
        return null;
    }
}
