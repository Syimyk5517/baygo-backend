package com.example.baygo.db.custom;

import com.example.baygo.db.model.enums.Status;
import com.example.baygo.db.responses.AnalysisResponse;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Repository
public class CustomRepositoryImpl implements CustomOrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public PaginationResponse<OrderResponse> getAll(int page, int size, String keyword, Status status, Long sellerId) {
        int offset = (page - 1) * size;
        String query = """
                SELECT o.id as orderId, s.barcode, u.first_name, sp.price, p.name as product_name, o.date_of_order, o.status
                FROM orders o
                         JOIN orders_sub_products_size osps ON o.id = osps.order_id
                         JOIN sub_products sp ON osps.sub_products_size_id = sp.id
                         JOIN products p ON sp.product_id = p.id
                         JOIN sizes s ON sp.id = s.sub_product_id
                         JOIN buyers b ON o.buyer_id = b.id
                         JOIN users u ON b.user_id = u.id
                WHERE p.seller_id = :sellerId
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("sellerId", sellerId);

        if (keyword != null && !keyword.isEmpty()) {
            query += "AND (p.name iLIKE :keyword OR u.first_name iLIKE :keyword) ";
            params.addValue("keyword", "%" + keyword + "%");
        }

        if (status != null) {
            query += "AND o.status = :status ";
            params.addValue("status", status.name());
        }

        query += """
                ORDER BY o.date_of_order DESC
                LIMIT :size OFFSET :offset
                """;

        params.addValue("size", size);
        params.addValue("offset", offset);

        List<OrderResponse> orderResponses = namedParameterJdbcTemplate.query(
                query,
                params,
                (rs, rowNum) -> mapToOrderResponse(rs)
        );

        String countQuery = """
                SELECT COUNT(o.id)
                FROM orders o
                         JOIN orders_sub_products_size osps ON o.id = osps.order_id
                         JOIN sub_products sp ON osps.sub_products_size_id = sp.id
                         JOIN products p ON sp.product_id = p.id
                         JOIN sizes s ON sp.id = s.sub_product_id
                         JOIN buyers b ON o.buyer_id = b.id
                         JOIN users u ON b.user_id = u.id
                WHERE p.seller_id = :sellerId
                """;

        if (keyword != null && !keyword.isEmpty()) {
            countQuery += "AND (p.name iLIKE :keyword OR u.first_name iLIKE :keyword) ";
        }

        if (status != null) {
            countQuery += "AND o.status = :status ";
        }

        int totalCount = namedParameterJdbcTemplate.queryForObject(
                countQuery,
                params,
                Integer.class
        );

        return new PaginationResponse<>(orderResponses, page, size, totalCount);
    }

    private OrderResponse mapToOrderResponse(ResultSet rs) throws SQLException {
        Long orderId = rs.getLong("orderId");
        int barcode = rs.getInt("barcode");
        String firstName = rs.getString("first_name");
        BigDecimal productPrice = rs.getBigDecimal("price");
        String productName = rs.getString("product_name");
        LocalDate orderDate = rs.getDate("date_of_order").toLocalDate();
        Status status = Status.valueOf(rs.getString("status"));

        return new OrderResponse(orderId, barcode, firstName, productPrice, productName, orderDate, status);
    }

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
}
