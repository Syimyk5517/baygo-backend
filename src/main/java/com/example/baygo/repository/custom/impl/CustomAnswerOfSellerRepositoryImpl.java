package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.QuestionForSellerLandingResponse;
import com.example.baygo.repository.custom.CustomAnswerOfSellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomAnswerOfSellerRepositoryImpl implements CustomAnswerOfSellerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<QuestionForSellerLandingResponse> getAllQuestionsForSeller(Long sellerId) {
        String getAllQuestion = """
                select
                    bq.id as id,
                    sp.main_image as productPhoto,
                    bq.question as description,
                    bq.created_at as createAt
                from buyer_questions bq
                    join sub_products sp on bq.sub_product_id = sp.id
                    join products p on sp.product_id = p.id
                where p.seller_id = ?
                order by bq.created_at desc limit 4
                """;

        return jdbcTemplate.query(getAllQuestion, (resultSet, i) -> new QuestionForSellerLandingResponse(
                resultSet.getLong("id"),
                resultSet.getString("productPhoto"),
                resultSet.getString("description"),
                resultSet.getDate("createAt").toLocalDate()
        ), sellerId);
    }
}
