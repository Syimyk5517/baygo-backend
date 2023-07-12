package com.example.baygo.repository.custom.impl;

import com.example.baygo.dto.response.TransitDirectionResponse;
import com.example.baygo.repository.custom.CustomTransitDirectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomTransitDirectionRepositoryImpl implements CustomTransitDirectionRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<TransitDirectionResponse> getAllTransactions(String name) {

        String transitDirectionQuery= """
                select 
                w.id as id,
                w.name as name,
                w.location as location
                from warehouses w join  supplies s on s.supply_cost where id = w.id and w.name
                """;


        return jdbcTemplate.query(transitDirectionQuery, (resultSet, i)->
                new TransitDirectionResponse(
                        resultSet.getLong("warehouseId"),
                        resultSet.getInt("transitWareHouse"),
                        resultSet.getBigDecimal("supplyCost")));
    }
}
