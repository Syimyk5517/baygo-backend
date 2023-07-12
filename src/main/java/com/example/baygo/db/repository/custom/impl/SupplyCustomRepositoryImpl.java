package com.example.baygo.db.repository.custom.impl;

import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.repository.custom.SupplyCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SupplyCustomRepositoryImpl implements SupplyCustomRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<SuppliesResponse> getAllSuppliesOfSeller(Long currentUserId, String supplyNumber, SupplyStatus status, int page, int pageSize) {

        String sql = """
                SELECT s.id , s.supply_number, s.supply_type, s.created_at, s.quantity_of_products, s.accepted_products,
                s.commission, s.supply_cost, s.planned_date, s.actual_date, u.phone_number, s.status
                FROM supplies s
                JOIN users u ON s.seller_id = u.id
                WHERE u.id =""" + currentUserId;

        if (supplyNumber != null && !supplyNumber.isEmpty()) {
            sql += " AND s.supply_number LIKE '" + supplyNumber + "%'";
        }

        if (status != null && status.describeConstable().isPresent()) {
            sql += " AND s.status = '" + status + "'";
        }

        sql += " ORDER BY s.id ";

        int offset = (page - 1) * pageSize;
        sql += " LIMIT " + pageSize + " OFFSET " + offset;

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                SuppliesResponse.builder()
                        .id(rs.getLong("id"))
                        .supplyNumber(rs.getString("supply_number"))
                        .supplyType(rs.getString("supply_type"))
                        .createdAt(rs.getDate("created_at").toLocalDate())
                        .quantityOfProducts(rs.getInt("quantity_of_products"))
                        .acceptedProducts(rs.getInt("accepted_products"))
                        .commission(rs.getInt("commission"))
                        .supplyCost(rs.getBigDecimal("supply_cost"))
                        .plannedDate(rs.getDate("planned_date").toLocalDate())
                        .actualDate(rs.getDate("actual_date").toLocalDate())
                        .user(rs.getString("phone_number"))
                        .status(SupplyStatus.valueOf(rs.getString("status")))
                        .build());
    }
}
