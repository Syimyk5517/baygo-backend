package com.example.baygo.db.repository.custom.impl;

import com.example.baygo.db.dto.response.AnalysisResponse;
import com.example.baygo.db.dto.response.OrderResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.model.enums.Status;
import com.example.baygo.db.repository.custom.CustomOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Repository
public class CustomOrderRepositoryImpl implements CustomOrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public PaginationResponse<OrderResponse> getAll(int page, int size, String keyword, Status status, Long sellerId) {
        String baseQuery = """
                   SELECT o.id as orderId, s.barcode, u.first_name, sp.price, p.name as product_name, o.date_of_order, o.status
                   FROM orders o 
                   join orders_sizes os on o.id = os.order_id 
                   join sizes s on s.id = os.size_id
                   join sub_products sp on sp.id = s.sub_product_id 
                   join products p on p.id = sp.product_id
                   join buyers b ON o.buyer_id = b.id
                   join users u ON b.user_id = u.id
                   WHERE p.seller_id = :sellerId
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("sellerId", sellerId);

        if (keyword != null && !keyword.isEmpty()) {
            baseQuery += " AND (p.name ILIKE :keyword OR u.first_name ILIKE :keyword)";
            params.addValue("keyword", "%" + keyword + "%");
        }

        if (status != null) {
            baseQuery += " AND o.status = :status";
            params.addValue("status", status.name());
        }

        String countQuery = "SELECT COUNT(*) FROM (" + baseQuery + ") AS count_query";

        int totalCount = namedParameterJdbcTemplate.queryForObject(
                countQuery,
                params,
                Integer.class
        );
        int totalPage = (int) Math.ceil((double) totalCount / size);

        String query = baseQuery + " ORDER BY o.date_of_order DESC LIMIT :size OFFSET :offset";
        int offset = (page - 1) * size;
        params.addValue("size", size);
        params.addValue("offset", offset);

        List<OrderResponse> orderResponses = namedParameterJdbcTemplate.query(
                query,
                params,
                (rs, rowNum) -> new OrderResponse(
                        rs.getLong("orderId"),
                        rs.getInt("barcode"),
                        rs.getString("first_name"),
                        rs.getBigDecimal("price"),
                        rs.getString("product_name"),
                        rs.getObject("date_of_order", LocalDate.class),
                        Status.valueOf(rs.getString("status"))
                )
        );

        return PaginationResponse.<OrderResponse>builder()
                .elements(orderResponses)
                .currentPage(page)
                .totalPages(totalPage)
                .build();
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
