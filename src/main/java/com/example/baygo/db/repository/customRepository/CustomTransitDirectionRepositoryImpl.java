package com.example.baygo.db.repository.customRepository;

import com.example.baygo.dto.response.SupplyTransitDirectionResponse;
import com.example.baygo.db.repository.CustomTransitDirectionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomTransitDirectionRepositoryImpl implements CustomTransitDirectionRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<SupplyTransitDirectionResponse> getAllTransactions(String location) {

        String transitDirectionQuery= """
                SELECT
                    w.id AS warehouseId,
                    w.name AS name,
                    w.location AS location,
                    s.supply_cost AS supply_cost
                FROM warehouses w
                JOIN supplies s ON w.id = s.warehouse_id
                WHERE w.location = ?""";

        return jdbcTemplate.query(transitDirectionQuery, (resultSet, i)->
                new SupplyTransitDirectionResponse(
                        resultSet.getLong("warehouseId"),
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        resultSet.getBigDecimal("supply_cost")),location);
    }
}
