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
    public List<TransitDirectionResponse> getAllTransactions(String location) {

        String transitDirectionQuery= """
                SELECT
                    w.id AS id,
                    w.transit_warehouse AS transit_warehouse,
                    w.location AS location,
                    s.supply_cost AS supply_cost
                FROM warehouses w
                JOIN supplies s ON w.id = s.warehouse_id
                WHERE w.location = ?""";

        return jdbcTemplate.query(transitDirectionQuery, (resultSet, i)->
                new TransitDirectionResponse(
                        resultSet.getLong("warehouseId"),
                        resultSet.getInt("transit_wareHouse"),
                        resultSet.getString("location"),
                        resultSet.getBigDecimal("supply_cost")),location);
    }
}
