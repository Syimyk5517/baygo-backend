package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;
import com.example.baygo.db.dto.response.QuestionForSellerLandingResponse;
import com.example.baygo.db.dto.response.PaginationResponse;
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
    public PaginationResponse<BuyerQuestionResponse> getAllQuestions(String keyWord, int page, int pageSize, Long sellerId) {
        String sql = """
                    select
                    (SELECT i.images FROM sub_product_images i
                    join sub_products sp on sp.id = i.sub_product_id
                    where sp.product_id = p.id LIMIT 1) as image,
                    p.id as product_id, b.full_name as full_name,b.photo as photo,
                    bq.question as question, p.articul as articul, p.name as name,
                    bq.created_at as create_at
                from buyer_questions bq
                         join buyers b on b.id = bq.buyer_id
                         join users u on u.id = b.user_id
                         join products p on p.id = bq.product_id
                         join sellers s on s.id = p.seller_id
                where s.id = %s %s """;
        List<Object> params = new ArrayList<>();

        String keywordCondition = "";
        if (keyWord != null) {
            keywordCondition = "AND p.articul iLIKE ? ";
            params.add("%" + keyWord + "%");
        }
        sql = String.format(sql, sellerId, keywordCondition);

        String countSql = "SELECT COUNT(*) FROM (" + sql + ") as count_query";
        int count = jdbcTemplate.queryForObject(countSql, params.toArray(), Integer.class);
        int totalPage = (int) Math.ceil((double) count / pageSize);
        int offset = (page - 1) * pageSize;
        sql = String.format(sql + "LIMIT %s OFFSET %s", pageSize, offset);

        List<BuyerQuestionResponse> questions = jdbcTemplate.query(sql, params.toArray(), (resultSet, i) -> new BuyerQuestionResponse(
                resultSet.getLong("product_id"),
                resultSet.getString("full_name"),
                resultSet.getString("photo"),
                resultSet.getString("image"),
                resultSet.getString("question"),
                resultSet.getString("articul"),
                resultSet.getString("name"),
                resultSet.getDate("create_at").toLocalDate()));
        return PaginationResponse.<BuyerQuestionResponse>builder()
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

        return jdbcTemplate.query(getAllQuestion, (resultSet, i)-> new QuestionForSellerLandingResponse(
                resultSet.getLong("id"),
                resultSet.getString("productPhoto"),
                resultSet.getString("description"),
                resultSet.getDate("createAt").toLocalDate()
        ), sellerId);
    }
}
