package com.example.baygo.db.repository.customRepository.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.SupplyProductResponse;
import com.example.baygo.db.repository.customRepository.SupplyCustomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SupplyCustomRepositoryImpl implements SupplyCustomRepository {


    private final JdbcTemplate jdbcTemplate;

    @Override
    public PaginationResponse<SupplyProductResponse> searchSupplyProducts(String keyWord, int page, int size) {
        String query = "SELECT images AS images, " +
                "       s.barcode AS barcode, " +
                "       s.quantity AS quantity, " +
                "       p.name AS name, " +
                "       s2.vendor_number AS vendor_number, " +
                "       b.name AS brand_name, " +
                "       s.size AS sizes, " +
                "       sp.color AS color " +
                "FROM sub_products sp " +
                "         LEFT JOIN sub_product_images spi ON sp.id = spi.sub_product_id " +
                "         LEFT JOIN sizes s ON sp.id = s.sub_product_id " +
                "         LEFT JOIN products p ON p.id = sp.product_id " +
                "         LEFT JOIN sellers s2 ON p.seller_id = s2.id " +
                "         LEFT JOIN brands b ON p.name = b.name " +
                "WHERE b.name iLIKE ? " +
                "   OR p.name iLIKE ? " +
                "   OR sp.color iLIKE ?";
        String countSql = "SELECT COUNT(*) FROM (" + query + ") AS count_query";
        int count = jdbcTemplate.queryForObject(countSql, Integer.class, "%" + keyWord + "%", "%" + keyWord + "%", "%" + keyWord + "%");
        int totalPage = (int) Math.ceil((double) count / size);
        int offset = (page - 1) * size;
        query = query + " LIMIT ? OFFSET ?";
        List<SupplyProductResponse> query1 = jdbcTemplate.query(query, (resultSet, i) ->
                        new SupplyProductResponse(
                                resultSet.getString("images"),
                                resultSet.getString("barcode"),
                                resultSet.getInt("quantity"),
                                resultSet.getString("name"),
                                resultSet.getString("vendor_number"),
                                resultSet.getString("brand_name"),
                                resultSet.getString("sizes"),
                                resultSet.getString("color")),
                "%" + keyWord + "%",
                "%" + keyWord + "%",
                "%" + keyWord + "%",
                size,
                offset);

        return PaginationResponse
                .<SupplyProductResponse>builder()
                .currentPage(page)
                .totalPage(totalPage)
                .quantityOfProducts(count)
                .elements(query1)
                .build();
    }

//    @Override
//    public PaginationResponse<SupplyProductResponse> searchSupplyProducts(String keyWord, int page, int size) {
//        String query = " select images           as images," +
//                "       s.barcode        as barcode," +
//                "       s.quantity       as quantity," +
//                "       p.name           as name," +
//                "       s2.vendor_number as vendor_number," +
//                "       b.name           as brand_name," +
//                "       s.size           as  sizes, " +
//                "       sp.color         as color " +
//                "from sub_products sp" +
//                "         left join sub_product_images spi on sp.id = spi.sub_product_id" +
//                "         left join sizes s on sp.id = s.sub_product_id" +
//                "         left join products p on p.id = sp.product_id" +
//                "         left join sellers s2 on p.seller_id = s2.id" +
//                "         left join brands b on p.name = b.name" +
//                " where b.name ilike '%" + keyWord + "%'" +
//                "   or p.name ilike '%" + keyWord + "%'" +
//                "   or sp.color ilike '%" + keyWord + "%'";
//
//        List<SupplyProductResponse> query1 = jdbcTemplate.query(query, (resultSet, i) ->
//                new SupplyProductResponse(
//                        resultSet.getString("images"),
//                        resultSet.getString("barcode"),
//                        resultSet.getInt("quantity"),
//                        resultSet.getString("name"),
//                        resultSet.getString("vendor_number"),
//                        resultSet.getString("brand_name"),
//                        resultSet.getString("sizes"),
//                        resultSet.getString("color"))
//
//        );
//        return PaginationResponse
//                .<SupplyProductResponse>builder()
//                .currentPage(page)
//                .totalPage(page)
//                .quantityOfProducts(size)
//                .elements(query1)
//                .build();
//    }
}
