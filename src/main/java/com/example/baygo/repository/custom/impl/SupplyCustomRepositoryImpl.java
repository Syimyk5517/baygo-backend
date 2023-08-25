package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.dto.response.SupplyLandingPage;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.dto.response.deliveryFactor.DeliveryFactorResponse;
import com.example.baygo.db.dto.response.deliveryFactor.SupplyTypeResponse;
import com.example.baygo.db.dto.response.deliveryFactor.WarehouseCostResponse;
import com.example.baygo.db.dto.response.supply.SupplySellerProductResponse;
import com.example.baygo.db.model.User;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.model.enums.SupplyType;
import com.example.baygo.repository.custom.SupplyCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class SupplyCustomRepositoryImpl implements SupplyCustomRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final JwtService jwtService;

    @Override
    public PaginationResponse<SupplyProductResponse> getSupplyProducts(Long sellerId, Long supplyId, String keyWord, int page, int size) {
        String query = """
                select
                     p.id as product_id,
                     sp.id as sub_product_id,
                     s.id as size_id,
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
            query += " AND (cast(s.barcode as text) iLIKE :keyWord OR p.brand iLIKE :keyWord OR p.name iLIKE :keyWord OR sp.color iLIKE :keyWord)";
            params.addValue("keyWord", "%" + keyWord + "%");
        }

        String countSql = "SELECT COUNT(*) FROM (" + query + ") AS count_query";
        int count = namedParameterJdbcTemplate.query(countSql, params, (resultSet, i) ->
                resultSet.getInt("count")
        ).stream().findFirst().orElseThrow();

        int totalPage = (int) Math.ceil((double) count / size);
        int offset = (page - 1) * size;
        query += " LIMIT :limit OFFSET :offset";
        params.addValue("limit", size);
        params.addValue("offset", offset);

        List<SupplyProductResponse> supplyProductResponses = namedParameterJdbcTemplate.query(query, params, (resultSet, i) ->
                new SupplyProductResponse(
                        resultSet.getLong("product_id"),
                        resultSet.getLong("sub_product_id"),
                        resultSet.getLong("size_id"),
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
    public PaginationResponse<SupplySellerProductResponse> getSellerProducts(
            Integer searchWithBarcode, String category, String brand, int page, int pageSize) {
        User user = jwtService.getAuthenticate();

        String sql = """
                SELECT s.id as size_id, (SELECT i.images FROM sub_product_images i right join sub_products sp on sp.id = i.sub_product_id LIMIT 1) as image,
                    sc.name as category, s.barcode as barcode, seller.vendor_number as vendor_number, prod.brand as brand, s.size as size, sub.color as colour
                FROM products prod
                    JOIN sub_products sub ON prod.id = sub.product_id
                    LEFT JOIN sub_categories sc ON prod.sub_category_id = sc.id
                    LEFT JOIN sizes s on sub.id = s.sub_product_id
                    LEFT JOIN sellers seller on prod.seller_id = seller.id
                WHERE prod.seller_id = ?
                """;

        List<Object> params = new ArrayList<>();
        params.add(user.getSeller().getId());
        if (searchWithBarcode != null) {
            sql += " AND CAST(s.barcode AS TEXT) LIKE ?";
            params.add(searchWithBarcode + "%");
        }

        if (category != null) {
            sql += " AND sc.name=?";
            params.add(category);
        }

        if (brand != null) {
            sql += " AND prod.brand=?";
            params.add(brand);
        }

        String countProducts = "SELECT COUNT(*) FROM (" + sql + ") AS count_query";
        List<Integer> countList = jdbcTemplate.queryForList(countProducts, Integer.class, params.toArray());
        int count = countList.get(0);

        int totalPage = (int) Math.ceil((double) count / pageSize);

        sql += " LIMIT ? OFFSET ?";
        int offset = (page - 1) * pageSize;
        params.add(pageSize);
        params.add(offset);

        List<SupplySellerProductResponse> supplyResponseList = jdbcTemplate.query(
                sql,
                params.toArray(),
                (resultSet, item) -> SupplySellerProductResponse.builder()
                        .productSizeId(resultSet.getLong("size_id"))
                        .imageProduct(resultSet.getString("image"))
                        .categoryProduct(resultSet.getString("category"))
                        .barcodeProduct(resultSet.getInt("barcode"))
                        .vendorCodeSeller(resultSet.getString("vendor_number"))
                        .brandProduct(resultSet.getString("brand"))
                        .sizeProduct(resultSet.getString("size"))
                        .colorProduct(resultSet.getString("colour"))
                        .build()
        );

        return PaginationResponse.<SupplySellerProductResponse>builder()
                .totalPages(totalPage)
                .currentPage(page)
                .elements(supplyResponseList)
                .build();
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
