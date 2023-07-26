package com.example.baygo.db.repository.custom.impl;

import com.example.baygo.db.dto.response.GetAllReviewsResponse;
import com.example.baygo.db.repository.custom.CustomGetAllReviewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
@RequiredArgsConstructor
public class CustomGetAllReviewsRepoImpl implements CustomGetAllReviewsRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<GetAllReviewsResponse> getAllReviews() {
        String sql = """
                select r.id, spi.images, r.grade,r.text, r.date_and_time
                    from reviews r
                    join sub_products sp on r.product_id = sp.product_id
                    join sub_product_images spi on sp.id = spi.sub_product_id
                """;
        return jdbcTemplate.query(sql, (resultSet, i)-> new GetAllReviewsResponse(
                resultSet.getLong("id"),
                resultSet.getString("image"),
                resultSet.getInt("grade"),
                resultSet.getString("text"),
                resultSet.getDate("dateAndTime").toLocalDate()
        ));
    }
}
