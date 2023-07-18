package com.example.baygo.db.custom;

import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryTypeResponse;
import com.example.baygo.db.dto.response.deliveryFactor.WarehouseCostResponse;
import com.example.baygo.db.model.enums.DeliveryType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class CustomDeliveryFactorImpl implements CustomDeliveryFactor {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page) {
        int offset = (page - 1) * size;
        String searchKeyword = "%" + keyword + "%";
        String deliveryFactorSql = """
                SELECT w.id AS w_id,
                       w.name AS w_name                
                FROM warehouses w
                """;
        if (keyword != null) {
            deliveryFactorSql += "WHERE w.name ILIKE ? ";
        }
        deliveryFactorSql += "LIMIT ? OFFSET ?";

        return jdbcTemplate.query(deliveryFactorSql, (resultSet, rowNum) -> {
            DeliveryFactorResponse deliveryFactorResponse = new DeliveryFactorResponse();
            deliveryFactorResponse.setWarehouseId(resultSet.getLong("w_id"));
            deliveryFactorResponse.setWarehouseName(resultSet.getString("w_name"));

            String deliveryTypesSql = """
                    SELECT DISTINCT s.delivery_type AS delivery_type
                    FROM supplies s
                    WHERE s.warehouse_id = ?
                    """;

            List<DeliveryTypeResponse> deliveryTypeResponses = jdbcTemplate.query(deliveryTypesSql, (innermostResultSet, d) -> {
                DeliveryTypeResponse deliveryTypeResponse = new DeliveryTypeResponse();
                deliveryTypeResponse.setDeliveryType(DeliveryType.valueOf(innermostResultSet.getString("delivery_type")));

                if (date == null) {
                    LocalDate currentDate = LocalDate.now();
                    for (int i = 0; i <= 6; i++) {
                        LocalDate futureDate = currentDate.plusDays(i);
                        WarehouseCostResponse warehouseCostResponse = warehouseCost(
                                deliveryFactorResponse.getWarehouseId(), deliveryTypeResponse.getDeliveryType(), futureDate);
                        deliveryTypeResponse.addWarehouseCost(warehouseCostResponse);
                    }
                } else {
                    WarehouseCostResponse warehouseCostResponse = warehouseCost(
                            deliveryFactorResponse.getWarehouseId(), deliveryTypeResponse.getDeliveryType(), date);
                    deliveryTypeResponse.setWarehouseCostResponses(List.of(warehouseCostResponse));
                }

                return deliveryTypeResponse;
            }, deliveryFactorResponse.getWarehouseId());

            deliveryFactorResponse.setDeliveryTypeResponses(deliveryTypeResponses);
            return deliveryFactorResponse;
        }, (keyword != null) ? new Object[]{searchKeyword, size, offset} : new Object[]{size, offset});

    }

    private WarehouseCostResponse warehouseCost(Long warehouseId, DeliveryType deliveryType, LocalDate date) {
        String sql = """
                    SELECT s.planned_date AS planned_date
                    FROM supplies s
                    WHERE s.warehouse_id = ? AND s.delivery_type = ? AND s.planned_date = ?
                """;

        List<WarehouseCostResponse> warehouseCostResponses = jdbcTemplate.query(sql, (innermostResult, d) -> {
            WarehouseCostResponse warehouseCostResponse = new WarehouseCostResponse();
            warehouseCostResponse.setDate(innermostResult.getDate("planned_date").toLocalDate());
            return warehouseCostResponse;
        }, warehouseId, deliveryType.name(), date);
        WarehouseCostResponse warehouseCostResponse = new WarehouseCostResponse();
        warehouseCostResponse.setDate(date);
        warehouseCostResponse.setWarehouseCost(BigDecimal.valueOf(warehouseCostResponses.size() / 10 * 2000L));
        warehouseCostResponse.setPlat(warehouseCostResponses.size() < 11 ? "Free" : "x" + warehouseCostResponses.size() / 10);
        return warehouseCostResponse;

    }
}
