package com.example.baygo.db.repository.custom.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SuppliesResponse;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.model.enums.SupplyStatus;
import com.example.baygo.db.repository.custom.SupplyCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SupplyCustomRepositoryImpl implements SupplyCustomRepository {
    private final JdbcTemplate jdbcTemplate;

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

        String countSql = "SELECT COUNT(*) FROM (" + sql + ") as count_query";
        Integer countResult = jdbcTemplate.queryForObject(countSql, Integer.class);
        int count = countResult != null ? countResult : 0;
        int totalCount = (int) Math.ceil((double) count / pageSize);

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
                .page(page)
                .totalPages(totalCount)
                .build();
    }

    @Override
    public PaginationResponse<SupplyProductResponse> searchSupplyProducts(Long id, String keyWord, int page, int size) {
        String query = """
                select (
                        select 
                               spi2.images 
                        from sub_product_images spi2 
                        where spi2.sub_product_id=sp.id )                   as image,
                                       s.barcode                            as barcode,
                                       s.quantity                           as quantity,
                                       p.name                               as name,
                                       s2.vendor_number                     as vendor_number,
                                       p.brand                              as brand_name,
                                       s.size                               as sizes,
                                       sp.color                             as color
                                 from sub_products sp
                                         join sub_product_images spi on sp.id = spi.sub_product_id
                                         join sizes s on sp.id = s.sub_product_id
                                         join products p on p.id = sp.product_id
                                         join sellers s2 on p.seller_id = s2.id
                                         join supplies s3 on p.seller_id = s3.seller_id
                                 where s3.id = ?
                                   or p.brand iLIKE ?
                                   or p.name iLIKE ?
                                   or sp.color iLIKE ?
                                """;
        String countSql = "SELECT COUNT(*) FROM (" + query + ") AS count_query";
        Integer count = jdbcTemplate.query(countSql, (resultSet, i) -> resultSet.getInt("count"),
                id,
                "%" + keyWord + "%",
                "%" + keyWord + "%",
                "%" + keyWord + "%").stream().findFirst().orElseThrow();
        int totalPage = (int) Math.ceil((double) count / size);
        int offset = (page - 1) * size;
        query = query + " LIMIT ? OFFSET ?";
        List<SupplyProductResponse> query1 = jdbcTemplate.query(query, (resultSet, i) ->
                        new SupplyProductResponse(
                                resultSet.getString("image"),
                                resultSet.getInt("barcode"),
                                resultSet.getInt("quantity"),
                                resultSet.getString("name"),
                                resultSet.getString("vendor_number"),
                                resultSet.getString("brand_name"),
                                resultSet.getString("sizes"),
                                resultSet.getString("color")),
                id,
                "%" + keyWord + "%",
                "%" + keyWord + "%",
                "%" + keyWord + "%"
                ,
                size,
                offset
        );
        return PaginationResponse
                .<SupplyProductResponse>builder()
                .page(page)
                .totalPages(totalPage)
                .quantityOfElements(count)
                .elements(query1)
                .build();
    }
}
