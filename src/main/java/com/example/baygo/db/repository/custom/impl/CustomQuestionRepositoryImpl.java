package com.example.baygo.db.repository.custom.impl;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.repository.custom.CustomQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
@RequiredArgsConstructor
public class CustomQuestionRepositoryImpl implements CustomQuestionRepository {
    private final JdbcTemplate jdbcTemplate;
    @Override
    public List<BuyerQuestionResponse> getAllQuestions() {
        String getAllQuestion = """
                select 
                bq.id as id,
                spi.images as productPhoto,
                bq.question as description,
                bq.created_at as createAt
                from buyer_questions bq
                join sub_products sp on bq.product_id = sp.product_id
                join sub_product_images spi on sp.id = spi.sub_product_id
                """;

        return jdbcTemplate.query(getAllQuestion, (resultSet, i) -> new BuyerQuestionResponse(
                resultSet.getLong("id"),
                resultSet.getString("productPhoto"),
                resultSet.getString("description"),
                resultSet.getDate("createAt").toLocalDate()){
        });
    }
}
