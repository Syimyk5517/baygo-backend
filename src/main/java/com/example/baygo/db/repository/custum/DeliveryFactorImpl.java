package com.example.baygo.db.repository.custum;

import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryTypeResponse;
import com.example.baygo.db.dto.response.deliveryFactor.WarehouseCostResponse;
import com.example.baygo.db.model.enums.DeliveryType;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@RequiredArgsConstructor
public class DeliveryFactorImpl implements DeliveryFactor {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page) {
        int offset = (page - 1) * size;
        String searchKeyword = "%" + keyword + "%";

        String deliveryFactorSql = """
                SELECT w.id AS warehouse_id,
                       w.warehouse_name AS warehouse_name            
                FROM warehouses w 
                WHERE w.warehouse_name ILIKE ?
                LIMIT ? OFFSET ?
                """;

        return jdbcTemplate.query(deliveryFactorSql, (resultSet, rowNum) -> {
            DeliveryFactorResponse deliveryFactorResponse = new DeliveryFactorResponse();
            deliveryFactorResponse.setWarehouseId(resultSet.getLong("warehouse_id"));
            deliveryFactorResponse.setWarehouseName(resultSet.getString("warehouse_name"));

            String deliveryTypesSql = """
                    SELECT DISTINCT s.supply_type AS supply_type
                    FROM supplies s
                    WHERE s.warehouse_id = ?
                    """;

            List<DeliveryTypeResponse> deliveryTypeResponses = jdbcTemplate.query(deliveryTypesSql, (innermostResultSet, d) -> {
                DeliveryTypeResponse deliveryTypeResponse = new DeliveryTypeResponse();
                deliveryTypeResponse.setDeliveryType(DeliveryType.valueOf(innermostResultSet.getString("supply_type")));

                if (date == null) {
                    LocalDate currentDate = LocalDate.now();
                    for (int i = 0; i <= 6; i++) {
                        LocalDate futureDate = currentDate.plusDays(i);
                        WarehouseCostResponse warehouseCostResponse = warehouseCost(
                                deliveryFactorResponse.getWarehouseId(), deliveryTypeResponse.getDeliveryType(), futureDate);
                        deliveryTypeResponse.setWarehouseCostResponses(List.of(warehouseCostResponse));
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
        }, searchKeyword, size, offset);

    }

    private WarehouseCostResponse warehouseCost(Long warehouseId, DeliveryType deliveryType, LocalDate date) {
        String sql = "SELECT COUNT(*) FROM supplies s WHERE s.warehouse_id = ? AND s.supply_type = ? AND s.created_at = ?";
        int supplyCount = jdbcTemplate.queryForObject(sql, Integer.class, warehouseId, deliveryType.name(), date);

        WarehouseCostResponse warehouseCostResponse = new WarehouseCostResponse();
        warehouseCostResponse.setDate(date);
        warehouseCostResponse.setWarehouseCost(BigDecimal.valueOf(supplyCount / 10 * 2000L));
        warehouseCostResponse.setPlat(supplyCount < 11 ? "Бесплатно" : "x" + supplyCount / 10);

        return warehouseCostResponse;
    }
}