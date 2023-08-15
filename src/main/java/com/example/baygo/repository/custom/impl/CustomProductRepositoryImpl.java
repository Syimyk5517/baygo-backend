package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SizeSellerResponse;
import com.example.baygo.repository.custom.CustomProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public PaginationResponse<ProductResponseForSeller> getAll(Long sellerId, Long categoryId, String keyWord, int page, int size) {
        String sql = """
                    with count_cte as (
                        select count(*) as total
                        from sub_products sp
                                 join products pr on sp.product_id = pr.id
                                 left join sizes s on sp.id = s.sub_product_id
                                 left join sub_product_images spi on sp.id = spi.sub_product_id
                                 join sellers sel on pr.seller_id = sel.id
                        where sel.id = ?
                    )
                    select pr.id as product_id,
                           sp.id as sub_product_id,
                           sp.main_image as sub_product_image,
                           sp.articul_of_seller as articul_of_seller,
                           sp.articulbg as articulbg,
                           sc.name as product,
                           pr.brand as brand,
                           pr.rating as rating,
                           pr.date_of_change as date_of_change,
                           sp.color as color,
                           (select total from count_cte) as total_count
                    from sub_products sp
                             join products pr on sp.product_id = pr.id
                             join sellers sel on pr.seller_id = sel.id
                             join sub_categories sc on pr.sub_category_id = sc.id
                             join categories c on sc.category_id = c.id
                    where sel.id = ? %s %s
                    order by pr.date_of_change desc
                """;

        String getSizes = """
                select
                   s.id,
                   s.size,
                   s.barcode,
                   s.quantity
                from sizes s
                where s.sub_product_id = ?
                """;
        String categoryCondition = "";
        if (categoryId != null) {
            categoryCondition = " and c.id = " + categoryId;
        }

        List<Object> params = new ArrayList<>();
        params.add(sellerId);
        params.add(sellerId);

        String keywordCondition = " and (1=1)";
        if (keyWord != null && !keyWord.isEmpty()) {
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");

            keywordCondition = """
                    and (
                    pr.name iLIKE ? OR sp.articul_of_seller iLIKE ?
                    OR CAST(sp.articulbg AS TEXT) iLIKE ? OR sc.name iLIKE ?
                    OR pr.brand iLIKE ?
                    )
                    """;
        }

        sql = String.format(sql, categoryCondition, keywordCondition);

        int count = jdbcTemplate.queryForObject("select count(*) from (" + sql + ") as count_query", Integer.class, params.toArray());

        int totalPage = (int) Math.ceil((double) count / size);
        int offset = (page - 1) * size;

        sql = sql + " limit ? offset ?";
        params.add(size);
        params.add(offset);

        List<ProductResponseForSeller> response = jdbcTemplate.query(sql, params.toArray(), (rs, rowNum) -> new ProductResponseForSeller(
                rs.getLong("product_id"),
                rs.getLong("sub_product_id"),
                rs.getString("sub_product_image"),
                rs.getString("articul_of_seller"),
                rs.getString("articulbg"),
                rs.getString("product"),
                rs.getString("brand"),
                rs.getDouble("rating"),
                rs.getDate("date_of_change").toLocalDate(),
                rs.getString("color"),
                jdbcTemplate.query(getSizes, ((resultSet, rowNum1) -> new SizeSellerResponse(
                        resultSet.getLong("id"),
                        resultSet.getString("size"),
                        resultSet.getInt("barcode"),
                        resultSet.getInt("quantity")
                )), rs.getLong("sub_product_id"))));

        return PaginationResponse.<ProductResponseForSeller>builder()
                .elements(response)
                .totalPages(totalPage)
                .currentPage(page)
                .build();
    }
}