package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.orders.AnalysisResponse;
import com.example.baygo.db.dto.response.orders.OrderWareHouseResponse;
import com.example.baygo.repository.custom.CustomOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Repository
public class CustomOrderRepositoryImpl implements CustomOrderRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameofTime, Long sellerId) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT ");
        queryBuilder.append("SUM(CASE WHEN date_of_order BETWEEN ? AND ? THEN total_price ELSE 0 END) AS amount_of_price, ");
        queryBuilder.append("SUM(CASE WHEN date_of_order BETWEEN ? AND ? THEN \"commission\" ELSE 0 END) AS \"commission\" ");
        queryBuilder.append("FROM orders o ");
        queryBuilder.append("JOIN supplies s ON o.status = s.status ");
        queryBuilder.append("WHERE warehouse_id = ? ");
        queryBuilder.append("AND seller_id = ? ");

        List<Object> params = new ArrayList<>();
        params.add(startDate);
        params.add(endDate);
        params.add(startDate);
        params.add(endDate);
        params.add(warehouseId);
        params.add(sellerId);

        if (nameofTime != null && !nameofTime.isEmpty()) {
            queryBuilder.append("AND (");
            if (nameofTime.equalsIgnoreCase("day")) {
                queryBuilder.append("lower(?) = 'day' AND date_of_order = CAST(? AS DATE) ");
                params.add(nameofTime);
                params.add(startDate);
            } else if (nameofTime.equalsIgnoreCase("week")) {
                queryBuilder.append("lower(?) = 'week' AND date_of_order >= date_trunc('week', CAST(? AS DATE)) AND date_of_order <= date_trunc('week', CAST(? AS DATE)) + interval '6 days' ");
                params.add(nameofTime);
                params.add(startDate);
                params.add(startDate);
            } else if (nameofTime.equalsIgnoreCase("month")) {
                queryBuilder.append("lower(?) = 'month' AND date_of_order >= date_trunc('month', CAST(? AS DATE)) AND date_of_order <= date_trunc('month', CAST(? AS DATE)) + interval '1 month - 1 day' ");
                params.add(nameofTime);
                params.add(startDate);
                params.add(startDate);
            }
            queryBuilder.append(") ");
        }

        queryBuilder.append("AND \"commission\" <> 0 ");

        String query = queryBuilder.toString();

        Map<String, Object> result = jdbcTemplate.queryForMap(query, params.toArray());

        BigDecimal amountOfPrice = (BigDecimal) result.get("amount_of_price");
        BigDecimal commission = (BigDecimal) result.get("commission");

        return new AnalysisResponse(amountOfPrice, commission);
    }


    @Override
    public List<OrderWareHouseResponse> getAllOrders(Long sellerId) {

        String query = """
                    SELECT location,
                           ROUND(COUNT(DISTINCT o.id) * 100.0 / total_orders.total_count, 2) AS percentage
                    FROM warehouses w
                    JOIN supplies s ON w.id = s.warehouse_id
                    JOIN supply_products sp ON s.id = sp.supply_id
                    JOIN orders_sizes os ON sp.size_id = os.size_id
                    JOIN orders o ON o.id = os.order_id
                    LEFT JOIN (
                        SELECT warehouse_id, COUNT(DISTINCT o.id) AS total_count
                        FROM supplies s
                        JOIN supply_products sp ON s.id = sp.supply_id
                        JOIN orders_sizes os ON sp.size_id = os.size_id
                        JOIN orders o ON o.id = os.order_id
                        WHERE s.seller_id = ?
                        GROUP BY warehouse_id
                    ) total_orders ON w.id = total_orders.warehouse_id
                    WHERE s.seller_id = ?
                    GROUP BY location, total_orders.total_count, o.buyer_id;
                """;
        return jdbcTemplate.query(query, (rs, rowNum) -> {
            String location = rs.getString("location");
            double percentage = rs.getDouble("percentage");
            return new OrderWareHouseResponse(location, percentage);
        }, sellerId, sellerId);
    }
}



