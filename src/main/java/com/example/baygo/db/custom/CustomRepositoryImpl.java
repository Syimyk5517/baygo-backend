package com.example.baygo.db.custom;
import com.example.baygo.db.model.enums.Status;
import com.example.baygo.db.repository.OrderRepository;
import com.example.baygo.db.responses.AnalysisResponse;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;


@RequiredArgsConstructor
@Repository
public class CustomRepositoryImpl implements CustomOrderRepository {
    private final JdbcTemplate jdbcTemplate;
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
    WHERE (p.name LIKE ? OR u.first_name LIKE ?)
      AND o.status = ?
      AND p.seller_id = ?
    ORDER BY o.date_of_order DESC
    LIMIT ? OFFSET ?
""";

        List<OrderResponse> orderResponses = jdbcTemplate.query(
                query,
                (rs, rowNum) -> mapToOrderResponse(rs),
                "%" + keyword + "%",
                "%" + keyword + "%",
                status.name(),
                sellerId,
                size,
                offset
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
        WHERE (p.name LIKE ? OR u.first_name LIKE ?)
          AND o.status = ?
          AND p.seller_id = ?
    """;

        int totalCount = jdbcTemplate.queryForObject(
                countQuery,
                Integer.class,
                "%" + keyword + "%",
                "%" + keyword + "%",
                status.toString(),
                sellerId
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
    public AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId, String nameofTime, boolean isCommission) {
        String query = """
        SELECT 
            SUM(CASE WHEN date_of_order BETWEEN ? AND ? THEN total_price ELSE 0 END) AS amount_of_price,
            SUM(CASE WHEN date_of_order BETWEEN ? AND ? THEN commission ELSE 0 END) AS commission
        FROM 
            orders o
            JOIN supplies s ON o.status = s.status
        WHERE 
            warehouse_id = ?
            AND (
                (lower(?) = 'day' AND date_of_order = CAST(? AS DATE))
                OR (lower(?) = 'week' AND date_of_order >= date_trunc('week', CAST(? AS DATE)) AND date_of_order <= date_trunc('week', CAST(? AS DATE)) + interval '6 days')
                OR (lower(?) = 'month' AND date_of_order >= date_trunc('month', CAST(? AS DATE)) AND date_of_order <= date_trunc('month', CAST(? AS DATE)) + interval '1 month - 1 day')
            )
            AND commission = CAST(? AS BOOLEAN)
    """;

        Object[] params = {
                startDate, endDate,
                startDate, endDate,
                warehouseId,
                nameofTime, startDate,
                nameofTime, startDate, startDate,
                nameofTime, startDate, startDate,
                isCommission
        };

        BigDecimal amountOfPrice = jdbcTemplate.queryForObject(query, params, BigDecimal.class);
        BigDecimal commission = jdbcTemplate.queryForObject(query, params, BigDecimal.class);

        return new AnalysisResponse(amountOfPrice, commission);
    }}

