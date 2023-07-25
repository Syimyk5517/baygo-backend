package com.example.baygo.db.repository.custom.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryTypeResponse;
import com.example.baygo.db.dto.response.deliveryFactor.WarehouseCostResponse;
import com.example.baygo.db.model.enums.SupplyType;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.repository.custom.SupplyCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SupplyCustomRepositoryImpl implements SupplyCustomRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public PaginationResponse<SuppliesResponse> getAllSuppliesOfSeller(Long currentUserId, String supplyNumber, SupplyStatus status, int page, int pageSize) {

        String sql = """
                SELECT s.id , s.supply_number, s.supply_type, s.created_at, s.quantity_of_products, s.accepted_products,
                s.commission, s.supply_cost, s.planned_date, s.actual_date, u.phone_number, s.status
                FROM supplies s
                JOIN sellers s2 on s.seller_id = s2.id
                JOIN users u on s2.user_id = u.id
                WHERE u.id =""" + currentUserId;

        if (supplyNumber != null && !supplyNumber.isEmpty()) {
            sql += " AND s.supply_number iLIKE '" + supplyNumber + "%'";
        }

        if (status != null && status.describeConstable().isPresent()) {
            sql += " AND s.status = '" + status + "'";
        }

        sql += " ORDER BY s.created_at DESC ";

        int totalCount = totalCount(sql, pageSize);

        int offset = (page - 1) * pageSize;
        sql += " LIMIT " + pageSize + " OFFSET " + offset;

        List<SuppliesResponse> suppliesResponses = jdbcTemplate.query(sql, (rs, rowNum) ->
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
        return PaginationResponse.<SuppliesResponse>builder()
                .elements(suppliesResponses)
                .currentPage(page)
                .totalPages(totalCount)
                .build();
    }

    @Override
    public PaginationResponse<SupplyProductResponse> getSupplyProducts(Long sellerId, Long supplyId, String keyWord, int page, int size) {
        String query = """
                select
                     (select spi.images
                     from sub_product_images spi
                     where spi.sub_product_id = sp.id
                     limit 1)                           as image,
                     s.barcode                          as barcode,
                     splp.quantity                      as quantity,
                     p.name                             as name,
                     sel.vendor_number                  as vendor_number,
                     p.brand                            as brand_name,
                     s.size                             as sizes,
                     sp.color                           as color
                from supplies spl
                     join supply_products splp on spl.id = splp.supply_id
                     join sizes s on splp.size_id = s.id
                     join sub_products sp on s.sub_product_id = sp.id
                     join products p on p.id = sp.product_id
                     join sellers sel on spl.seller_id = sel.id
                where sel.id = :sellerId and spl.id = :supplyId
                """;

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("sellerId", sellerId);
        params.addValue("supplyId", supplyId);
        if (keyWord != null && !keyWord.isEmpty()) {
            query += " AND (s.barcode iLIKE :keyWord OR p.brand iLIKE :keyWord OR p.name iLIKE :keyWord OR sp.color iLIKE :keyWord)";
            params.addValue("keyWord", "%" + keyWord + "%");
        }

        String countSql = "SELECT COUNT(*) FROM (" + query + ") AS count_query";
        int count = namedParameterJdbcTemplate.query(countSql, params,(resultSet, i) ->
                        resultSet.getInt("count")
             ).stream().findFirst().orElseThrow();

        int totalPage = (int) Math.ceil((double) count / size);
        int offset = (page - 1) * size;
        query += " LIMIT :limit OFFSET :offset";
        params.addValue("limit", size);
        params.addValue("offset", offset);

        List<SupplyProductResponse> supplyProductResponses = namedParameterJdbcTemplate.query(query, params, (resultSet, i) ->
                        new SupplyProductResponse(
                                resultSet.getString("image"),
                                resultSet.getInt("barcode"),
                                resultSet.getInt("quantity"),
                                resultSet.getString("name"),
                                resultSet.getString("vendor_number"),
                                resultSet.getString("brand_name"),
                                resultSet.getString("sizes"),
                                resultSet.getString("color"))
                        );
        return PaginationResponse.<SupplyProductResponse>builder()
                .elements(supplyProductResponses)
                .currentPage(page)
                .totalPages(totalPage)
                .build();
    }

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
                    SELECT DISTINCT s.delivery_type AS delivery_type
                    FROM supplies s
                    WHERE s.warehouse_id = ?
                    """;

            List<DeliveryTypeResponse> deliveryTypeResponses = jdbcTemplate.query(deliveryTypesSql, (innermostResultSet, d) -> {
                DeliveryTypeResponse deliveryTypeResponse = new DeliveryTypeResponse();
                deliveryTypeResponse.setDeliveryType(SupplyType.valueOf(innermostResultSet.getString("delivery_type")));

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
                    deliveryTypeResponse.addWarehouseCost(warehouseCostResponse);
                }

                return deliveryTypeResponse;
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

    private WarehouseCostResponse warehouseCost(Long warehouseId, SupplyType deliveryType, LocalDate date) {
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