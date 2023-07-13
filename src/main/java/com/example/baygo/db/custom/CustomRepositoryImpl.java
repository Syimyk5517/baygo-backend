package com.example.baygo.db.custom;
import com.example.baygo.db.model.enums.Status;
import com.example.baygo.db.repository.OrderRepository;
import com.example.baygo.db.responses.AnalysisResponse;
import com.example.baygo.db.responses.OrderResponse;
import com.example.baygo.db.responses.PaginationResponse;
import com.example.baygo.db.responses.SimpleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RequiredArgsConstructor
@Repository
public class CustomRepositoryImpl implements CustomOrderRepository {
    private final JdbcTemplate jdbcTemplate;
    private final OrderRepository orderRepository;

    @Override
    public PaginationResponse<OrderResponse> getAll(int page, int size, String keyword, Status status, Long sellerId) {
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
                    WHERE (p.name LIKE ? OR u.first_name LIKE ?) AND o.status = ? And p.seller_id=?
                    ORDER BY o.date_of_order DESC
                    LIMIT ? OFFSET ?
                """;

        List<OrderResponse> orderResponses = jdbcTemplate.query(query, (rs, rowNum) -> mapToOrderResponse(rs), "%" + keyword + "%", "%" + keyword + "%", status.name(), sellerId, size, offset);

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

    @Override
    public SimpleResponse deleteById(Long orderId, Long sellerId) {
        if (!orderRepository.existsById(orderId)) {
            return SimpleResponse.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message(String.format("Заказ с этим %s номером не найден", orderId))
                    .build();

        }
        String deleteBuyersQuery = "DELETE FROM buyers WHERE order_id = ?";
        String deleteSubProductsQuery = "DELETE FROM sub_products WHERE id IN (SELECT id FROM buyers WHERE order_id = ?)";
        String deleteProductsQuery = "DELETE FROM products WHERE id IN (SELECT product_id FROM sub_products WHERE id IN (SELECT id FROM buyers WHERE order_id = ?))";
        String deleteUsersQuery = "DELETE FROM users WHERE id IN (SELECT user_id FROM buyers WHERE order_id = ?)";
        String deleteOrdersQuery = "DELETE FROM orders WHERE id = ?";

        jdbcTemplate.update(deleteBuyersQuery, orderId);
        jdbcTemplate.update(deleteSubProductsQuery, orderId);
        jdbcTemplate.update(deleteProductsQuery, orderId);
        jdbcTemplate.update(deleteUsersQuery, orderId);
        int rowsAffected = jdbcTemplate.update(deleteOrdersQuery, orderId);
        if (rowsAffected > 0) {
            return SimpleResponse.builder()
                    .message(String.format("Заказ %s успешно удален", orderId))
                    .status(HttpStatus.OK)
                    .build();
        } else {
            return SimpleResponse.builder()
                    .message("Не удалось удалить заказ")
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
    }

    @Override
    public AnalysisResponse getWeeklyAnalysis(Date startDate, Date endDate, Long warehouseId,String nameOfTime,boolean commission) {
        String query = """
                SELECT SUM(o.total_price) AS total_order_sum, SUM(s.commission) AS total_commission
                FROM orders o
                         join supplies s on o.id = s.id
                WHERE warehouse_id = ?
                  AND date_of_order BETWEEN ? AND ?
                  AND commission = ?
                  AND total_price > ?
                  AND commission = ?
                """;

        Map<String, Object> result = jdbcTemplate.queryForMap(query, warehouseId, startDate, endDate, nameOfTime, commission);
        BigDecimal totalOrderSum = (BigDecimal) result.get("total_order_sum");
        BigDecimal totalCommission = (BigDecimal) result.get("total_commission");
        return new AnalysisResponse(totalOrderSum, totalCommission);
    }


}



