package com.example.baygo.db.repository.custom.impl;

import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SizeSellerResponse;
import com.example.baygo.db.repository.custom.CustomProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public Page<ProductResponseForSeller> getAll(Pageable pageable, long sellerId, String status, String keyWord) {
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
                           spi.images as sub_product_photo,
                           sel.vendor_number as vendor_number,
                           pr.articul as product_articul,
                           pr.name as product_name,
                           pr.brand as product_brand,
                           pr.rating as product_rating,
                           pr.date_of_change as product_date_of_change,
                           sp.color as sub_product_color,
                           (select total from count_cte) as total_count
                    from sub_products sp
                             join products pr on sp.product_id = pr.id
                             join sub_product_images spi on sp.id = spi.sub_product_id
                             join sellers sel on pr.seller_id = sel.id
                             %s
                    where sel.id = ? %s
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

        String sqlStatus = switch (status) {
            case "В избранном" -> "join buyers_favorites bsp on sp.id = bsp.sub_products_id";
            case "В корзине" -> "join buyers_baskets bsps on s.id = bsps.sub_products_size_id";
            case "Все акции" -> "join discounts d on d.id = sp.discount_id";
            default -> "";
        };

        List<Object> params = new ArrayList<>();
        params.add(sellerId);
        params.add(sellerId);

        String keywordCondition = " and (1=1)";
        if (keyWord != null && !keyWord.isEmpty()) {
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");

            keywordCondition = """
                    and (
                    pr.name iLIKE ? OR pr.articul iLIKE ? OR pr.brand iLIKE ?
                    )
                    """;
        }

        sql = String.format(sql, sqlStatus, keywordCondition);

        int total = jdbcTemplate.queryForObject("select count(*) from (" + sql + ") as count_query", Integer.class, params.toArray());

        sql = sql + " limit ? offset ?";
        params.add(pageable.getPageSize());
        params.add(pageable.getOffset());

        List<ProductResponseForSeller> response = jdbcTemplate.query(sql, params.toArray(), (rs, rowNum) -> new ProductResponseForSeller(
                rs.getLong("product_id"),
                rs.getLong("sub_product_id"),
                rs.getString("sub_product_photo"),
                rs.getString("vendor_number"),
                rs.getString("product_articul"),
                rs.getString("product_name"),
                rs.getString("product_brand"),
                rs.getDouble("product_rating"),
                rs.getDate("product_date_of_change").toLocalDate(),
                rs.getString("sub_product_color"),
                jdbcTemplate.query(getSizes, ((resultSet, rowNum1) -> new SizeSellerResponse(
                        resultSet.getLong("id"),
                        resultSet.getString("size"),
                        resultSet.getInt("barcode"),
                        resultSet.getInt("quantity")
                )), rs.getLong("sub_product_id"))));

        return new PageImpl<>(response, pageable, total);
    }
}