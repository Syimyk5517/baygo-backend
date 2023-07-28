package com.example.baygo.repository.custom.impl;

import com.example.baygo.db.dto.response.BuyerQuestionResponse;
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
                    p.id as product_id, u.full_name as full_name,b.photo as photo,
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
}
