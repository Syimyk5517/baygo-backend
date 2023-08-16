package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.ColorResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SizeSellerResponse;
import com.example.baygo.db.dto.response.product.ProductGetByIdResponse;
import com.example.baygo.db.dto.response.product.SizeResponse;
import com.example.baygo.repository.custom.CustomProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public PaginationResponse<ProductResponseForSeller> getAll(Long sellerId, String status, String keyWord, int page, int size) {
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
                           (select spi.images
                           from sub_product_images spi
                           where spi.sub_product_id = sp.id limit 1) as sub_product_photo,
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
                             join sellers sel on pr.seller_id = serial
                             %s
                    where sel.id = ? %s
                    order by pr.date_of_change desc
                """;

        String getSizes = """
                select
                   s.id,
                   s.size,
                   s.barcode,
                   s.fbs_quantity
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

        int count = jdbcTemplate.queryForObject("select count(*) from (" + sql + ") as count_query", Integer.class, params.toArray());

        int totalPage = (int) Math.ceil((double) count / size);
        int offset = (page - 1) * size;

        sql = sql + " limit ? offset ?";
        params.add(size);
        params.add(offset);

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

        return PaginationResponse.<ProductResponseForSeller>builder()
                .elements(response)
                .totalPages(totalPage)
                .currentPage(page)
                .build();
    }

    @Override
    public ProductGetByIdResponse getById(Long id, Long subProductId) {
        String query = """
                    SELECT sp.id as subProductId, p.name,  sp.color, p.brand, sp.price, sp.articul_of_seller, p.description,
                        COUNT(r.id) as amountOfReviews,
                           COALESCE(SUM(r.amount_of_like), 0) as totalLikes,
                           COALESCE(AVG(r.grade), 0) as averageRating
                    FROM sub_products sp
                    JOIN products p on sp.product_id = p.id 
                    LEFT JOIN reviews r ON p.id = r.product_id
                    WHERE sp.id = ?
                    GROUP BY sp.id, sp.color, sp.price,p.name, p.brand, sp.price, sp.articul_of_seller, p.description
                """;
        Object[] params = {subProductId};

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, params);
        if (rows.isEmpty()) {
            return null;
        }

        Map<String, Object> row = rows.get(0);
        Long subProductId1 = (Long) row.get("subProductId");
        String name = (String) row.get("name");
        String color = (String) row.get("color");
        String brand = (String) row.get("brand");
        BigDecimal price = (BigDecimal) row.get("price");
        String articul = (String) row.get("articul_of_seller");
        String description = (String) row.get("description");
        double rating = ((BigDecimal) row.get("averageRating")).doubleValue();
        int amountOfReviews = ((Number) row.get("amountOfReviews")).intValue();
        int totalLikes = rows.stream()
                .mapToInt(r -> ((Number) r.get("totalLikes")).intValue())
                .sum();

        int percentageOfLikes = 0;
        if (totalLikes != 0) {
            percentageOfLikes = Math.round((float) totalLikes / totalLikes * 100);
        }

        String colorQuery = """
                    SELECT sp.color, sp.color_hex_code
                    FROM sub_products sp
                    WHERE sp.product_id = ?
                """;

        List<ColorResponse> colorResponses = jdbcTemplate.query(colorQuery, params, (rs, rowNum) -> {
            String colorHex = rs.getString("color_hex_code");
            String color1 = rs.getString("color");
            return new ColorResponse(colorHex, color1);
        });


        String sizeQuery = """
                    SELECT   s.size
                    FROM sub_products sp
                    JOIN sizes s ON sp.id = s.sub_product_id
                    WHERE sp.id= ?
                """;

        List<SizeResponse> sizeResponses = jdbcTemplate.query(sizeQuery, params, (rs, rowNum) -> {
            String size = rs.getString("size");

            return new SizeResponse(size);
        });


        return new ProductGetByIdResponse(subProductId1, name, color, articul, brand, price, rating, amountOfReviews, percentageOfLikes, colorResponses, sizeResponses, description);
    }
    }
