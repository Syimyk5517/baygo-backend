package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SupplyLandingPage;
import com.example.baygo.db.dto.response.SupplyTransitDirectionResponse;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.deliveryFactor.SupplyTypeResponse;
import com.example.baygo.db.dto.response.deliveryFactor.WarehouseCostResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.model.enums.SupplyType;
import com.example.baygo.repository.custom.SupplyCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SupplyCustomRepositoryImpl implements SupplyCustomRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public PaginationResponse<DeliveryFactorResponse> findAllDeliveryFactor(String keyword, LocalDate date, int size, int page) {
        int offset = (page - 1) * size;
        String deliveryFactorSql = """
                SELECT w.id AS w_id,
                       w.name AS w_name
                FROM warehouses w
                """;
        if (keyword != null) {
            deliveryFactorSql += "WHERE w.name iLIKE '%" + keyword + "%'";
        }

        int totalCount = totalCount(deliveryFactorSql, size);

        deliveryFactorSql += "LIMIT " + size + " OFFSET " + offset;

        List<DeliveryFactorResponse> deliveryFactorResponses = jdbcTemplate.query(deliveryFactorSql, (resultSet, rowNum) -> {
            DeliveryFactorResponse deliveryFactorResponse = new DeliveryFactorResponse();
            deliveryFactorResponse.setWarehouseId(resultSet.getLong("w_id"));
            deliveryFactorResponse.setWarehouseName(resultSet.getString("w_name"));

            String deliveryTypesSql = """
                    SELECT DISTINCT s.supply_type AS supply_type
                    FROM supplies s
                    WHERE s.warehouse_id = ?
                    """;

            List<SupplyTypeResponse> deliveryTypeResponses = jdbcTemplate.query(deliveryTypesSql, (innermostResultSet, d) -> {
                SupplyTypeResponse supplyTypeResponse = new SupplyTypeResponse();
                SupplyType supplyType = SupplyType.valueOf(innermostResultSet.getString("supply_type"));
                supplyTypeResponse.setSupplyType(supplyType.getDisplayName());

                if (date == null) {
                    LocalDate currentDate = LocalDate.now();
                    for (int i = 0; i <= 6; i++) {
                        LocalDate futureDate = currentDate.plusDays(i);
                        WarehouseCostResponse warehouseCostResponse = warehouseCost(
                                deliveryFactorResponse.getWarehouseId(), supplyType, futureDate);
                        supplyTypeResponse.addWarehouseCost(warehouseCostResponse);
                    }
                } else {
                    WarehouseCostResponse warehouseCostResponse = warehouseCost(
                            deliveryFactorResponse.getWarehouseId(), supplyType, date);
                    supplyTypeResponse.addWarehouseCost(warehouseCostResponse);
                }

                return supplyTypeResponse;
            }, deliveryFactorResponse.getWarehouseId());

            deliveryFactorResponse.setDeliveryTypeResponses(deliveryTypeResponses);
            return deliveryFactorResponse;
        });
        return PaginationResponse.<DeliveryFactorResponse>builder()
                .elements(deliveryFactorResponses)
                .currentPage(page)
                .totalPages(totalCount)
                .build();
    }

    @Override
    public List<SupplyTransitDirectionResponse> getAllTransitDirections(String transitWarehouse, String destinationWarehouse) {
        String transitDirectionQuery = """
                SELECT
                    w.id AS warehouseId,
                    w.name AS transit_warehouse,
                    w.location AS destination_warehouse,
                    w.transit_cost AS transit_cost
                FROM warehouses w
                """;
        if (transitWarehouse != null && !transitWarehouse.isEmpty()
                && destinationWarehouse != null && !destinationWarehouse.isEmpty()) {
            transitDirectionQuery += " WHERE w.name iLIKE '%" + transitWarehouse
                    + "%' AND w.location iLIKE '%" + destinationWarehouse + "%'";
        } else if (transitWarehouse != null && !transitWarehouse.isEmpty()) {
            transitDirectionQuery += " WHERE w.name iLIKE '%" + transitWarehouse + "%'";
        } else if (destinationWarehouse != null && !destinationWarehouse.isEmpty()) {
            transitDirectionQuery += " WHERE w.location iLIKE '%" + destinationWarehouse + "%'";
        }

        return jdbcTemplate.query(transitDirectionQuery, (resultSet, i) ->
                new SupplyTransitDirectionResponse(
                        resultSet.getLong("warehouseId"),
                        resultSet.getString("transit_warehouse"),
                        resultSet.getString("destination_warehouse"),
                        resultSet.getBigDecimal("transit_cost")));
    }

    @Override
    public List<SupplyLandingPage> getAllSupplyForLanding(Long sellerId) {
        String query = """
                    SELECT s.id, s.supply_number, s.created_at, w.location AS warehouse_location,
                          s.quantity_of_products, s.status
                    FROM supplies s
                    JOIN warehouses w ON s.warehouse_id = w.id
                    JOIN supply_products sp ON s.id = sp.supply_id
                    WHERE s.seller_id = ?
                    GROUP BY s.id, s.supply_number, s.created_at, w.location, s.status
                    ORDER BY s.created_at DESC
                    LIMIT 3
                """;

        return jdbcTemplate.query(query, (rs, rowNum) -> {
            Long supplyId = rs.getLong("id");
            String supplyNumber = rs.getString("supply_number");
            LocalDate createdAt = rs.getObject("created_at", LocalDate.class);
            String warehouseLocation = rs.getString("warehouse_location");
            int quantityOfProducts = rs.getInt("quantity_of_products");
            SupplyStatus status = SupplyStatus.valueOf(rs.getString("status"));

            return new SupplyLandingPage(supplyId, supplyNumber, createdAt, warehouseLocation, quantityOfProducts, status);
        }, sellerId);
    }

    @Override
    public List<WarehouseCostResponse> getAllWarehouseCostResponse(Long warehouseId, SupplyType supplyType) {
        LocalDate currenDate = LocalDate.now();
        List<WarehouseCostResponse> warehouseCostResponses = new ArrayList<>();
        for (int i = 0; i <= 13; i++) {
            LocalDate futureDate = currenDate.plusDays(i);
            WarehouseCostResponse warehouseCostResponse =
                    warehouseCost(warehouseId, supplyType, futureDate);
            warehouseCostResponses.add(warehouseCostResponse);
        }
        return warehouseCostResponses;
    }

    private WarehouseCostResponse warehouseCost(Long warehouseId, SupplyType deliveryType, LocalDate date) {
        String sql = """
                    SELECT s.planned_date AS planned_date
                    FROM supplies s
                    WHERE s.warehouse_id = ? AND s.supply_type = ? AND s.planned_date = ?
                """;

        List<WarehouseCostResponse> warehouseCostResponses = jdbcTemplate.query(sql, (innermostResult, d) -> {
            WarehouseCostResponse warehouseCostResponse = new WarehouseCostResponse();
            warehouseCostResponse.setDate(innermostResult.getDate("planned_date").toLocalDate());
            return warehouseCostResponse;
        }, warehouseId, deliveryType.name(), date);
        WarehouseCostResponse warehouseCostResponse = new WarehouseCostResponse();
        warehouseCostResponse.setDate(date);
        warehouseCostResponse.setWarehouseCost(BigDecimal.valueOf(warehouseCostResponses.size() / 10 * 2000L));
        warehouseCostResponse.setGoodsPayment(warehouseCostResponses.size() < 11 ? "Бесплатный" : "x" + warehouseCostResponses.size() / 10);
        return warehouseCostResponse;

    }

    private int totalCount(String sql, int size) {
        String countSql = "SELECT COUNT(*) FROM (" + sql + ") as count_query";
        Integer countResult = jdbcTemplate.queryForObject(countSql, Integer.class);
        int count = countResult != null ? countResult : 0;
        return (int) Math.ceil((double) count / size);
    }

}
