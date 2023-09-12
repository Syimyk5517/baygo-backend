package com.example.baygo.repository.custom.impl;

import com.example.baygo.config.jwt.JwtService;
import com.example.baygo.db.dto.response.ProductsInBasketResponse;
import com.example.baygo.db.model.Buyer;
import com.example.baygo.repository.custom.CustomAddToBasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class CustomBasketRepositoryImpl implements CustomAddToBasketRepository {
    private final JdbcTemplate jdbcTemplate;
    private final JwtService jwtService;
    @Override
    public List<ProductsInBasketResponse> getAllProductFromBasket() {
        Buyer buyer = jwtService.getAuthenticate().getBuyer();
        String addToBasket = """
                SELECT b.sub_products_size_id               AS sizeId,
                       sp.description                       AS description,
                       s.size                               AS size,
                       sp.main_image                        AS image,
                       d.percent                            AS discount,
                       sp.price                             AS cost,
                       SUM(s.fbb_quantity + s.fbs_quantity) AS quantity
                FROM buyers_baskets b
                         JOIN sizes s ON s.id = b.sub_products_size_id
                         JOIN sub_products sp ON sp.id = s.sub_product_id
                         JOIN products p ON p.id = sp.product_id
                         JOIN discounts d ON sp.discount_id = d.id
                WHERE b.buyer_id = ?
                GROUP BY sp.description, s.size, sp.main_image, d.percent, sp.price, b.sub_products_size_id
                """;
        return jdbcTemplate.query(addToBasket, (resultSet,i)->
                new ProductsInBasketResponse(resultSet.getLong("sizeId"),
                        resultSet.getString("description"),
                        resultSet.getString("size"),
                        resultSet.getString("image"),
                        resultSet.getInt("discount"),
                        resultSet.getBigDecimal("cost"),
                        resultSet.getInt("quantity")),
                        buyer.getId());
    }
}
