package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.dto.response.PaginationReviewAndQuestionResponse;
import com.example.baygo.db.dto.response.QuestionForSellerLandingResponse;
import com.example.baygo.repository.custom.CustomAnswerOfSellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CustomAnswerOfSellerRepositoryImpl implements CustomAnswerOfSellerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public PaginationReviewAndQuestionResponse<BuyerQuestionResponse> getAllQuestions(boolean isAnswered, String keyWord, int page, int pageSize, Long sellerId) {
        String sql = """
                SELECT
                    bq.id as question_id,
                    sp.id as sub_product_id,
                    sp.main_image as product_image,
                    p.name as product_name,
                    bq.question as question,
                    bq.answer as answer,
                    sp.articul_of_seller as articul_of_seller,
                    sp.articulbg as articulBG,
                    bq.created_at as date_and_time
                from buyer_questions bq
                join sub_products sp on bq.sub_product_id = sp.id
                join products p on sp.product_id = p.id
                where p.seller_id = ?
                and bq.answer is null
                %s
                """;
        List<Object> params = new ArrayList<>();
        params.add(sellerId);

        if (isAnswered) {
            sql = sql.replace("and bq.answer is null", "AND bq.answer IS NOT NULL");
        }
        String keywordCondition = "";
        if (keyWord != null) {
            keywordCondition = "AND (p.name iLIKE ? OR sp.articul_of_seller iLIKE ? OR CAST(sp.articulbg AS TEXT) iLIKE ?)";
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");
            params.add("%" + keyWord + "%");
        }
        sql = String.format(sql, keywordCondition);

        String countSql = "SELECT COUNT(*) FROM (" + sql + ") as count_query";
        int count = jdbcTemplate.queryForObject(countSql, params.toArray(), Integer.class);
        int totalPage = (int) Math.ceil((double) count / pageSize);
        int offset = (page - 1) * pageSize;
        sql = String.format(sql + "LIMIT %s OFFSET %s", pageSize, offset);

        List<BuyerQuestionResponse> questions = jdbcTemplate.query(sql, params.toArray(), (resultSet, i) -> new BuyerQuestionResponse(
                resultSet.getLong("question_id"),
                resultSet.getLong("sub_product_id"),
                resultSet.getString("product_image"),
                resultSet.getString("product_name"),
                resultSet.getString("question"),
                resultSet.getString("answer"),
                resultSet.getString("articul_of_seller"),
                resultSet.getString("articulBG"),
                resultSet.getString("date_and_time")));

        return PaginationReviewAndQuestionResponse.<BuyerQuestionResponse>builder()
                .currentPage(page)
                .totalPages(totalPage)
                .elements(questions).build();
    }

    @Override
    public List<QuestionForSellerLandingResponse> getAllQuestionsForSeller(Long sellerId) {
        String getAllQuestion = """
                select
                    bq.id as id,
                    (select spi.images
                    from sup_product_images
                    where spi.sub_product_id = sp.id limit 1) as productPhoto,
                    bq.question as description,
                    bq.created_at as createAt
                from buyer_questions bq
                    join sub_products sp on bq.product_id = sp.product_id
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
