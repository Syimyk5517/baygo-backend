package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.PaginationResponse;
import com.example.baygo.db.dto.response.ProductBuyerResponse;
import com.example.baygo.db.dto.response.ProductResponseForSeller;
import com.example.baygo.db.dto.response.SizeSellerResponse;
import com.example.baygo.repository.custom.CustomProductRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomProductRepositoryImpl implements CustomProductRepository {
    private final JdbcTemplate jdbcTemplate;
    @PersistenceContext
    private final EntityManager entityManager;

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
    public PaginationResponse<ProductBuyerResponse> getAllProductsBuyer(String keyWord, List<String> sizes, List<String> compositions, List<String> brands, BigDecimal minPrice, BigDecimal maxPrice, List<String> colors, String sortBy, int page, int pageSize) {

            StringBuilder jpqlBuilder = new StringBuilder("SELECT " +
                    "s.id as sizeId, " +
                    "(select i.images from SubProductImages i where i.subProduct.id = sp.id limit 1) as image, " +
                    "p.name as name, p.description as description, p.rating as rating, " +
                    "count(r) as quantityOfRating, sp.price as price, " +
                    "coalesce(d.percent, 0) as percentOfDiscount " +
                    "from Product p " +
                    "left join p.reviews r " +
                    "join p.subProducts sp " +
                    "%s join sp.discount d " +
                    "join sp.sizes s " +
                    "%s " +
                    "where s.quantity > 0 %s %s %s %s %s %s %s " +
                    "group by s.id, image, name, description, rating, price, percentOfDiscount %s");

            List<Object> params = new ArrayList<>();

            String keywordCondition = "";
            if (keyWord != null) {
                keywordCondition = "AND p.name LIKE ? ";
                params.add("%" + keyWord + "%");
            }

            StringBuilder sizeCondition = new StringBuilder();
            if (sizes != null) {
                sizeCondition.append("AND s.size in (");
                for (int i = 0; i < sizes.size(); i++) {
                    if ((sizes.size() - 1) != i) {
                        sizeCondition.append(" ?, ");
                    } else {
                        sizeCondition.append(" ? ");
                    }
                }
                sizeCondition.append(")");
                params.addAll(sizes);
            }

        StringBuilder compositionCondition = new StringBuilder();
        if (compositions != null) {
            compositionCondition.append("AND p.composition in ( ");
            for (int comp = 0; comp < compositions.size(); comp++) {
                if((compositions.size()-1) != comp) {
                    compositionCondition.append(" ?, " +
                            "");
                }else {
                    compositionCondition.append(" ? ");
                }
            }
            compositionCondition.append(" )");
            params.addAll(compositions);
        }

        StringBuilder brandCondition = new StringBuilder();
        if (brands != null) {
            brandCondition.append("AND p.brand in ( ");
            for (int i = 0; i < brands.size(); i++) {
                if((brands.size()-1) != i) {
                    brandCondition.append(" ?, ");
                }else {
                    brandCondition.append(" ? ");
                }
            }
            brandCondition.append(" )");
            params.addAll(brands);
        }

            String priceCondition = "";
            if (minPrice != null && maxPrice != null) {
                priceCondition = "AND sp.price between ? and ? ";
                params.add(minPrice);
                params.add(maxPrice);

            } else if (minPrice != null) {
                priceCondition = "AND sp.price >= ? ";
                params.add(minPrice);
            } else if (maxPrice != null) {
                priceCondition = "AND sp.price <= ? ";
                params.add(maxPrice);
            }

        StringBuilder colorCondition = new StringBuilder();
        if (colors != null) {
            colorCondition.append("AND sp.color in ( ");
            for (int color = 0; color < colors.size(); color++) {
                if ((colors.size() - 1) != color) {
                    colorCondition.append(" ?, ");
                } else {
                    colorCondition.append(" ? ");
                }
            }
            colorCondition.append(")");
            params.addAll(colors);
        }

            String orderBy = "";
            String condition = "";
            String joinType = "LEFT";
            String addJoin = "";
            if (sortBy != null) {
                switch (sortBy) {
                    case "Новинки":
                        condition = "AND p.dateOfCreate >= CURRENT_DATE - interval '7 day' ";
                        break;
                    case "Бестселлеры":
                        addJoin = "join sp.orders o on o.subProduct = sp";
                        condition = "AND p.dateOfCreate >= CURRENT_DATE - interval '7 day' and o.productCount > 20 ";
                        orderBy = ", o.productCount\n" +
                                "order by o.productCount";
                        break;
                    case "Все акции":
                        joinType = "";
                        break;
                    case "По увеличению цены":
                        orderBy = "order by sp.price";
                        break;
                    case "По уменьшению цены":
                        orderBy = "order by sp.price DESC";
                        break;
                }
            }

            String jpql = String.format(jpqlBuilder.toString(), joinType, addJoin, keywordCondition, sizeCondition, compositionCondition, brandCondition, priceCondition, colorCondition, condition, orderBy);

            TypedQuery<Long> countQuery = entityManager.createQuery("SELECT COUNT(*) " + jpql, Long.class);
            for (int i = 0; i < params.size(); i++) {
                countQuery.setParameter(i + 1, params.get(i));
            }
            int count = countQuery.getSingleResult().intValue();
            int totalPage = (int) Math.ceil((double) count / pageSize);
            int offset = (page - 1) * pageSize;

            jpql = jpql + " LIMIT :pageSize OFFSET :offset";
            TypedQuery<ProductBuyerResponse> query = entityManager.createQuery(jpql, ProductBuyerResponse.class);
            for (int i = 0; i < params.size(); i++) {
                query.setParameter(i + 1, params.get(i));
            }
            query.setParameter("pageSize", pageSize);
            query.setParameter("offset", offset);

            List<ProductBuyerResponse> products = query.getResultList();

            return PaginationResponse.<ProductBuyerResponse>builder()
                    .currentPage(page)
                    .totalPages(totalPage)
                    .quantityOfProducts(count)
                    .elements(products).build();
        }


}