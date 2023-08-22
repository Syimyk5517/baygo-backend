package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.request.SellerSizeRequest;
import com.example.baygo.db.dto.response.*;
import com.example.baygo.repository.custom.CustomProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public PaginationResponse<ProductResponseForSeller> getAll(Long sellerId, Long categoryId, String keyWord, String sortBy, boolean ascending, int page, int size) {
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
                           (select s.size from sizes s where s.sub_product_id = sp.id limit 1) as size,
                           (select s.barcode from sizes s where s.sub_product_id = sp.id limit 1) as barcode,
                           (select s.quantity from sizes s where s.sub_product_id = sp.id limit 1) as quantity,
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
        switch (sortBy) {
            case "rating" -> sql = sql.replace("order by pr.date_of_change desc",
                    "order by pr.rating " + (ascending ? "asc" : "desc"));

            case "quantity" -> sql = sql.replace("order by pr.date_of_change desc",
                    "order by quantity " + (ascending ? "asc" : "desc"));

            default -> sql = sql.replace("order by pr.date_of_change desc",
                    "order by pr.date_of_change " + (ascending ? "asc" : "desc"));
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
                rs.getString("size"),
                rs.getInt("barcode"),
                rs.getInt("quantity"),
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
    public ProductGetByIdResponse getById(Long id) {
        String query = """
                SELECT sp.id as subProductId, MAX(p.manufacturer) as manufacturer,
                                       p.name, p.brand,sp.articulbg, p.season, p.composition,sp.price ,sp.articulbg,
                                       sp.articul_of_seller as articulOfSeller,sp.height,sp.weight,sp.width,sp.length,sp.description 
                                FROM sub_products sp
                                JOIN products p on sp.product_id = p.id
                                LEFT JOIN sizes s on sp.id = s.sub_product_id
                                JOIN sub_product_images spi on sp.id = spi.sub_product_id
                                WHERE sp.id = ?
                                GROUP BY sp.id, p.name, p.brand, sp.articulbg, p.season, p.composition, sp.description""";
        Object[] params = {id};
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(query, params);
        if (rows.isEmpty()) {
            return null;
        }
        Map<String, Object> row = rows.get(0);
        Long ids = (Long) row.get("subProductId");
        String name = (String) row.get("name");
        String brand = (String) row.get("brand");
        BigDecimal price = (BigDecimal) row.get("price");
        String manufacturer = (String) row.get("manufacturer");
        String season = (String) row.get("season");
        String composition = (String) row.get("composition");
        Integer articulbg = (Integer)row.get("articulbg");
        String articulOfSeller = (String) row.get("articulOfSeller");
        String description = (String) row.get("description");
        Integer height = (Integer) row.get("height");
        double weight = (double) row.get("weight");
        Integer width = (Integer) row.get("width");
        Integer length = (Integer) row.get("length");

        String getSizes = """
                select
                   s.size as size,
                   s.barcode as barcode
                from sizes s
                where s.sub_product_id = ?
                """;
        List<SellerSizeRequest> sizeSellerResponses = jdbcTemplate.query(getSizes, params, (rs, i) -> {
            String size = rs.getString("size");
            int barcode = rs.getInt("barcode");
            return new SellerSizeRequest( size, barcode);
        });

        String colorQuery = """
                SELECT sp.color,
                 sp.color_hex_code
                FROM sub_products sp
                WHERE sp.product_id = ?""";
        List<ColorResponse> colorResponses = jdbcTemplate.query(colorQuery, params, (rs, rowNum) -> {
            String colorHex = rs.getString("color_hex_code");
            String color1 = rs.getString("color");
            return new ColorResponse(colorHex, color1);
        });

        String images = """
                SELECT  ARRAY_AGG(sp.images) as images
                FROM sub_product_images sp 
                WHERE sp.sub_product_id = ?""";
        List<ImageResponse> imageResponses = jdbcTemplate.query(images, params, (rs, rowNum) -> {
            String images1 = rs.getString("images");
            return new ImageResponse(images1);
        });
        return new ProductGetByIdResponse(ids, manufacturer, brand, name,
                 season, composition, description, articulbg, articulOfSeller,
                height, width, length, weight, price,
                colorResponses, sizeSellerResponses, imageResponses);
    }
}