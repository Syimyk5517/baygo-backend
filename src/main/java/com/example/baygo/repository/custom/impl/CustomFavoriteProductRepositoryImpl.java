package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.HomePageResponse;
import com.example.baygo.repository.custom.CustomFavoriteProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomFavoriteProductRepositoryImpl implements CustomFavoriteProductRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<HomePageResponse> findAllFavoriteItems() {
        String sql = """
                SELECT p.id AS productId,
                sp.id AS subProductId,
                p.name AS productName,
                sp.main_image AS productImage,
                COUNT(bf.buyer_id) AS selection_count
                FROM products p
                JOIN sub_products sp ON p.id = sp.product_id
                JOIN buyers_favorites bf ON sp.id = bf.sub_products_id
                GROUP BY p.id, sp.id, p.name, sp.main_image
                HAVING COUNT(bf.buyer_id) >= (SELECT COUNT(*) FROM buyers) / 2
                ORDER BY selection_count DESC
                LIMIT 8
                """;
        return jdbcTemplate.query(sql,(innerResult,i)->{
            return HomePageResponse.builder()
                    .productId(innerResult.getLong("productId"))
                    .subProductId(innerResult.getLong("subProductId"))
                    .name(innerResult.getString("productName"))
                    .mainImage(innerResult.getString("productImage")).build();
        });
    }
}
