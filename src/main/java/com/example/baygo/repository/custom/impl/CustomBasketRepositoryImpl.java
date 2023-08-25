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
                SELECT b.sub_products_size_id as sizeId,
                       sp.description as description,
                       s.size as size,
                       sp.main_image as image,
                       d.percent as discount,
                       sp.price as cost
                    FROM buyers_baskets b
                    join sizes s on s.id = b.sub_products_size_id
                    join sub_products sp on sp.id = s.sub_product_id
                    join products p on p.id = sp.product_id
                    join discounts d on sp.discount_id = d.id
                    WHERE b.buyer_id =?
                """;
        return jdbcTemplate.query(addToBasket, (resultSet,i)->
                new ProductsInBasketResponse(resultSet.getLong("sizeId"),
                        resultSet.getString("description"),
                        resultSet.getString("size"),
                        resultSet.getString("image"),
                        resultSet.getInt("discount"),
                        resultSet.getBigDecimal("cost")),
                        buyer.getId());
    }
}
