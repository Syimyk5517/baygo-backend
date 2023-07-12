package com.example.baygo.db.custom;

import com.example.baygo.db.enums.StatusOrder;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
@RequiredArgsConstructor
@Repository
public class CustomRepositoryImpl implements CustomOrderRepository {
    private final JdbcTemplate jdbcTemplate;

    public PaginationResponse<OrderResponse> getAll(int page, int size, String keyword, StatusOrder status) {
        int offset = (page - 1) * size;
        String query = """
       SELECT p.barcode,
              u.first_name,
              p.name,
              sp.price,
              o.date_of_order,
              o.status
               FROM orders o join buyers b on o.id = b.order_id
                join sub_products sp on sp.id= b.order_id
                   join products p on p.id = sp.product_id
               join users u on b.user_id = u.id
        WHERE (p.name LIKE ? OR u.first_name LIKE ?) AND o.status = ?
        ORDER BY o.date_of_order DESC
        LIMIT ? OFFSET ?
    """;

        List<OrderResponse> orderResponses = jdbcTemplate.query(query, (rs, rowNum) -> mapToOrderResponse(rs), "%" + keyword + "%", "%" + keyword + "%", status.toString(), size, offset);

        String countQuery = """
        SELECT COUNT(o.id)
        FROM orders o
        JOIN buyers b ON o.id = b.order_id
        JOIN sub_products sp ON sp.id = b.order_id
        JOIN products p ON p.id = sp.product_id
        JOIN sellers s ON s.id = p.seller_id
        JOIN users u ON u.id = s.user_id
        WHERE (p.name LIKE ? OR u.first_name LIKE ?) AND o.status = ?
    """;

        int totalCount = jdbcTemplate.queryForObject(countQuery, Integer.class, "%" + keyword + "%", "%" + keyword + "%", status.toString());

        return new PaginationResponse<>(orderResponses, page, size, totalCount);
    }

    private OrderResponse mapToOrderResponse(ResultSet rs) throws SQLException {
        Long orderId = rs.getLong("order_id");
        String barcode = rs.getString("barcode");
        String firstName = rs.getString("first_name");
        String productPrice = rs.getString("price");
        String productName = rs.getString("name");
        LocalDate orderDate = rs.getDate("date_of_order").toLocalDate();
        String status = rs.getString("status");

        return new OrderResponse(orderId, barcode, firstName, productPrice, productName, orderDate, status);
    }
}